package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;

public class GetPaginatedAllApi<T> extends ApiBase<GetPaginatedAllApi<T>> {
	private final Type resultType;

	public GetPaginatedAllApi(String baseUrl, Type type) {
		this.baseUrl = baseUrl;
		this.resultType = type;
	}

	public T call() {
		return call("");
	}

	public T call(String id) {
		if (id != null && !id.isEmpty()) {
			baseUrl = String.format(baseUrl, id);
		}
		return tryCall()
			.then()
			.statusCode(200)
			.extract()
			.as(resultType);
	}

	public Response tryCall() {
		return get("");
	}

	public Response tryCall(String id) {
		if (id != null && !id.isEmpty()) {
			baseUrl = String.format(baseUrl, id);
		}
		return get("");
	}

}
