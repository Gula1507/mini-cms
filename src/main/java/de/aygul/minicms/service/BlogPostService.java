package de.aygul.minicms.service;

import de.aygul.minicms.dto.BlogPostRequestDTO;
import de.aygul.minicms.dto.BlogPostResponseDTO;
import de.aygul.minicms.dto.CategoryDTO;
import de.aygul.minicms.exception.BlogPostIdNotFoundException;
import de.aygul.minicms.exception.CategoryNotFoundException;
import de.aygul.minicms.mediator.ApplicationMediator;
import de.aygul.minicms.model.*;
import de.aygul.minicms.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final ApplicationMediator applicationMediator;

    public Long createBlogPost(BlogPostRequestDTO blogPostRequestDTO) {
        BlogPost blogPost = convertToEntity(blogPostRequestDTO);
        blogPostRepository.save(blogPost);
        return blogPost.getId();
    }

    public List<BlogPostResponseDTO> getAllBlogPosts() {
        List<BlogPost> blogPosts = blogPostRepository.findAll();
        return blogPosts.stream().map(this::convertToResponseDTO).toList();
    }

    public BlogPostResponseDTO getBlogPostById(Long id) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow(() -> new BlogPostIdNotFoundException(id));
        return convertToResponseDTO(blogPost);
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

        if (newTitle != null && !newTitle.isBlank()) {
            existingBlogPost.setTitle(newTitle);
        }
        if (newBody != null && !newBody.isBlank()) {
            existingBlogPost.setBody(newBody);
        }

        BlogPost updatedBlogPost = blogPostRepository.save(existingBlogPost);
        return convertToResponseDTO(updatedBlogPost);
    }

    public BlogPost convertToEntity(BlogPostRequestDTO blogPostRequestDTO) {

        List<Category> categories = blogPostRequestDTO.getCategoriesDTO().stream()
                                                      .map(categoryDTO -> applicationMediator.resolveCategoryByName(
                                                              categoryDTO.getCategoryName())).toList();

        return new BlogPost(null,
                blogPostRequestDTO.getTitle(),
                blogPostRequestDTO.getBody(),
                blogPostRequestDTO.getAuthor(),
                LocalDate.now(),
                BlogPostStatus.DRAFT,
                categories);
    }

    public BlogPostResponseDTO convertToResponseDTO(BlogPost blogPost) {
        List<CategoryDTO> categoryDTOs = blogPost.getCategories().stream()
                                                 .map(category -> new CategoryDTO(category.getCategoryName())).toList();

        return new BlogPostResponseDTO(blogPost.getId(),
                blogPost.getTitle(),
                blogPost.getBody(),
                blogPost.getAuthor(),
                blogPost.getPublicationDate(),
                blogPost.getBlogPostStatus(),
                categoryDTOs);
    }

    public List<BlogPostResponseDTO> getBlogPostsByCategory(String categoryName) {
        List<BlogPost> filteredBlogPosts = blogPostRepository.findByCategories_CategoryNameIgnoreCase(categoryName);

        if (filteredBlogPosts.isEmpty()) {
            throw new CategoryNotFoundException("There is no category with name: " + categoryName);
        }

        return filteredBlogPosts.stream()
                                .map(this::convertToResponseDTO)
                                .toList();
    }

    public void updateBlogPostStatus(Long id, BlogPostStatus blogPostStatus) {
        BlogPost existingBlogPost = blogPostRepository.findById(id)
                                                      .orElseThrow(() -> new BlogPostIdNotFoundException(id));
        existingBlogPost.setBlogPostStatus(blogPostStatus);
        blogPostRepository.save(existingBlogPost);
    }
}