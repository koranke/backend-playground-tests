package org.example.backendPlayground.domain;

import lombok.EqualsAndHashCode;
import org.example.backendPlayground.enums.PostVisibility;
import lombok.Data;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class Post extends BaseDomain {
	private Long id;
	private Long userId;
	private String title;
	private String content;
	private PostVisibility visibility;
	private Date dateCreated;
	private Date dateUpdated;
}
