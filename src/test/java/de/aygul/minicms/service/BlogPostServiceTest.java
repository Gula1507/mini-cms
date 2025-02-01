package de.aygul.minicms.service;

import de.aygul.minicms.mediator.ApplicationMediator;
import de.aygul.minicms.model.*;
import de.aygul.minicms.repository.BlogPostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BlogPostServiceTest {

    private final BlogPostRepository mockedBlogPostRepository = mock(BlogPostRepository.class);
    private final ApplicationMediator mockedMediator = mock(ApplicationMediator.class);
    BlogPostService blogPostService=new BlogPostService(mockedBlogPostRepository,mockedMediator);

    @Test
    @DisplayName("Should create a blog post and return its ID")
    void testCreateBlogPostWithDraftStatus() {

        BlogPostDTO blogPostDTO = new BlogPostDTO("Valid Title",
                "Valid Body",
                "Valid Author",
                List.of(new CategoryDTO("Tech")));

        when(mockedBlogPostRepository.save(any(BlogPost.class))).thenAnswer(invocation -> {
            BlogPost savedPost = invocation.getArgument(0);
            savedPost.setId(1L);
            return savedPost;
        });

        blogPostService.convertToEntity(blogPostDTO);
        Long result = blogPostService.createBlog(blogPostDTO);
        assertEquals(1L, result);
        verify(mockedBlogPostRepository, times(1)).save(any(BlogPost.class));
    }
}