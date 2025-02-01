package de.aygul.minicms.service;

import de.aygul.minicms.mediator.ApplicationMediator;
import de.aygul.minicms.model.BlogPost;
import de.aygul.minicms.model.BlogPostDTO;
import de.aygul.minicms.model.BlogPostStatus;
import de.aygul.minicms.model.Category;
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
}