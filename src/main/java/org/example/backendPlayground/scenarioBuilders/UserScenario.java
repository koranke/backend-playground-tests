package org.example.backendPlayground.scenarioBuilders;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.backendPlayground.api.UserService;
import org.example.backendPlayground.domain.Post;
import org.example.backendPlayground.domain.User;
import org.example.core.utilities.RandomData;
import org.example.core.utilities.ScenarioCore;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserScenario extends ScenarioCore {
	private Integer numberOfPosts;
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String dateOfBirth;
	private List<PostScenario> postScenarios;

	public UserScenario withDefaults() {
		if (this.needsDefaultValuesPopulated) {
			this.firstName = getNonNull(firstName, RandomData.en.name().firstName());
			this.lastName = getNonNull(lastName, RandomData.en.name().lastName());
			this.email = getNonNull(email, RandomData.en.internet().emailAddress());
			this.numberOfPosts = getNonNull(numberOfPosts, 0);
			if (this.numberOfPosts > 0) {
				this.postScenarios = new ArrayList<>();
				for (int i = 0; i < numberOfPosts; i++) {
					this.postScenarios.add(new PostScenario().withDefaults());
				}
			}
		}
		return this;
	}

	public UserScenario create() {
		withDefaults();
		User user = UserService.create(getAsUser()).call();
		this.id = user.getId();

		if (postScenarios != null) {
			for (PostScenario postScenario : postScenarios) {
				postScenario.withUserId(id);
				postScenario.create();
			}
		}
		return this;
	}

	public User getAsUser() {
		User user = new User();
		user.setId(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPhone(phone);
		user.setDateOfBirth(dateOfBirth);
		return user;
	}

	public UserScenario withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public UserScenario withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public UserScenario withEmail(String email) {
		this.email = email;
		return this;
	}

	public UserScenario withPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public UserScenario withDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public UserScenario withPost(PostScenario postScenario) {
		if (postScenarios == null) {
			postScenarios = new ArrayList<>();
		}
		postScenarios.add(postScenario);
		return this;
	}

	public UserScenario withNumberOfPosts(Integer numberOfPosts) {
		this.numberOfPosts = numberOfPosts;
		return this;
	}

	public List<Post> getPosts() {
		List<Post> posts = new ArrayList<>();
		if (postScenarios != null) {
			for (PostScenario postScenario : postScenarios) {
				posts.add(postScenario.getAsPost());
			}
		}
		return posts;
	}
}
