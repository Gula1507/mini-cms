package de.aygul.minicms.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super("The category is not found. "+ message );
    }
}
