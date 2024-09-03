package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;

public class GetSingleApi<T> extends ApiBase<GetSingleApi<T>> {
	private final Type resultType;
	private String parentId;
	private String id;

	public GetSingleApi<T> withParentId(Long parentId) {
		this.parentId = parentId.toString();
		return this;
	}

	public GetSingleApi<T> withId(Long id) {
		this.id = id.toString();
		return this;
	}

	public GetSingleApi(String baseUrl, Type type) {
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
		return get(id);
	}
}
