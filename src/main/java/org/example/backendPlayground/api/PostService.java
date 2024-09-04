package org.example.backendPlayground.api;

import com.google.gson.reflect.TypeToken;
import org.example.backendPlayground.domain.Post;
import org.example.core.api.CreateApi;
import org.example.core.api.DeleteApi;
import org.example.core.api.GetAllApi;
import org.example.core.api.GetSingleApi;
import org.example.core.api.UpdateApi;
import org.example.core.configuration.Configuration;

import java.util.List;

public final class PostService {
	private static final String BASE_URL = Configuration.BASE_POST_SERVICE_URL + "users/%s/posts";
	private static final String DIRECT_BASE_URL = Configuration.BASE_POST_SERVICE_URL + "posts";

	private PostService() {	}

	public static CreateApi<Post> create(Long userId, Object post) {
		return new CreateApi<Post>(BASE_URL, Post.class).withBody(post).withParentId(userId);
	}

	public static GetSingleApi<Post> getById(Long id) {
		return new GetSingleApi<Post>(DIRECT_BASE_URL, Post.class).withId(id);
	}

	public static GetAllApi<Post> getAllForUser(Long userId) {
		return new GetAllApi<Post>(BASE_URL, TypeToken.getParameterized(List.class, Post.class).getType())
				.withParentId(userId);
	}

	public static UpdateApi<Post> update(Long userId, Long postId, Post post) {
		return new UpdateApi<Post>(BASE_URL, Post.class).withParentId(userId).withId(postId).withBody(post);
	}

	public static DeleteApi delete(Long userId, Long postId) {
		return new DeleteApi(BASE_URL).withParentId(userId).withId(postId);
	}

}
