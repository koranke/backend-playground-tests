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

public class UserTests extends TestBase {

	@Test
	public void testGetUserById() {
		UserScenario userScenario = new UserScenario().create();
		User user = UserService.getById().call(userScenario.getId().toString());

		assertThat(user).isEqualTo(userScenario.getAsUser());
	}

	@Test
	public void testGetAllUsers() {
		new UserScenario().create();
		PaginatedUserResponse users = UserService.getAll().call();

		assertThat(users.getCurrentPage()).isEqualTo(0);
		assertThat(users.getNumberOfItems()).isGreaterThan(0);
		assertThat(users.getUsers().size()).isGreaterThan(0);
	}


	@Test
	public void testCreateWithAllFields() {
		UserScenario userScenario = new UserScenario()
				.withPhone(RandomData.en.phoneNumber().phoneNumber())
				.withDateOfBirth(RandomData.getRandomPastDate(6000))
				.create();

		User savedUser = UserDb.getById(userScenario.getId());
		assertThat(savedUser).isEqualTo(userScenario.getAsUser());
	}

	@Test
	public void testCreateMissingRequiredField() {
		UserScenario userScenario = new UserScenario().withDefaults();
		userScenario.setFirstName(null);

		UserService.create().tryCall(userScenario.getAsUser())
				.then()
				.statusCode(400);
	}

	@Test
	public void testDuplicateUser() {
		UserScenario userScenario = new UserScenario().create();

		User newUser = userScenario.getAsUser();
		newUser.setId(null);
		newUser.setPhone("123-456-7890");

		UserService.create().tryCall(newUser)
				.then()
				.statusCode(400);
	}

}
