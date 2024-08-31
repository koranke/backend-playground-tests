package org.example.backendPlayground.api;

import org.example.backendPlayground.domain.PaginatedUserResponse;
import org.example.backendPlayground.domain.User;
import org.example.core.api.CreateApi;
import org.example.core.api.GetByIdApi;
import org.example.core.api.GetPaginatedAllApi;
import org.example.core.configuration.Configuration;

public class UserService {
	private static final String BASE_URL = Configuration.BASE_USER_SERVICE_URL + "users";

	public static CreateApi<User> create(User user) {
		return new CreateApi<User>(BASE_URL, User.class).withBody(user);
	}

	public static GetByIdApi<User> getById(Long id) {
		return new GetByIdApi<User>(BASE_URL, User.class).withId(id);
	}

	public static GetPaginatedAllApi<PaginatedUserResponse> getAll() {
		return new GetPaginatedAllApi<>(BASE_URL, PaginatedUserResponse.class);
	}

}
