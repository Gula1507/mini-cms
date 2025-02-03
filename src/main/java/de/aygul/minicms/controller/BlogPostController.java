package de.aygul.minicms.controller;

import de.aygul.minicms.dto.BlogPostRequestDTO;
import de.aygul.minicms.dto.BlogPostResponseDTO;
import de.aygul.minicms.model.BlogPostStatus;
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

    @GetMapping("/{id}")
    public BlogPostResponseDTO getBlogPostById(@PathVariable Long id) {
        return blogPostService.getBlogPostById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlogPost(@PathVariable Long id) {
       blogPostService.deleteBlogPost(id);
    }

    @PatchMapping("/{id}")
    public BlogPostResponseDTO updateBlogPostPartial(@PathVariable Long id, @RequestParam(required = false) String title,
                                              @RequestParam(required = false) String body) {
        return blogPostService.updateBlogPostPartial(id, title,body);
    }

    @GetMapping("/category/{categoryName}")
    public List<BlogPostResponseDTO> getBlogPostsByCategory(@PathVariable String categoryName) {
        return blogPostService.getBlogPostsByCategory(categoryName);
    }

    @PatchMapping("/{id}/status")
    public void updateBlogPostStatus(@PathVariable Long id, @RequestBody
                                                     BlogPostStatus blogPostStatus
                                                     ) {
      blogPostService.updateBlogPostStatus(id, blogPostStatus);
    }
}
