package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;

public class GetByIdApi<T> extends ApiBase<GetByIdApi<T>> {
	private final Type resultType;

	public GetByIdApi(String baseUrl, Type type) {
		this.baseUrl = baseUrl;
		this.resultType = type;
	}

	public T call(String id) {
		return tryCall(id)
			.then()
			.statusCode(200)
			.extract()
			.as(resultType);
	}

	public Response tryCall(String id) {
		return get(id);
	}
}
