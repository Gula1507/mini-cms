package de.aygul.minicms.controller;

import de.aygul.minicms.model.BlogPostDTO;
import de.aygul.minicms.service.BlogPostService;
import jakarta.validation.Valid;
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
    public Long createBlog(@RequestBody @Valid BlogPostDTO blogPostDTO)
    {
        return blogPostService.createBlog(blogPostDTO);
    }
}
