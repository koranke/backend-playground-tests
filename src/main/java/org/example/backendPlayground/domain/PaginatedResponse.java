package org.example.backendPlayground.domain;

import lombok.Data;

@Data
public class PaginatedResponse {
	private int currentPage;
	private int totalPages;
	private long totalItems;
	private int size;
	private int numberOfItems;
}
