package org.example.core.api;

import java.lang.reflect.Type;
import java.util.List;

public class ReturnList<T> extends ApiEndpoint<ReturnList<T>> {

	public ReturnList(Type resultType) {
		super(resultType);
	}

	public List<T> call() {
		return (List<T>) this.callGetList();
	}

}
