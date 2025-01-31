package de.aygul.minicms.service;

import de.aygul.minicms.model.BlogPost;
import de.aygul.minicms.model.BlogPostStatus;
import de.aygul.minicms.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;

    public BlogPost createBlog(BlogPost blogPost) {
        blogPost.setBlogPostStatus(BlogPostStatus.DRAFT);
        return blogPostRepository.save(blogPost);
    }
}