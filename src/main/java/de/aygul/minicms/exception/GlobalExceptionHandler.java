package de.aygul.minicms.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(BlogPostIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleBlogPostNotFound(BlogPostIdNotFoundException blogPostIdNotFoundException) {
       logger.warn(blogPostIdNotFoundException.getMessage(),blogPostIdNotFoundException);
        return new ErrorMessage(blogPostIdNotFoundException.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleCategoryNotFound(CategoryNotFoundException categoryNotFoundException) {
        logger.warn(categoryNotFoundException.getMessage(),categoryNotFoundException);
        return new ErrorMessage(categoryNotFoundException.getMessage());
    }

    @ExceptionHandler(CategoryAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleCategoryExist(CategoryAlreadyExistException categoryAlreadyExistException) {
        logger.warn(categoryAlreadyExistException.getMessage(), categoryAlreadyExistException);
        return new ErrorMessage(categoryAlreadyExistException.getMessage());
    }
}
