package de.aygul.minicms.service;

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

    public Long createBlog(BlogPostDTO blogPostDTO) {
        BlogPost blogPost = convertToEntity(blogPostDTO);
        blogPostRepository.save(blogPost);
        return blogPost.getId();
    }

    public List<BlogPostDTO> getAllBlogPosts() {
        List<BlogPost> blogPosts = blogPostRepository.findAll();
        return blogPosts.stream().map(this::convertToDTO).toList();
    }

    public BlogPost convertToEntity(BlogPostDTO blogPostDTO) {

        List<Category> categories = blogPostDTO.getCategoriesDTO().stream()
                                               .map(categoryDTO -> applicationMediator.resolveCategoryByName(categoryDTO.getCategoryName()))
                                               .toList();

        return new BlogPost(null,
                blogPostDTO.getTitle(),
                blogPostDTO.getBody(),
                blogPostDTO.getAuthor(),
                LocalDate.now(),
                BlogPostStatus.DRAFT,
                categories);
    }

    public BlogPostDTO convertToDTO(BlogPost blogPost) {
        List<CategoryDTO> categoryDTOs = blogPost.getCategories().stream()
                                                 .map(category -> new CategoryDTO(category.getCategoryName()))
                                                 .toList();

        return new BlogPostDTO(
                blogPost.getTitle(),
                blogPost.getBody(),
                blogPost.getAuthor(),
                categoryDTOs
        );
    }
}