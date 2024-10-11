package org.example.core.api;

import io.restassured.response.Response;

public class DeleteApi extends ApiBase<DeleteApi> {

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
