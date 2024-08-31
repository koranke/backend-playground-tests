package org.example.backendPlayground.domain;

import lombok.Data;

@Data
public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String dateOfBirth;
}
