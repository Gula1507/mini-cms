package de.aygul.minicms.service;

import de.aygul.minicms.exception.CategoryAlreadyExistException;
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
        categoryRepository.findByCategoryName(categoryDTO.getCategoryName())
                          .ifPresent(category -> { throw new CategoryAlreadyExistException(category.getCategoryName()); });
        Category category = new Category(null, categoryDTO.getCategoryName(), new ArrayList<>());
        categoryRepository.save(category);
        return category.getId();
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category id: " + id + " is not found");
        }
        categoryRepository.deleteById(id);
    }


}
