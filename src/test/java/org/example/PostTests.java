package org.example;

import org.example.backendPlayground.api.PostService;
import org.example.backendPlayground.db.PostDb;
import org.example.backendPlayground.domain.Post;
import org.example.backendPlayground.enums.PostVisibility;
import org.example.backendPlayground.scenarioBuilders.PostScenario;
import org.example.backendPlayground.scenarioBuilders.UserScenario;
import org.example.core.TestBase;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
}
