package org.example.backendPlayground.domain;

import org.example.backendPlayground.enums.PostVisibility;
import lombok.Data;

import java.util.Date;

@Data
public class Post {
	private Long id;
	private Long userId;
	private String title;
	private String content;
	private PostVisibility visibility;
	private Date dateCreated;
	private Date dateUpdated;
}
