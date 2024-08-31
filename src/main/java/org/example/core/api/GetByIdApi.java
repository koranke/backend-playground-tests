package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;

public class GetByIdApi<T> extends ApiBase<GetByIdApi<T>> {
	private final Type resultType;
	private String id;

	public GetByIdApi<T> withId(Long id) {
		this.id = id.toString();
		return this;
	}

	public GetByIdApi<T> withId(String id) {
		this.id = id;
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
		return get(id);
	}
}
