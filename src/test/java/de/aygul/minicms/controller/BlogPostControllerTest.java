package de.aygul.minicms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.aygul.minicms.model.BlogPost;
import de.aygul.minicms.model.BlogPostStatus;
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

import java.time.LocalDateTime;
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
    @DisplayName("createBlog save BlogPost with Draft status")
    void createBlog() throws Exception {
        BlogPost blogPost = new BlogPost(null,
                "Sample Blog Post Title",
                "This is the content of the blog post.",
                "Author Name",
                LocalDateTime.now(),
                BlogPostStatus.PUBLISHED,
                List.of());

        blogPostRepository.save(blogPost);

        mockMvc.perform(MockMvcRequestBuilders.post("/blogpost").contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(blogPost)))
               .andExpect(status().isCreated()).andExpect(jsonPath("$.title").value("Sample Blog Post Title"))
               .andExpect(jsonPath("$.id").isNotEmpty())
               .andExpect(jsonPath("$.body").value("This is the content of the blog post."))
               .andExpect(jsonPath("$.author").value("Author Name")).andExpect(jsonPath("$.blogStatus").value("DRAFT"));
    }
}