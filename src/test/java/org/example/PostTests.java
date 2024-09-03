package org.example;

import org.example.backendPlayground.api.PostService;
import org.example.backendPlayground.db.PostDb;
import org.example.backendPlayground.domain.Post;
import org.example.backendPlayground.enums.PostVisibility;
import org.example.backendPlayground.scenarioBuilders.PostScenario;
import org.example.backendPlayground.scenarioBuilders.UserScenario;
import org.example.core.TestBase;
import org.example.core.utilities.ObjectHelper;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PostTests extends TestBase {

	@Test
	public void testCreatePost() {
		// SETUP
		Long userId = new UserScenario().create().getId();
		Post userPost = new PostScenario().withDefaults().withUserId(userId).getAsPost();

		// ACTION UNDER TEST
		Post post = PostService.create(userId, userPost).call();

		// VERIFY
		Post savedPost = PostDb.getById(post.getId());
		assertThat(savedPost)
				.usingRecursiveComparison()
				.ignoringFields("dateCreated", "dateUpdated")
				.isEqualTo(post);
	}

	@Test
	public void testCreateWithInvalidVisibility() {
		// SETUP
		Long userId = new UserScenario().create().getId();
		String userPost = new PostScenario().withDefaults().withUserId(userId).getAsPost().toJson();
		userPost = userPost.replace("PUBLIC", "INVALID_VISIBILITY");

		// ACTION UNDER TEST && VERIFY
		PostService.create(userId, userPost).tryCall()
				.then()
				.statusCode(400)
				.body(equalTo("Invalid request body: JSON parse error: Cannot deserialize value of type `com.example.backendPlayground.enums.PostVisibility` from String \"INVALID_VISIBILITY\": not one of the values accepted for Enum class: [PUBLIC, PRIVATE, SHARED]"));
	}

	@Test
	public void testGetPostById() {
		// SETUP
		UserScenario userScenario = new UserScenario().withNumberOfPosts(1).create();
		Long postId = userScenario.getPostScenarios().get(0).getPostId();

		// ACTION UNDER TEST
		Post post = PostService.getById(postId).call();

		// VERIFY
		Post savedPost = PostService.getById(postId).call();
		assertThat(savedPost)
				.usingRecursiveComparison()
				.ignoringFields("dateCreated", "dateUpdated")
				.isEqualTo(post);
	}

	@Test
	public void testGetInvalidPostById() {
		// SETUP
		Long postId = Long.MAX_VALUE;

		// ACTION UNDER TEST && VERIFY
		PostService.getById(postId).tryCall()
				.then()
				.statusCode(404)
				.body(equalTo("Post with id " + postId + " does not exist"));
	}

	@Test
	public void testGetAllPostsForUser() {
		// SETUP
		UserScenario userScenario = new UserScenario().withNumberOfPosts(2).create();
		Long userId = userScenario.getId();

		// ACTION UNDER TEST & VERIFY
		assertThat(PostService.getAllForUser(userId).call().size()).isEqualTo(2);
	}

	@Test
	public void testGetFilteredPostsForUser() {
		// SETUP
		UserScenario userScenario = new UserScenario()
				.withPost(new PostScenario().withDefaults().withPostVisibility(PostVisibility.PUBLIC))
				.withPost(new PostScenario().withDefaults().withPostVisibility(PostVisibility.PRIVATE))
				.create();

		Long userId = userScenario.getId();

		// ACTION UNDER TEST
		List<Post> posts = PostService.getAllForUser(userId)
				.withQueryParameter("visibility", PostVisibility.PUBLIC.toString())
				.call();

		// VERIFY
		assertThat(posts.size()).isEqualTo(1);
		assertThat(posts.get(0))
				.usingRecursiveComparison()
				.ignoringFields("dateCreated", "dateUpdated")
				.isEqualTo(userScenario.getPostScenarios().get(0).getAsPost());
	}

	@Test
	public void testUpdatePost() {
		// SETUP
		UserScenario userScenario = new UserScenario().withNumberOfPosts(1).create();
		Post originalPost = PostDb.getById(userScenario.getPostScenarios().get(0).getPostId());
		Post updatedPost = ObjectHelper.clone(originalPost);
		updatedPost.setTitle("Updated title");
		updatedPost.setDateCreated(null);
		updatedPost.setDateUpdated(null);

		// ACTION UNDER TEST
		PostService.update(userScenario.getId(), originalPost.getId(), updatedPost).call();

		// VERIFY
		Post savedPost = PostDb.getById(originalPost.getId());
		assertThat(savedPost)
				.usingRecursiveComparison()
				.ignoringFields("dateCreated", "dateUpdated")
				.isEqualTo(updatedPost);
		assertThat(savedPost.getDateUpdated()).isAfter(originalPost.getDateUpdated());
	}

	@Test
	public void testDeletePost() {
		// SETUP
		UserScenario userScenario = new UserScenario().withNumberOfPosts(1).create();
		Post post = PostDb.getById(userScenario.getPostScenarios().get(0).getPostId());

		// ACTION UNDER TEST
		PostService.delete(userScenario.getId(), post.getId()).call();

		// VERIFY
		assertThat(PostDb.getById(post.getId())).isNull();
	}

	@Test
	public void testDeleteWithInvalidPostId() {
		// SETUP
		UserScenario userScenario = new UserScenario().create();
		Long postId = Long.MAX_VALUE;

		// ACTION UNDER TEST && VERIFY
		PostService.delete(userScenario.getId(), postId).tryCall()
				.then()
				.statusCode(404)
				.body(equalTo("Post with id " + postId + " does not exist"));
	}

	@Test
	public void testDeleteWithInvalidUserId() {
		// SETUP
		UserScenario userScenario = new UserScenario().withNumberOfPosts(1).create();
		Long userId = Long.MAX_VALUE;
		Long postId = userScenario.getPostScenarios().get(0).getPostId();

		// ACTION UNDER TEST && VERIFY
		PostService.delete(userId, postId).tryCall()
				.then()
				.statusCode(404)
				.body(equalTo("User with id " + userId + " does not exist"));
	}
}
