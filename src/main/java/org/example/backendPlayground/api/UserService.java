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
		return new ReturnSingle<>(Method.POST, BASE_URL, User.class)
				.withBody(user);
	}

	public static ReturnSingle<User> getById(Long id) {
		return new ReturnSingle<>(Method.GET, BY_ID_URL, User.class)
				.withPathParameter(userIdKey, id.toString());
	}

	public static ReturnSingle<PaginatedUserResponse> getAll() {
		return new ReturnSingle<>(Method.GET, BASE_URL, PaginatedUserResponse.class);
	}

	public static ReturnSingle<User> update(Long id, User user) {
		return new ReturnSingle<>(Method.PUT, BY_ID_URL, User.class)
				.withPathParameter(userIdKey, id.toString())
				.withBody(user);
	}

	public static ReturnNone delete(Long id) {
		return new ReturnNone(Method.DELETE, BY_ID_URL)
				.withPathParameter(userIdKey, id.toString());
	}

}
