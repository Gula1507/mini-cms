package de.aygul.minicms.exception;

public class CategoryNotEmptyException extends RuntimeException {
    public CategoryNotEmptyException(String message) {
        super("Category cannot be deleted because it contains blog posts. "+message);
    }
}
