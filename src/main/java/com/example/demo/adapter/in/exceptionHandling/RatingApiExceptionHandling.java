package com.example.demo.adapter.in.exceptionHandling;

import com.example.demo.adapter.in.web.InappropriateAccountNumberException;
import com.example.demo.adapter.in.web.RateLimitController;
import com.example.demo.adapter.in.web.RatingsController;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.exceptions.RateLimitNotFoundException;
import com.example.demo.exceptions.RatingsNotFoundException;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestControllerAdvice(assignableTypes = { RatingsController.class })
public class RatingApiExceptionHandling {

	@ExceptionHandler(RatingsNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionModel handleRatingsNotFoundException(RatingsNotFoundException ex) {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setCode("404");
		exceptionModel.setErr(ex.getMessage());
		
		return exceptionModel;
	}
	
	@ExceptionHandler(PoliticianNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ExceptionModel> handlePoliticianNotFoundException(PoliticianNotFoundException e) {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("404");
		exceptionModel.setErr(e.getMessage());
		
		return new ResponseEntity<ExceptionModel>(exceptionModel, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserRateLimitedOnPoliticianException.class)
	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	public ResponseEntity<EntityModel<ExceptionModel>> handleRateLimitedException(UserRateLimitedOnPoliticianException e) throws UserRateLimitedOnPoliticianException {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("429");
		exceptionModel.setErr(e.getMessage());
		exceptionModel.setOptional("This endpoint only allows one request per week on certain politicians");

		EntityModel<ExceptionModel> response = EntityModel.of(exceptionModel)
				.add(linkTo(methodOn(RateLimitController.class)
						.findRateLimitOnCurrentUser(e.polNumber.politicianNumber(), null))
						.withRel("rate-limit"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Retry-After", e.getDaysLeft().toString() + " days");
		return new ResponseEntity<EntityModel<ExceptionModel>>(response, headers, HttpStatus.TOO_MANY_REQUESTS);
	}
	
	@ExceptionHandler(RateLimitNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ExceptionModel> handleRateLimitNotFoundException(RateLimitNotFoundException e) {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("404");
		exceptionModel.setErr(e.getMessage());
		
		return new ResponseEntity<ExceptionModel>(exceptionModel, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InappropriateAccountNumberException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleInvalidAccountNumber(InappropriateAccountNumberException e) throws JsonProcessingException {
		Map<String, String> body = new HashMap<>();
		body.put("reason", "Inappropriate account number given");
		body.put("action", "Check appropriate account numbers for valid account numbers");

		String response = new ObjectMapper().writeValueAsString(body);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
}
