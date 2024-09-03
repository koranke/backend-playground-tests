package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;

public class CreateApi<T> extends ApiBase<CreateApi<T>> {
	private final Type resultType;
	private String parentId;
	private Object body;

	public CreateApi<T> withParentId(Long parentId) {
		this.parentId = parentId.toString();
		return this;
	}

	public CreateApi<T> withBody(Object body) {
		this.body = body;
		return this;
	}

	public CreateApi(String baseUrl, Type type) {
		this.baseUrl = baseUrl;
		this.resultType = type;
	}

	public T call() {
		return tryCall()
			.then()
			.statusCode(200)
			.extract()
			.as(resultType);
	}

	public Response tryCall() {
		if (parentId != null && !parentId.isEmpty()) {
			baseUrl = String.format(baseUrl, parentId);
		}
		return post("", body);
	}
}
