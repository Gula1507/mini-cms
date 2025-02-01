package de.aygul.minicms.model;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogPostRequestDTO {
    @NotBlank(message = "Title must not be blank")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Body must not be blank")
    @Size(min = 10, message = "Body must be at least 10 characters long")
    private String body;

    @NotBlank(message = "Author must not be blank")
    @Size(min = 3, message = "Author name must be at least 3 characters long")
    private String author;

    @NotNull(message = "Categories must not be null")
    private List<CategoryDTO> categoriesDTO;
}
