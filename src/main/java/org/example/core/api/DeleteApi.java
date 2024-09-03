package org.example.core.api;

import io.restassured.response.Response;

public class DeleteApi extends ApiBase<DeleteApi> {
	private String parentId;
	private String id;

	public DeleteApi withParentId(Long parentId) {
		this.parentId = parentId.toString();
		return this;
	}

	public DeleteApi withId(Long id) {
		this.id = id.toString();
		return this;
	}

	public DeleteApi withId(String id) {
		this.id = id;
		return this;
	}

	public DeleteApi(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Response call() {
		return tryCall()
			.then()
			.statusCode(200)
			.extract().response();
	}

	public Response tryCall() {
		if (parentId != null && !parentId.isEmpty()) {
			baseUrl = String.format(baseUrl, parentId);
		}
		return delete(id);
	}
}
