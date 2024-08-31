package org.example.core.api;

import io.restassured.response.Response;

import java.lang.reflect.Type;
import java.util.List;

public class GetAllWithParentIdApi<T> extends ApiBase<GetAllWithParentIdApi<T>> {
	private final Type resultType;
	private String parentId;

	public GetAllWithParentIdApi<T> withParentId(String parentId) {
		this.parentId = parentId;
		return this;
	}

	public GetAllWithParentIdApi(String baseUrl, Type type) {
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
