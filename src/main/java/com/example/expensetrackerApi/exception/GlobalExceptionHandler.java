package com.example.expensetrackerApi.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.expensetrackerApi.entity.ErrorObject;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorObject>  handleExpenseNotFoundException(ResourceNotFoundException ex, WebRequest req) {
		ErrorObject errorObject = new ErrorObject();
	
		errorObject.setMessage(ex.getMessage());
		
		errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
		
		errorObject.setTimestamp(new Date());
		
		return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorObject>  handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest req) {
		ErrorObject errorObject = new ErrorObject();
	
		errorObject.setMessage(ex.getMessage());
		
		errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		
		errorObject.setTimestamp(new Date());
		
		return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorObject>  Exception(Exception ex, WebRequest req) {
		ErrorObject errorObject = new ErrorObject();
	
		errorObject.setMessage(ex.getMessage());
		
		errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		errorObject.setTimestamp(new Date());
		
		return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(ItemAlreadyExistsException.class)
	public ResponseEntity<ErrorObject>  handleItemALreadyExistsException(ItemAlreadyExistsException ex, WebRequest req) {
		ErrorObject errorObject = new ErrorObject();
	
		errorObject.setMessage(ex.getMessage());
		
		errorObject.setStatusCode(HttpStatus.CONFLICT.value());
		
		errorObject.setTimestamp(new Date());
		
		return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.CONFLICT);

	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorObject>  IllegalArgumentException(IllegalArgumentException ex, WebRequest req) {
		ErrorObject errorObject = new ErrorObject();
	
		errorObject.setMessage(ex.getMessage());
		
		errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		
		errorObject.setTimestamp(new Date());
		
		return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.BAD_REQUEST);

	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("timestamp", new Date());
		
		body.put("statusCode", HttpStatus.BAD_REQUEST.value());
		
		List<String> errors = ex.getBindingResult()
			.getFieldErrors()
				.stream()
					.map(x -> x.getDefaultMessage())
					.collect(Collectors.toList());
		
//		.stream() converts the result to an array which is further mapped and default message is filtered
//		in the next step .collect makes the collection of all the results to a list
		
		body.put("messages", errors);
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
}
