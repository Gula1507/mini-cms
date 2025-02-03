package de.aygul.minicms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.aygul.minicms.dto.BlogPostRequestDTO;
import de.aygul.minicms.dto.BlogPostResponseDTO;
import de.aygul.minicms.dto.CategoryDTO;
import de.aygul.minicms.model.*;
import de.aygul.minicms.repository.BlogPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BlogPostControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BlogPostRepository blogPostRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        blogPostRepository.deleteAll();
    }

    @Test
    @DisplayName("createBlogPost return status 201 and generated ID")
    void createBlogPost() throws Exception {
        BlogPostRequestDTO blogPostRequestDTO = new BlogPostRequestDTO("Valid Title",
                "Valid Body",
                "Valid Author",
                List.of(new CategoryDTO("Tech")));

        BlogPost blogPost = new BlogPost(null,
                "Valid Title",
                "Valid Body",
                "Valid Author",
                LocalDate.now(),
                null,
                List.of(new Category(null, "Tech", new ArrayList<>())));

        blogPostRepository.save(blogPost);
        mockMvc.perform(MockMvcRequestBuilders.post("/blogpost").contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(blogPostRequestDTO)))
               .andExpect(status().isCreated()).andExpect(jsonPath("$").isNumber());

    }

    @Test
    @DisplayName("getAllBlogPosts should return a list of BlogPostResponseDTOs with correct title and status")
    void getAllBlogPosts() throws Exception {
        BlogPost blogPost1 = new BlogPost(null,
                "Title 1",
                "Body 1",
                "Author 1",
                LocalDate.now(),
                BlogPostStatus.PUBLISHED,
                List.of(new Category(null, "Tech", new ArrayList<>())));
        BlogPost blogPost2 = new BlogPost(null,
                "Title 2",
                "Body 2",
                "Author 2",
                LocalDate.now(),
                BlogPostStatus.DRAFT,
                List.of(new Category(null, "Science", new ArrayList<>())));

        blogPostRepository.save(blogPost1);
        blogPostRepository.save(blogPost2);

        mockMvc.perform(get("/blogpost")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$[0].title").value("Title 1")).andExpect(jsonPath("$[1].title").value("Title 2"))
               .andExpect(jsonPath("$[0].blogPostStatus").value("PUBLISHED"))
               .andExpect(jsonPath("$[1].blogPostStatus").value("DRAFT"));
    }

    @Test
    @DisplayName("Should return status 200 and blog post DTO if found")
    void testGetBlogPostByIdSuccess() throws Exception {
        BlogPost blogPost = new BlogPost(null,
                "Valid Title",
                "Valid Body",
                "Valid Author",
                LocalDate.now(),
                BlogPostStatus.DRAFT,
                new ArrayList<>());
        blogPostRepository.save(blogPost);
        Long existingId = blogPost.getId(); // ID des gespeicherten Blogposts

        mockMvc.perform(get("/blogpost/{id}", existingId)).andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(existingId)).andExpect(jsonPath("$.title").value("Valid Title"))
               .andExpect(jsonPath("$.body").value("Valid Body")).andExpect(jsonPath("$.author").value("Valid Author"));
    }

    @Test
    @DisplayName("deleteBlogPost returns 404 when blog post does not exist")
    void deleteBlogPost_notFound() throws Exception {
        mockMvc.perform(delete("/blogpost/{id}", 999L)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("updateBlogPostPartial return BlogPost with updated title and body")
    void updateBlogPostPartialSuccess() throws Exception {

        BlogPostResponseDTO blogPostResponseDTO = new BlogPostResponseDTO(1L,
                "New Title",
                "New Body",
                "Valid Author",
                LocalDate.now(),
                BlogPostStatus.DRAFT,
                new ArrayList<>());

        String title = "New Title";
        String body = "New Body";

        BlogPost blogPost = new BlogPost(null,
                "Valid Title",
                "Valid Body",
                "Valid Author",
                LocalDate.now(),
                BlogPostStatus.DRAFT,
                new ArrayList<>());

        blogPostRepository.save(blogPost);

        mockMvc.perform(patch("/blogpost/1").param("title", title)  // Verwende param() für URL-Parameter
                                            .param("body", body)).andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(blogPostResponseDTO)));
    }
}