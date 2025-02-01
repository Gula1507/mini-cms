package de.aygul.minicms.exception;

public class BlogPostIdNotFoundException extends RuntimeException {
    public BlogPostIdNotFoundException(Long id) {
        super("Blog post not found with id: " + id);
    }
}
