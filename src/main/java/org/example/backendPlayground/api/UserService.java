package org.example.backendPlayground.api;

import org.example.backendPlayground.domain.PaginatedUserResponse;
import org.example.backendPlayground.domain.User;
import org.example.core.api.CreateApi;
import org.example.core.api.DeleteApi;
import org.example.core.api.GetSingleApi;
import org.example.core.api.UpdateApi;
import org.example.core.configuration.Configuration;

public final class UserService {
	private static final String BASE_URL = Configuration.BASE_USER_SERVICE_URL + "users";

	private UserService() {	}

	public static CreateApi<User> create(User user) {
		return new CreateApi<User>(BASE_URL, User.class).withBody(user);
	}

	public static GetSingleApi<User> getById(Long id) {
		return new GetSingleApi<User>(BASE_URL, User.class).withId(id);
	}

	public static GetSingleApi<PaginatedUserResponse> getAll() {
		return new GetSingleApi<>(BASE_URL, PaginatedUserResponse.class);
	}

	public static UpdateApi<User> update(Long id, User user) {
		return new UpdateApi<User>(BASE_URL, User.class).withId(id).withBody(user);
	}

	public static DeleteApi delete(Long id) {
		return new DeleteApi(BASE_URL).withId(id);
	}

}
