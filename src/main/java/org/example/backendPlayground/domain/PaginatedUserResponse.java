package org.example.backendPlayground.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaginatedUserResponse extends PaginatedResponse {
	private List<User> users;
}
