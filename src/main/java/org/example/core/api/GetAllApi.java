package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;
import java.util.List;

public class GetAllApi<T> extends ApiBase<GetAllApi<T>> {
	private final Type resultType;

	public GetAllApi(String baseUrl, Type type) {
		this.baseUrl = baseUrl;
		this.resultType = type;
	}

//	public List<T> call() {
//		return call("");
//	}

	public List<T> call() {
//		if (id != null && !id.isEmpty()) {
//			baseUrl = String.format(baseUrl, id);
//		}
		return tryCall()
			.then()
			.statusCode(200)
			.extract()
			.as(resultType);
	}

	public Response tryCall() {
		return get("");
	}

//	public Response tryCall(String id) {
//		if (id != null && !id.isEmpty()) {
//			baseUrl = String.format(baseUrl, id);
//		}
//		return get("");
//	}

}
