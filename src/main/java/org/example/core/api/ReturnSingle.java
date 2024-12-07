package org.example.core.api;

public class ReturnSingle<T> extends ApiEndpoint<ReturnSingle<T>> {

	public ReturnSingle(String baseUrl, Class<T> resultType) {
		super(baseUrl, resultType);
	}

	public T call() {
		return (T) this.callSingle();
	}

}
