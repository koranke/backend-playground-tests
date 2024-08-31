package org.example;

import org.example.backendPlayground.api.UserService;
import org.example.backendPlayground.db.UserDb;
import org.example.backendPlayground.domain.PaginatedUserResponse;
import org.example.backendPlayground.domain.User;
import org.example.backendPlayground.scenarioBuilders.UserScenario;
import org.example.core.TestBase;
import org.example.core.utilities.RandomData;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserTests extends TestBase {

	@Test
	public void testGetUserById() {
		// SETUP
		UserScenario userScenario = new UserScenario().create();

		// ACTION UNDER TEST
		User user = UserService.getById(userScenario.getId()).call();

		// VERIFY
		assertThat(user).isEqualTo(userScenario.getAsUser());
	}

	@Test
	public void testGetAllUsers() {
		// SETUP
		new UserScenario().create();

		// ACTION UNDER TEST
		PaginatedUserResponse users = UserService.getAll().call();

		// VERIFY
		assertThat(users.getCurrentPage()).isEqualTo(0);
		assertThat(users.getNumberOfItems()).isGreaterThan(0);
		assertThat(users.getUsers().size()).isGreaterThan(0);
	}


	@Test
	public void testCreateWithAllFields() {
		// SETUP & ACTION UNDER TEST
		UserScenario userScenario = new UserScenario()
				.withPhone(RandomData.en.phoneNumber().phoneNumber())
				.withDateOfBirth(RandomData.getRandomPastDate(6000))
				.create();

		// VERIFY
		User savedUser = UserDb.getById(userScenario.getId());
		assertThat(savedUser).isEqualTo(userScenario.getAsUser());
	}

	@Test
	public void testCreateMissingRequiredField() {
		// SETUP
		UserScenario userScenario = new UserScenario().withDefaults();
		userScenario.setFirstName(null);

		// ACTION UNDER TEST & VERIFY
		UserService.create(userScenario.getAsUser()).tryCall()
				.then()
				.statusCode(400)
				.body(equalTo("User must have a first name"));
	}

	@Test
	public void testDuplicateUser() {
		// SETUP
		UserScenario userScenario = new UserScenario().create();

		User newUser = userScenario.getAsUser();
		newUser.setId(null);
		newUser.setPhone("123-456-7890");

		// ACTION UNDER TEST & VERIFY
		UserService.create(newUser).tryCall()
				.then()
				.statusCode(400)
				.body(equalTo("User with same first name, last name and email already exists"));
	}

}
