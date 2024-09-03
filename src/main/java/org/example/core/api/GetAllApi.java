package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;
import java.util.List;

public class GetAllApi<T> extends ApiBase<GetAllApi<T>> {
	private final Type resultType;
	private String parentId;

	public GetAllApi<T> withParentId(Long parentId) {
		this.parentId = parentId.toString();
		return this;
	}

	public GetAllApi(String baseUrl, Type type) {
		this.baseUrl = baseUrl;
		this.resultType = type;
	}

	public List<T> call() {
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
		return get("");
	}

}
