package bg.wow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import bg.wow.exception.ApplicationException;

@RestControllerAdvice
public class GlobalControllerAdvice {
	
	@ExceptionHandler(ApplicationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String handleApplicationExceptions(ApplicationException ex) {
		return ex.getClass().getSimpleName();
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleGeneralExceptions() {
		return "Unexcpected exception";
	}
}
