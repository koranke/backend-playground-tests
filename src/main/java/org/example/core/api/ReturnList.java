package org.example.core.api;

import java.lang.reflect.Type;
import java.util.List;

public class ReturnList<T> extends ApiEndpoint<ReturnList<T>> {

	public ReturnList(String baseUrl, Type resultType) {
		super(baseUrl, resultType);
	}

	public List<T> call() {
		return (List<T>) this.callList();
	}

}
