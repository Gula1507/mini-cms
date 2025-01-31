package de.aygul.minicms.service;

import de.aygul.minicms.model.BlogPost;
import de.aygul.minicms.model.BlogPostStatus;
import de.aygul.minicms.model.Category;
import de.aygul.minicms.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RequiredArgsConstructor
class BlogPostServiceTest {

    BlogPostRepository mockedBlogPostRepository = mock(BlogPostRepository.class);

    BlogPostService blogPostService = new BlogPostService(mockedBlogPostRepository);

    @Test
    @DisplayName("createBlog save BlogPost with Draft status")
    void testCreateBlogPostWithDraftStatus() {

        Category validCategory = new Category(1L, "Tech", null);
        BlogPost validBlogPost = new BlogPost(null,
                "Valid Title",
                "Valid body",
                "Valid Author",
                null,
                null,
                List.of(validCategory));

        when(mockedBlogPostRepository.save(validBlogPost)).thenReturn(validBlogPost);

        BlogPost result = blogPostService.createBlog(validBlogPost);
        BlogPostStatus actualStatus = result.getBlogPostStatus();
        BlogPostStatus expectedStatus = BlogPostStatus.DRAFT;

        assertEquals(expectedStatus, actualStatus);
        verify(mockedBlogPostRepository, times(1)).save(validBlogPost);
    }
}