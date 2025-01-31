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
	private static final String BASE_URL = Configuration.BASE_POST_SERVICE_URL + "users/{userId}/posts";
	private static final String BY_ID_URL = Configuration.BASE_POST_SERVICE_URL + "users/{userId}/posts/{postId}";
	private static final String DIRECT_BASE_URL = Configuration.BASE_POST_SERVICE_URL + "posts/{postId}";
	private static final String userIdKey = "userId";
	private static final String postIdKey = "postId";


	private PostService() {	}

	public static ReturnSingle<Post> create(Long userId, Object post) {
		return new ReturnSingle<>(Method.POST, BASE_URL, Post.class)
				.withBody(post)
				.withPathParameter(userIdKey, userId.toString());
	}

	public static ReturnSingle<Post> getById(Long id) {
		return new ReturnSingle<>(Method.GET, DIRECT_BASE_URL, Post.class)
				.withPathParameter(postIdKey, id.toString());
	}

	public static ReturnList<Post> getAllForUser(Long userId) {
		return new ReturnList<Post>(Method.GET, BASE_URL, TypeToken.getParameterized(List.class, Post.class).getType())
				.withPathParameter(userIdKey, userId.toString());
	}

	public static ReturnSingle<Post> update(Long userId, Long postId, Post post) {
		return new ReturnSingle<>(Method.PUT, BY_ID_URL, Post.class)
				.withPathParameter(userIdKey, userId.toString())
				.withPathParameter(postIdKey, postId.toString())
				.withBody(post);
	}

	public static ReturnNone delete(Long userId, Long postId) {
		return new ReturnNone(Method.DELETE, BY_ID_URL)
				.withPathParameter(userIdKey, userId.toString())
				.withPathParameter(postIdKey, postId.toString())
				;
	}

}
