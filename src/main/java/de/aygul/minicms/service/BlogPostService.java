package de.aygul.minicms.service;

import de.aygul.minicms.dto.BlogPostRequestDTO;
import de.aygul.minicms.dto.BlogPostResponseDTO;
import de.aygul.minicms.exception.BlogPostIdNotFoundException;
import de.aygul.minicms.exception.CategoryNotFoundException;
import de.aygul.minicms.mapper.BlogPostMapper;
import de.aygul.minicms.mediator.ApplicationMediator;
import de.aygul.minicms.mediator.BlogPostHistoryMediator;
import de.aygul.minicms.model.BlogPost;
import de.aygul.minicms.model.BlogPostHistory;
import de.aygul.minicms.model.BlogPostStatus;
import de.aygul.minicms.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final ApplicationMediator applicationMediator;
    private final BlogPostHistoryMediator blogPostHistoryMediator;
    private final BlogPostMapper blogPostMapper;

    public Long createBlogPost(BlogPostRequestDTO blogPostRequestDTO) {
        BlogPost blogPost = blogPostMapper.toEntity(blogPostRequestDTO, applicationMediator);
        blogPostRepository.save(blogPost);
        return blogPost.getId();
    }

    public List<BlogPostResponseDTO> getAllBlogPosts() {
        List<BlogPost> blogPosts = blogPostRepository.findAll();
        return blogPosts.stream().map(blogPostMapper::toResponseDTO).toList();
    }

    public BlogPostResponseDTO getBlogPostById(Long id) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow(() -> new BlogPostIdNotFoundException(id));
        return blogPostMapper.toResponseDTO(blogPost);
    }

    public void deleteBlogPost(Long id) {
        if (!blogPostRepository.existsById(id)) {
            throw new BlogPostIdNotFoundException(id);
        }
        blogPostRepository.deleteById(id);
    }

    public BlogPostResponseDTO updateBlogPostPartial(Long id, String newTitle, String newBody) {
        BlogPost existingBlogPost = blogPostRepository.findById(id)
                                                      .orElseThrow(() -> new BlogPostIdNotFoundException(id));
        BlogPostHistory blogPostHistory = blogPostMapper.toBlogPostHistory(existingBlogPost);
        blogPostHistoryMediator.save(blogPostHistory);
        BlogPost updatedBlogPost = getUpdatedBlogPost(newTitle, newBody, existingBlogPost);
        blogPostRepository.save(updatedBlogPost);
        return blogPostMapper.toResponseDTO(updatedBlogPost);
    }

    public List<BlogPostResponseDTO> getBlogPostsByCategory(String categoryName) {
        List<BlogPost> filteredBlogPosts = blogPostRepository.findByCategories_CategoryNameIgnoreCase(categoryName);

        if (filteredBlogPosts.isEmpty()) {
            throw new CategoryNotFoundException("There is no category with name: " + categoryName);
        }

        return filteredBlogPosts.stream().map(blogPostMapper::toResponseDTO).toList();
    }

    public void updateBlogPostStatus(Long id, BlogPostStatus blogPostStatus) {
        BlogPost existingBlogPost = blogPostRepository.findById(id)
                                                      .orElseThrow(() -> new BlogPostIdNotFoundException(id));
        existingBlogPost.setBlogPostStatus(blogPostStatus);
        blogPostRepository.save(existingBlogPost);
    }

    private BlogPost getUpdatedBlogPost(String newTitle, String newBody, BlogPost existingBlogPost) {
        BlogPost updatedBlogPost = new BlogPost();

        updatedBlogPost.setTitle(newTitle != null && !newTitle.isBlank() ? newTitle : existingBlogPost.getTitle());
        updatedBlogPost.setBody(newBody != null && !newBody.isBlank() ? newBody : existingBlogPost.getBody());

        updatedBlogPost.setVersion(existingBlogPost.getVersion() + 1);
        updatedBlogPost.setPublicationDate(existingBlogPost.getPublicationDate());
        updatedBlogPost.setBlogPostStatus(existingBlogPost.getBlogPostStatus());
        updatedBlogPost.setCategories(existingBlogPost.getCategories());
        updatedBlogPost.setAuthor(existingBlogPost.getAuthor());
        updatedBlogPost.setId(existingBlogPost.getId());
        return updatedBlogPost;
    }
}