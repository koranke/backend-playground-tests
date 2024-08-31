package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;

public class CreateApi<T> extends ApiBase<CreateApi<T>> {
	private final Type resultType;
	private T body;

	public CreateApi<T> withBody(T body) {
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
		return post("", body);
	}
}
