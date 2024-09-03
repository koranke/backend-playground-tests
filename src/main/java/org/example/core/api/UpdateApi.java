package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;

public class UpdateApi<T> extends ApiBase<UpdateApi<T>> {
	private final Type resultType;
	private String id;
	private String parentId;
	private Object body;

	public UpdateApi<T> withBody(Object body) {
		this.body = body;
		return this;
	}

	public UpdateApi<T> withParentId(Long parentId) {
		this.parentId = parentId.toString();
		return this;
	}

	public UpdateApi<T> withId(Long id) {
		this.id = id.toString();
		return this;
	}

	public UpdateApi(String baseUrl, Type type) {
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
		return put(id, body);
	}
}
