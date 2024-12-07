package org.example.backendPlayground.api;

import io.restassured.http.Method;
import org.example.backendPlayground.domain.PaginatedUserResponse;
import org.example.backendPlayground.domain.User;
import org.example.core.api.ReturnNone;
import org.example.core.api.ReturnSingle;
import org.example.core.configuration.Configuration;

public final class UserService {
	private static final String BASE_URL = Configuration.BASE_USER_SERVICE_URL + "users";

	private UserService() {	}

	public static ReturnSingle<User> create(User user) {
		return new ReturnSingle<>(BASE_URL, User.class)
				.withMethod(Method.POST)
				.withBody(user);
	}

	public static ReturnSingle<User> getById(Long id) {
		return new ReturnSingle<>(BASE_URL, User.class)
				.withId(id);
	}

	public static ReturnSingle<PaginatedUserResponse> getAll() {
		return new ReturnSingle<>(BASE_URL, PaginatedUserResponse.class);
	}

	public static ReturnSingle<User> update(Long id, User user) {
		return new ReturnSingle<>(BASE_URL, User.class)
				.withMethod(Method.PUT)
				.withId(id)
				.withBody(user);
	}

	public static ReturnNone<User> delete(Long id) {
		return new ReturnNone<User>(BASE_URL)
				.withMethod(Method.DELETE)
				.withId(id);
	}

}
