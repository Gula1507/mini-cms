package de.aygul.minicms.controller;

import de.aygul.minicms.model.BlogPostRequestDTO;
import de.aygul.minicms.model.BlogPostResponseDTO;
import de.aygul.minicms.service.BlogPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blogpost")
public class BlogPostController {

    private final BlogPostService blogPostService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createBlog(@RequestBody @Valid BlogPostRequestDTO blogPostRequestDTO)
    {
        return blogPostService.createBlogPost(blogPostRequestDTO);
    }

    @GetMapping
    public List<BlogPostResponseDTO> getAllBlogPosts() {
        return blogPostService.getAllBlogPosts();
    }
}
