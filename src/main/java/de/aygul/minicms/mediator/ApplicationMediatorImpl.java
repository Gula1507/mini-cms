package de.aygul.minicms.mediator;

import de.aygul.minicms.model.Category;
import de.aygul.minicms.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationMediatorImpl implements ApplicationMediator{

    private final CategoryRepository categoryRepository;

    @Override
    public Category resolveCategoryByName(String categoryName) {

        return categoryRepository.findByCategoryName(categoryName)
                                 .orElseThrow(() -> new RuntimeException("Kategorie nicht gefunden: " + categoryName));
    }
}
