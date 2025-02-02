package de.aygul.minicms.service;

import de.aygul.minicms.exception.CategoryAlreadyExistException;
import de.aygul.minicms.exception.CategoryNotEmptyException;
import de.aygul.minicms.exception.CategoryNotFoundException;
import de.aygul.minicms.model.Category;
import de.aygul.minicms.model.CategoryDTO;
import de.aygul.minicms.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long createCategory(CategoryDTO categoryDTO) {
        categoryRepository.findByCategoryName(categoryDTO.getCategoryName()).ifPresent(category -> {
            throw new CategoryAlreadyExistException(category.getCategoryName());
        });
        Category category = new Category(null, categoryDTO.getCategoryName(), new ArrayList<>());
        categoryRepository.save(category);
        return category.getId();
    }

    public void deleteCategory(Long id) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(
                "Category with id: " + id + " not exists"));
        if (!existingCategory.getBlogPosts().isEmpty()) {
            throw new CategoryNotEmptyException("Category id: " + id+".");
        }
        categoryRepository.deleteById(id);
    }
}
