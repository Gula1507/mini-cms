package de.aygul.minicms.service;

import de.aygul.minicms.mediator.ApplicationMediator;
import de.aygul.minicms.model.*;
import de.aygul.minicms.repository.BlogPostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BlogPostServiceTest {

    private final BlogPostRepository mockedBlogPostRepository = mock(BlogPostRepository.class);
    private final ApplicationMediator mockedMediator = mock(ApplicationMediator.class);
    BlogPostService blogPostService = new BlogPostService(mockedBlogPostRepository, mockedMediator);

    @Test
    @DisplayName("Should create a blog post and return its ID")
    void testCreateBlogPostWithDraftStatus() {

        BlogPostRequestDTO blogPostRequestDTO = new BlogPostRequestDTO("Valid Title",
                "Valid Body",
                "Valid Author",
                List.of(new CategoryDTO("Tech")));

        when(mockedBlogPostRepository.save(any(BlogPost.class))).thenAnswer(invocation -> {
            BlogPost savedPost = invocation.getArgument(0);
            savedPost.setId(1L);
            return savedPost;
        });

        blogPostService.convertToEntity(blogPostRequestDTO);
        Long result = blogPostService.createBlogPost(blogPostRequestDTO);
        assertEquals(1L, result);
        verify(mockedBlogPostRepository, times(1)).save(any(BlogPost.class));
    }

    @Test
    @DisplayName("getAllBlogPosts should return a list of BlogPostResponseDTOs with correct status")
    void getAllBlogPosts() {
        Category category1 = new Category(1L, "Tech", new ArrayList<>());
        Category category2 = new Category(2L, "Science", new ArrayList<>());

        when(mockedMediator.resolveCategoryByName("Tech")).thenReturn(category1);
        when(mockedMediator.resolveCategoryByName("Science")).thenReturn(category2);

        BlogPost blogPost1 = new BlogPost(null,
                "Title 1",
                "Body 1",
                "Author 1",
                LocalDate.now(),
                BlogPostStatus.DRAFT,
                List.of(category1));
        BlogPost blogPost2 = new BlogPost(null,
                "Title 2",
                "Body 2",
                "Author 2",
                LocalDate.now(),
                BlogPostStatus.PUBLISHED,
                List.of(category2));

        when(mockedBlogPostRepository.findAll()).thenReturn(List.of(blogPost1, blogPost2));

        List<BlogPostResponseDTO> blogPostResponseDTOs = blogPostService.getAllBlogPosts();

        assertNotNull(blogPostResponseDTOs);
        assertEquals(2, blogPostResponseDTOs.size());

        BlogPostResponseDTO responseDTO1 = blogPostResponseDTOs.getFirst();
        assertEquals("Title 1", responseDTO1.getTitle());
        assertEquals(BlogPostStatus.DRAFT, responseDTO1.getBlogPostStatus());

        BlogPostResponseDTO responseDTO2 = blogPostResponseDTOs.get(1);
        assertEquals("Title 2", responseDTO2.getTitle());
        assertEquals(BlogPostStatus.PUBLISHED, responseDTO2.getBlogPostStatus());
    }
}