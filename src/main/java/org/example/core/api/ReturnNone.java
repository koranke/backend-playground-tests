package org.example.core.api;

import io.restassured.http.Method;
import io.restassured.response.Response;

public class ReturnNone extends ApiEndpoint<ReturnNone, Object> {

	public ReturnNone(Method method, String endpointUrl) {
		super(method, endpointUrl, null);
	}

	public Response call() {
		return this.callGetResponse();
	}

}
