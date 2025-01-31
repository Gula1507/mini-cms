package de.aygul.minicms.controller;

import de.aygul.minicms.service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blogpost")
public class BlogPostController {

    private final BlogPostService blogPostService;
}
