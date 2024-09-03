package org.example;

import io.restassured.response.Response;
import org.example.backendPlayground.api.UserService;
import org.example.backendPlayground.db.UserDb;
import org.example.backendPlayground.domain.PaginatedUserResponse;
import org.example.backendPlayground.domain.User;
import org.example.backendPlayground.scenarioBuilders.UserScenario;
import org.example.core.TestBase;
import org.example.core.utilities.RandomData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

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
	public void testGetUserByInvalidId() {
		// SETUP
		Long userId = Long.MAX_VALUE;

		// ACTION UNDER TEST & VERIFY
		UserService.getById(userId).tryCall()
				.then()
				.statusCode(404)
				.body(equalTo("User with id " + userId + " does not exist"));
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

	@DataProvider(name = "MissingRequiredFieldScenarios")
	public Object[][] getMissingRequiredFieldScenarios() {
		List<Object[]> data = new ArrayList<>();
		String scenario;
		UserScenario userScenario;
		String expectedErrorMessage;

		//------------------------------------------------
		scenario = "Missing first name";
		//------------------------------------------------
		userScenario = new UserScenario().withDefaults().withFirstName(null);
		expectedErrorMessage = "User must have a first name";
		data.add(new Object[]{ scenario, userScenario, expectedErrorMessage });

		//------------------------------------------------
		scenario = "Missing last name";
		//------------------------------------------------
		userScenario = new UserScenario().withDefaults().withLastName(null);
		expectedErrorMessage = "User must have a last name";
		data.add(new Object[]{ scenario, userScenario, expectedErrorMessage });

		//------------------------------------------------
		scenario = "Missing email";
		//------------------------------------------------
		userScenario = new UserScenario().withDefaults().withEmail(null);
		expectedErrorMessage = "User must have an email";
		data.add(new Object[]{ scenario, userScenario, expectedErrorMessage });

		return data.toArray(new Object[][]{});
	}

	@Test(dataProvider = "MissingRequiredFieldScenarios")
	public void testCreateMissingRequiredField(String scenario, UserScenario userScenario, String expectedErrorMessage) {

		// ACTION UNDER TEST & VERIFY
		UserService.create(userScenario.getAsUser()).tryCall()
				.then()
				.statusCode(400)
				.body(equalTo(expectedErrorMessage));
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

	@Test
	public void testUpdateUser() {
		// SETUP
		UserScenario userScenario = new UserScenario().create();
		User user = userScenario.getAsUser();
		user.setEmail(RandomData.en.internet().emailAddress());

		// ACTION UNDER TEST
		User updatedUser = UserService.update(userScenario.getId(), user).call();

		// VERIFY
		assertThat(updatedUser).isEqualTo(user);
	}

	@Test
	public void testUpdateUserToDuplicate() {
		// SETUP
		UserScenario userScenario1 = new UserScenario().create();
		UserScenario userScenario2 = new UserScenario().create();

		User user1 = userScenario1.getAsUser();

		// ACTION UNDER TEST & VERIFY
		UserService.update(userScenario2.getId(), user1).tryCall()
				.then()
				.statusCode(400)
				.body(equalTo("User with same first name, last name and email already exists"));
	}

	@Test
	public void testDeleteUser() {
		// SETUP
		UserScenario userScenario = new UserScenario().create();

		// ACTION UNDER TEST
		UserService.delete(userScenario.getId()).call();

		// VERIFY
		assertThat(UserDb.getById(userScenario.getId())).isNull();
	}

	@Test
	public void testDeleteUserWithPosts() {
		// SETUP
		UserScenario userScenario = new UserScenario().withNumberOfPosts(1).create();

		// ACTION UNDER TEST
		Response response = UserService.delete(userScenario.getId()).tryCall();

		// VERIFY
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(response.body().asString()).isEqualTo("User with id %s has posts and cannot be deleted".formatted(userScenario.getId()));
	}

}
