package net.nullpointer.xlsxfinder.exception.handler;

import net.nullpointer.xlsxfinder.exception.FileNotFoundException;
import net.nullpointer.xlsxfinder.exception.FileReadException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FileNotFoundException.class)
    public String handleFileNotFound(FileNotFoundException exc) {
        return exc.getMessage();
    }

    @ExceptionHandler(FileReadException.class)
    public String handleFileReadException(FileReadException exc) {
        return exc.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(IllegalArgumentException exc) {
        return "Error on request: " + exc.getMessage();
    }
}
