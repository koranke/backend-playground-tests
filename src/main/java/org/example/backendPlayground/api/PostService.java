package org.example.backendPlayground.api;

import com.google.gson.reflect.TypeToken;
import org.example.backendPlayground.domain.Post;
import org.example.core.api.CreateWithParentIdApi;
import org.example.core.api.GetAllWithParentIdApi;
import org.example.core.api.GetByIdApi;
import org.example.core.configuration.Configuration;

import java.util.List;

public class PostService {
	private static final String BASE_URL = Configuration.BASE_POST_SERVICE_URL + "users/%s/posts";
	private static final String DIRECT_BASE_URL = Configuration.BASE_POST_SERVICE_URL + "posts";

	public static CreateWithParentIdApi<Post> create() {
		return new CreateWithParentIdApi<>(BASE_URL, Post.class);
	}

	public static GetByIdApi<Post> getById() {
		return new GetByIdApi<>(DIRECT_BASE_URL, Post.class);
	}

	public static GetAllWithParentIdApi<Post> getAllForUser() {
		return new GetAllWithParentIdApi<>(BASE_URL, TypeToken.getParameterized(List.class, Post.class).getType());
	}

}
