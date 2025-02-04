package de.aygul.minicms.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleException(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return new ErrorMessage("Internal Server Error");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
logger.warn(httpMessageNotReadableException.getMessage(),httpMessageNotReadableException);
        return new ErrorMessage("Invalid request body.");
    }

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

    @ExceptionHandler(CategoryNotEmptyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleCategoryExist(CategoryNotEmptyException categoryNotEmptyException) {
        logger.warn(categoryNotEmptyException.getMessage(), categoryNotEmptyException);
        return new ErrorMessage(categoryNotEmptyException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {
        logger.warn(methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);
        List<String> errorMessages = methodArgumentNotValidException.getBindingResult()
                                              .getFieldErrors()
                                              .stream()
                                              .map(fieldError -> fieldError.getField() + ": "
                                                                 + fieldError.getDefaultMessage())
                                              .toList();
        String errorMessage = String.join(", ", errorMessages);
        return new ErrorMessage("Validation failed: " + errorMessage);
    }
}
