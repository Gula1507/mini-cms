package de.aygul.minicms.exception;

public class CategoryAlreadyExistException extends RuntimeException {

    public CategoryAlreadyExistException(String categoryName) {
        super("The category '" + categoryName + "' already exists");
    }
}
