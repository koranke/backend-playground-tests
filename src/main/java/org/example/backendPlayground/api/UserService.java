package org.example.backendPlayground.api;

import io.restassured.http.Method;
import org.example.backendPlayground.domain.PaginatedUserResponse;
import org.example.backendPlayground.domain.User;
import org.example.core.api.ReturnNone;
import org.example.core.api.ReturnSingle;
import org.example.core.configuration.Configuration;

public final class UserService {
	private static final String BASE_URL = Configuration.BASE_USER_SERVICE_URL + "users";
	private static final String BY_ID_URL = Configuration.BASE_USER_SERVICE_URL + "users" + "/{userId}";
	private static final String userIdKey = "userId";

	private UserService() {	}

	public static ReturnSingle<User> create(User user) {
		return new ReturnSingle<>(User.class)
				.withMethod(Method.POST)
				.withEndpointUrl(BASE_URL)
				.withBody(user);
	}

	public static ReturnSingle<User> getById(Long id) {
		return new ReturnSingle<>(User.class)
				.withEndpointUrl(BY_ID_URL)
				.withPathParameter(userIdKey, id.toString());
	}

	public static ReturnSingle<PaginatedUserResponse> getAll() {
		return new ReturnSingle<>(PaginatedUserResponse.class)
				.withEndpointUrl(BASE_URL);
	}

	public static ReturnSingle<User> update(Long id, User user) {
		return new ReturnSingle<>(User.class)
				.withEndpointUrl(BY_ID_URL)
				.withMethod(Method.PUT)
				.withPathParameter(userIdKey, id.toString())
				.withBody(user);
	}

	public static ReturnNone<User> delete(Long id) {
		return new ReturnNone<User>()
				.withEndpointUrl(BY_ID_URL)
				.withMethod(Method.DELETE)
				.withPathParameter(userIdKey, id.toString());
	}

}
