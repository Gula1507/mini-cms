package de.aygul.minicms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.aygul.minicms.model.*;
import de.aygul.minicms.repository.BlogPostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    @DisplayName("createBlog return status 201 and generated ID")
    void createBlog() throws Exception {
        BlogPostDTO blogPostDTO = new BlogPostDTO("Valid Title",
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
                                              .content(objectMapper.writeValueAsString(blogPostDTO)))
               .andExpect(status().isCreated()).andExpect(jsonPath("$").isNumber());

    }
}