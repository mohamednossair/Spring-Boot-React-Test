package com.hoaxify.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoaxify.error.ApiError;
import com.hoaxify.models.User;
import com.hoaxify.services.UserService;
import com.hoaxify.shared.GenericResponse;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/api/1.0/users")
	public GenericResponse createUser(@Valid @RequestBody User user) {

		userService.save(user);
		return new GenericResponse("Saved");
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ApiError handelValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
		var apiError = new ApiError(400, "Validation Error", request.getServletPath());
		var result = exception.getBindingResult();
		Map<String, String> validationErrors = new HashMap<>();
		for (FieldError fieldError : result.getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		apiError.setValidationErrors(validationErrors);

		return apiError;
	}

}
