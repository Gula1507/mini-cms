package de.aygul.minicms.service;

import de.aygul.minicms.model.BlogPost;
import de.aygul.minicms.model.BlogStatus;
import de.aygul.minicms.model.Category;
import de.aygul.minicms.model.CategoryName;
import de.aygul.minicms.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;

    public BlogPost createBlog(BlogPost blogPost) {

        for (Category category : blogPost.getCategories()) {
            try {
                CategoryName.valueOf(category.getCategoryName().toString());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Category not found: " + category.getCategoryName());
            }
        }
        blogPost.setBlogStatus(BlogStatus.DRAFT);
        return blogPostRepository.save(blogPost);
    }
}