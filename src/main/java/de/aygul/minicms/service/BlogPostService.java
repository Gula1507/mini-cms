package de.aygul.minicms.service;

import de.aygul.minicms.model.BlogPost;
import de.aygul.minicms.model.BlogStatus;
import de.aygul.minicms.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;

    public BlogPost createBlog(BlogPost blogPost) {
        blogPost.setBlogStatus(BlogStatus.DRAFT);
        return blogPostRepository.save(blogPost);
    }
}