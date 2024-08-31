package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;

public class CreateApi<T> extends ApiBase<CreateApi<T>> {
	private final Type resultType;

	public CreateApi(String baseUrl, Type type) {
		this.baseUrl = baseUrl;
		this.resultType = type;
	}

	public T call(T body) {
		return tryCall(body)
			.then()
			.statusCode(200)
			.extract()
			.as(resultType);
	}

	public Response tryCall(T body) {
		return post("", body);
	}
}
