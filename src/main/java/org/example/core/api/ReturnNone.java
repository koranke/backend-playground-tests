package org.example.core.api;

import io.restassured.response.Response;

public class ReturnNone<T> extends ApiEndpoint<ReturnNone<T>> {

	public ReturnNone() {
		super(null);
	}

	public Response call() {
		return this.callGetResponse();
	}

}
