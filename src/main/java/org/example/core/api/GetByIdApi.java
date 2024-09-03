package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;

public class GetByIdApi<T> extends ApiBase<GetByIdApi<T>> {
	private final Type resultType;
	private String parentId;
	private String id;

	public GetByIdApi<T> withParentId(Long parentId) {
		this.parentId = parentId.toString();
		return this;
	}

	public GetByIdApi<T> withId(Long id) {
		this.id = id.toString();
		return this;
	}

	public GetByIdApi(String baseUrl, Type type) {
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
