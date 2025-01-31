package de.aygul.minicms.controller;

import de.aygul.minicms.model.BlogPost;
import de.aygul.minicms.service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blogpost")
public class BlogPostController {

    private final BlogPostService blogPostService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost createBlog(@RequestBody BlogPost blogPost) {
        return blogPostService.createBlog(blogPost);
    }
}
