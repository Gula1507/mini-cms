package de.aygul.minicms.dto;

import de.aygul.minicms.model.BlogPostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogPostResponseDTO {
    private Long id;
    private String title;
    private String body;
    private String author;
    private LocalDate publicationDate;
    private BlogPostStatus blogPostStatus;
    private List<CategoryDTO> categoriesDTO;
}
