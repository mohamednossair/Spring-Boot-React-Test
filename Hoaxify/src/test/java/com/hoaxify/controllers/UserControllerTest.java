package com.hoaxify.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.hoaxify.error.ApiError;
import com.hoaxify.models.User;
import com.hoaxify.models.UserRepository;
import com.hoaxify.shared.GenericResponse;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
class UserControllerTest {

	private static final String API_1_0_USERS = "/api/1.0/users";

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	UserRepository userRepository;

	@BeforeEach
	public void cleanUp() {
		log.info("Clean All Data");
		userRepository.deleteAll();
	}

	public <T> ResponseEntity<T> postSingUp(Object request, Class<T> response) {
		return testRestTemplate.postForEntity(API_1_0_USERS, request, response);
	}

	private User createValidUser() {
		User user = new User();
		user.setUserName("test-user");
		user.setDisplayName("test-display");
		user.setPassword("P4sswords");
		return user;
	}

	@Test
	void postUser_whenUserIsVallid_reciveOk() {
		User user = createValidUser();
		ResponseEntity<Object> response = postSingUp(user, Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void postUser_whenUserIsVallid_UserSaveToDatabase() {
		User user = createValidUser();

		postSingUp(user, Object.class);
		assertThat(userRepository.count()).isEqualTo(1);
	}

	@Test
	void postUser_whenUserIsVallid_reciveSuccessMessage() {
		User user = createValidUser();

		ResponseEntity<GenericResponse> postSingUp = postSingUp(user, GenericResponse.class);
		assertThat(postSingUp.getBody().getMessage()).isNotNull();
	}

	@Test
	void postUser_whenUserNameisNull_reciveBadRequest() {
		User user = createValidUser();
		user.setUserName(null);
		ResponseEntity<Object> postSingUp = postSingUp(user, Object.class);
		assertThat(postSingUp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void postUser_whenUserNamewithLessThanRequired_reciveBadRequest() {
		User user = createValidUser();
		user.setUserName("abc");
		ResponseEntity<Object> postSingUp = postSingUp(user, Object.class);
		assertThat(postSingUp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void postUser_whenDisplayNamewithLessThanRequired_reciveBadRequest() {
		User user = createValidUser();
		user.setDisplayName("abc");
		ResponseEntity<Object> postSingUp = postSingUp(user, Object.class);
		assertThat(postSingUp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void postUser_whenPasswordwithLessThanRequired_reciveBadRequest() {
		User user = createValidUser();
		user.setPassword("abc");
		ResponseEntity<Object> postSingUp = postSingUp(user, Object.class);
		assertThat(postSingUp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void postUser_whenUserNameExceedRequiredLenght_reciveBadRequest() {
		User user = createValidUser();
		String val = IntStream.rangeClosed(0, 256).mapToObj(c -> "a").collect(Collectors.joining());
		user.setUserName(val);
		ResponseEntity<Object> postSingUp = postSingUp(user, Object.class);
		assertThat(postSingUp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void postUser_whenDisplayNameExceedRequiredLenght_reciveBadRequest() {
		User user = createValidUser();
		String val = IntStream.rangeClosed(0, 256).mapToObj(c -> "a").collect(Collectors.joining());
		user.setDisplayName(val);
		ResponseEntity<Object> postSingUp = postSingUp(user, Object.class);
		assertThat(postSingUp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void postUser_whenPasswordExceedRequiredLenght_reciveBadRequest() {
		User user = createValidUser();
		String val = IntStream.rangeClosed(0, 256).mapToObj(c -> "a").collect(Collectors.joining());
		user.setPassword(val);
		ResponseEntity<Object> postSingUp = postSingUp(user, Object.class);
		assertThat(postSingUp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void postUser_whenPasswordAllLowers_reciveBadRequest() {
		User user = createValidUser();
		user.setPassword("alllowers");
		ResponseEntity<Object> postSingUp = postSingUp(user, Object.class);
		assertThat(postSingUp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void postUser_whenPasswordAllUpperCase_reciveBadRequest() {
		User user = createValidUser();
		user.setPassword("ALLUPPERCASE");
		ResponseEntity<Object> postSingUp = postSingUp(user, Object.class);
		assertThat(postSingUp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void postUser_whenPasswordAllNumber_reciveBadRequest() {
		User user = createValidUser();
		user.setPassword("123345687");
		ResponseEntity<Object> postSingUp = postSingUp(user, Object.class);
		assertThat(postSingUp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void postUser_whenDisplayNameisNull_reciveBadRequest() {
		User user = createValidUser();
		user.setDisplayName(null);
		ResponseEntity<Object> postSingUp = postSingUp(user, Object.class);
		assertThat(postSingUp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void postUser_whenPasswordisNull_reciveBadRequest() {
		User user = createValidUser();
		user.setPassword(null);
		ResponseEntity<Object> postSingUp = postSingUp(user, Object.class);
		assertThat(postSingUp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void postUser_whenUserIsValid_passwordIsHashedInDatabase() {
		User user = createValidUser();

		postSingUp(user, Object.class);
		List<User> findAll = userRepository.findAll();
		assertThat(findAll.get(0).getPassword()).isNotEqualTo(user.getPassword());
	}

	@Test
	void PostUser_WhenUserIsInvalidReciveApiError() {
		User user = new User();
		ResponseEntity<ApiError> response = postSingUp(user, ApiError.class);
		assertThat(response.getBody().getUrl()).isEqualTo(API_1_0_USERS);

	}

	@Test
	void PostUser_WhenUserIsInvalidReciveApiErrorWithValidationErrors() {
		User user = new User();
		ResponseEntity<ApiError> response = postSingUp(user, ApiError.class);
		assertThat(response.getBody().getValidationErrors().size()).isEqualTo(3);

	}

	@Test
	void PostUser_WhenUserIsNull_reciveMessageErrorUserNameISNULL() {
		User user = new User();
		ResponseEntity<ApiError> response = postSingUp(user, ApiError.class);
		assertThat(response.getBody().getValidationErrors().get("userName")).isEqualTo("Username cannot be null");
	}

	@Test
	void PostUser_WhenUserIsInvalidLength_reciveGenericMessageErrorofSize() {
		User user = createValidUser();
		user.setUserName("abd");
		ResponseEntity<ApiError> response = postSingUp(user, ApiError.class);
		assertThat(response.getBody().getValidationErrors().get("userName"))
				.isEqualTo("It must have minimum 4 and maximum 128 characters");
	}

	@Test
	void PostUser_WhenPasswordIsNull_reciveMessageErrorPasswordISNULL() {
		User user = new User();
		ResponseEntity<ApiError> response = postSingUp(user, ApiError.class);
		assertThat(response.getBody().getValidationErrors().get("password")).isEqualTo("Cannot be null");
	}

	@Test
	void postUser_whenPasswordInvalidPattern_reciveInvalidPasswordMessagePattern() {
		User user = createValidUser();
		user.setPassword("alllowers");
		ResponseEntity<ApiError> postSingUp = postSingUp(user, ApiError.class);
		assertThat(postSingUp.getBody().getValidationErrors().get("password"))
				.isEqualTo("Password must have at least one uppercase, one lowercase letter and one number");
	}

	@Test
	void postUser_whenAnotherUserHasSameUserName_ReciveBadRewquest() {
		userRepository.save(createValidUser());

		User user = createValidUser();
		ResponseEntity<Object> postSingUp = postSingUp(user, Object.class);

		assertThat(postSingUp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	void postUser_WhenAnotherUserHasSameUserName_ReciveMessageDublicationUserName() {
		userRepository.save(createValidUser());
		User user = createValidUser();
		ResponseEntity<ApiError> postSingUp = postSingUp(user, ApiError.class);
		assertThat(postSingUp.getBody().getValidationErrors().get("userName")).isEqualTo("This name is in use");
	}

}
