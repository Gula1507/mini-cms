package de.aygul.minicms.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {

    @NotBlank(message = "Category name must not be blank")
    @Size(min = 3, message = "Category name must be at least 3 characters long")
    private String categoryName;
}
