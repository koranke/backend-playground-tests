package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;

public class CreateWithParentIdApi<T> extends ApiBase<CreateWithParentIdApi<T>> {
	private final Type resultType;

	public CreateWithParentIdApi(String baseUrl, Type type) {
		this.baseUrl = baseUrl;
		this.resultType = type;
	}

	public T call(String id, T body) {
		return tryCall(id, body)
			.then()
			.statusCode(200)
			.extract()
			.as(resultType);
	}

	public Response tryCall(String id, T body) {
		baseUrl = String.format(baseUrl, id);
		return post("", body);
	}
}
