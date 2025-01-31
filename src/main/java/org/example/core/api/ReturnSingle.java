package org.example.core.api;

import io.restassured.http.Method;

public class ReturnSingle<T> extends ApiEndpoint<ReturnSingle<T>, T> {

	public ReturnSingle(Method method, String endpointUrl, Class<T> resultType) {
		super(method, endpointUrl, resultType);
	}

	public T call() {
		return this.callGetSingle();
	}

}
