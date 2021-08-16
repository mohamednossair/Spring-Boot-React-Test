package com.hoaxify.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.hoaxify.models.User;
import com.hoaxify.models.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
	UserRepository userRepository;

	@Test
	void findByUserName_whenUserExist_ReturnUser() {

		User user = new User();
		user.setUserName("test-user");
		user.setDisplayName("test-display");
		user.setPassword("P4sswords");
		testEntityManager.persist(user);

		User findByUserName = userRepository.findByUserName("test-user");
		assertThat(findByUserName).isNotNull();

	}

	@Test
	void findByUserName_whenUserNotExist_RetrunNUll() {
		User findByUserName = userRepository.findByUserName("NotExist_test-user");
		assertThat(findByUserName).isNull();
	}
	
	

}
