package de.aygul.minicms.mediator;

import de.aygul.minicms.model.Category;

public interface ApplicationMediator {
    Category resolveCategoryByName(String categoryName);
}

