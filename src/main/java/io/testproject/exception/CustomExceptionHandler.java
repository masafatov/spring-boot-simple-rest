package io.testproject.exception;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<CustomException> handleValidationException (
			ValidationException ex) {
		CustomException customExeption = new CustomException(ex.getMessage());
		return new ResponseEntity<>(customExeption,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomException> handleException(Exception ex) {
		CustomException customExeption = new CustomException(ex.getMessage());
		return new ResponseEntity<>(customExeption,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<CustomException> NOT_FOUND(NoHandlerFoundException ex) {
		CustomException customExeption = new CustomException(ex.getMessage());
		return new ResponseEntity<>(customExeption,HttpStatus.NOT_FOUND);
	}
}
