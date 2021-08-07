package com.hoaxify.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

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
		user.setPassword("P4ssword");
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
	void postUser_whenUserIsValid_passwordIsHashedInDatabase() {
		User user = createValidUser();

		postSingUp(user, Object.class);
		List<User> findAll = userRepository.findAll();
		assertThat(findAll.get(0).getPassword()).isNotEqualTo(user.getPassword());
	}
}
