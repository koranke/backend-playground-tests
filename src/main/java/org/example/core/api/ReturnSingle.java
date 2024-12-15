package org.example.core.api;

public class ReturnSingle<T> extends ApiEndpoint<ReturnSingle<T>> {

	public ReturnSingle(Class<T> resultType) {
		super(resultType);
	}

	public T call() {
		return (T) this.callGetSingle();
	}

}
