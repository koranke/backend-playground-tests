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
		Long userId = new UserScenario().create().getId();
		Post post = PostService.create().call(userId.toString(), new PostScenario().withDefaults().withUserId(userId).getAsPost());
		Post savedPost = PostDb.getById(post.getId());
		assertThat(savedPost)
				.usingRecursiveComparison()
				.ignoringFields("dateCreated", "dateUpdated")
				.isEqualTo(post);
	}

	@Test
	public void testGetPostById() {
		UserScenario userScenario = new UserScenario().withNumberOfPosts(1).create();
		Long postId = userScenario.getPostScenarios().get(0).getPostId();

		Post post = PostService.getById().call(postId.toString());

		Post savedPost = PostService.getById().call(postId.toString());
		assertThat(savedPost)
				.usingRecursiveComparison()
				.ignoringFields("dateCreated", "dateUpdated")
				.isEqualTo(post);
	}

	@Test
	public void testGetAllPostsForUser() {
		UserScenario userScenario = new UserScenario().withNumberOfPosts(2).create();
		Long userId = userScenario.getId();

		assertThat(PostService.getAllForUser().call(userId.toString()).size()).isEqualTo(2);
	}

	@Test
	public void testGetFilteredPostsForUser() {
		UserScenario userScenario = new UserScenario()
				.withPost(new PostScenario().withDefaults().withPostVisibility(PostVisibility.PUBLIC))
				.withPost(new PostScenario().withDefaults().withPostVisibility(PostVisibility.PRIVATE))
				.create();

		Long userId = userScenario.getId();
		List<Post> posts = PostService.getAllForUser()
				.withQueryParameter("visibility", PostVisibility.PUBLIC.toString())
				.call(userId.toString());
		assertThat(posts.size()).isEqualTo(1);
		assertThat(posts.get(0))
				.usingRecursiveComparison()
				.ignoringFields("dateCreated", "dateUpdated")
				.isEqualTo(userScenario.getPostScenarios().get(0).getAsPost());
	}
}
