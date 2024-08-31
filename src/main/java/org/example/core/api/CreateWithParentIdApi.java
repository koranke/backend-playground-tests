package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;

public class CreateWithParentIdApi<T> extends ApiBase<CreateWithParentIdApi<T>> {
	private final Type resultType;
	private String parentId;
	private T body;

	public CreateWithParentIdApi<T> withParentId(Long parentId) {
		this.parentId = parentId.toString();
		return this;
	}

	public CreateWithParentIdApi<T> withBody(T body) {
		this.body = body;
		return this;
	}

	public CreateWithParentIdApi(String baseUrl, Type type) {
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
		baseUrl = String.format(baseUrl, parentId);
		return post("", body);
	}
}
