package org.example.core.api;

import io.restassured.response.Response;

public class ReturnNone<T> extends ApiEndpoint<ReturnNone<T>> {

	public ReturnNone(String baseUrl) {
		super(baseUrl, null);
	}

	public Response call() {
		return this.callEmpty();
	}

}
