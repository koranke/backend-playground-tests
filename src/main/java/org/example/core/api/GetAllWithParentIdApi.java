package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;
import java.util.List;

public class GetAllWithParentIdApi<T> extends ApiBase<GetAllWithParentIdApi<T>> {
	private final Type resultType;

	public GetAllWithParentIdApi(String baseUrl, Type type) {
		this.baseUrl = baseUrl;
		this.resultType = type;
	}

	public List<T> call(String id) {
		if (id != null && !id.isEmpty()) {
			baseUrl = String.format(baseUrl, id);
		}
		return tryCall(id)
			.then()
			.statusCode(200)
			.extract()
			.as(resultType);
	}

	public Response tryCall(String id) {
		if (id != null && !id.isEmpty()) {
			baseUrl = String.format(baseUrl, id);
		}
		return get("");
	}

}
