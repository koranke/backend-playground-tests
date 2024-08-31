package org.example.backendPlayground.scenarioBuilders;

import lombok.Getter;
import org.example.backendPlayground.api.PostService;
import org.example.backendPlayground.domain.Post;
import org.example.backendPlayground.enums.PostVisibility;
import org.example.core.utilities.RandomData;
import org.example.core.utilities.ScenarioCore;

@Getter
public class PostScenario extends ScenarioCore {
	private Long userId;
	private Long postId;
	private String postTitle;
	private String postContent;
	private PostVisibility postVisibility;

	public PostScenario withDefaults() {
		if (this.needsDefaultValuesPopulated) {
			this.postTitle = getNonNull(postTitle, RandomData.getRandomString(10));
			this.postContent = getNonNull(postContent, RandomData.en.lorem().paragraph());
			this.postVisibility = getNonNull(postVisibility, PostVisibility.PUBLIC);
		}

		return this;
	}

	public PostScenario create() {
		withDefaults();
		assert this.userId != null : "User ID is required to create a post";

		Post post = PostService.create(this.userId, getAsPost()).call();
		this.postId = post.getId();
		return this;
	}

	public Post getAsPost() {
		Post post = new Post();
		post.setId(postId);
		post.setUserId(userId);
		post.setTitle(postTitle);
		post.setContent(postContent);
		post.setVisibility(postVisibility);
		return post;
	}

	public PostScenario withPostTitle(String postTitle) {
		this.postTitle = postTitle;
		return this;
	}

	public PostScenario withPostContent(String postContent) {
		this.postContent = postContent;
		return this;
	}

	public PostScenario withPostVisibility(PostVisibility postVisibility) {
		this.postVisibility = postVisibility;
		return this;
	}

	public PostScenario withUserId(Long userId) {
		this.userId = userId;
		return this;
	}

}
