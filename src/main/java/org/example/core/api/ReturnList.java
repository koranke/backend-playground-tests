package org.example.core.api;

import io.restassured.http.Method;

import java.lang.reflect.Type;
import java.util.List;

public class ReturnList<T> extends ApiEndpoint<ReturnList<T>, T> {

	public ReturnList(Method method, String endpointUrl, Type resultType) {
		super(method, endpointUrl, resultType);
	}

	public List<T> call() {
		return this.callGetList();
	}

}
