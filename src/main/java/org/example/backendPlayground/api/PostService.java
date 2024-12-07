package org.example.backendPlayground.api;

import com.google.gson.reflect.TypeToken;
import io.restassured.http.Method;
import org.example.backendPlayground.domain.Post;
import org.example.core.api.ReturnList;
import org.example.core.api.ReturnNone;
import org.example.core.api.ReturnSingle;
import org.example.core.configuration.Configuration;

import java.util.List;

public final class PostService {
	private static final String BASE_URL = Configuration.BASE_POST_SERVICE_URL + "users/%s/posts";
	private static final String DIRECT_BASE_URL = Configuration.BASE_POST_SERVICE_URL + "posts";

	private PostService() {	}

	public static ReturnSingle<Post> create(Long userId, Object post) {
		return new ReturnSingle<>(BASE_URL, Post.class)
				.withMethod(Method.POST)
				.withBody(post)
				.withParentId(userId);
	}

	public static ReturnSingle<Post> getById(Long id) {
		return new ReturnSingle<>(DIRECT_BASE_URL, Post.class)
				.withId(id);
	}

	public static ReturnList<Post> getAllForUser(Long userId) {
		return new ReturnList<Post>(BASE_URL, TypeToken.getParameterized(List.class, Post.class).getType())
				.withParentId(userId);
	}

	public static ReturnSingle<Post> update(Long userId, Long postId, Post post) {
		return new ReturnSingle<>(BASE_URL, Post.class).withParentId(userId)
				.withMethod(Method.PUT)
				.withId(postId)
				.withBody(post);
	}

	public static ReturnNone<Post> delete(Long userId, Long postId) {
		return new ReturnNone<Post>(BASE_URL)
				.withMethod(Method.DELETE)
				.withParentId(userId)
				.withId(postId);
	}

}
