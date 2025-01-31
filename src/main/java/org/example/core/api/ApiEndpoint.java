package org.example.core.api;

import com.google.gson.Gson;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.enums.AuthType;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public abstract class ApiEndpoint<T extends ApiEndpoint<T, R>, R> {
	private RequestSpecification requestSpecification;
	protected String endpointUrl;
	protected Method method;
	protected int expectedStatusCode = 200;
	protected String contentType = "application/json; charset=UTF-8";
	protected String accept;
	protected String authorization;
	protected AuthType authType;
	protected Map<String, String> headers;
	protected Map<String, String> pathParameters;
	protected Map<String, String> queryParameters;
	protected Object body;
	protected Type resultType;

	public ApiEndpoint(Method method, String endpointUrl, Type type) {
		this.method = method;
		this.endpointUrl = endpointUrl;
		this.resultType = type;
	}

	protected R callGetSingle() {
		return tryCall()
				.then()
				.statusCode(expectedStatusCode)
				.extract()
				.as(resultType);
	}

	protected List<R> callGetList() {
		return tryCall()
				.then()
				.statusCode(expectedStatusCode)
				.extract()
				.as(resultType);
	}

	protected Response callGetResponse() {
		return tryCall()
				.then()
				.statusCode(expectedStatusCode)
				.extract()
				.response();
	}

	public Response tryCall() {
		configureClient();
		if (body != null) {
			String bodyString = getBodyString(body);
			requestSpecification.body(bodyString);
		}
		return requestSpecification.request(method, endpointUrl);
	}

	public T withPathParameter(String key, String value) {
		if (pathParameters == null) {
			pathParameters = new java.util.HashMap<>();
		}
		pathParameters.put(key, value);
		return (T) this;
	}

	public T withQueryParameter(String key, String value) {
		if (queryParameters == null) {
			queryParameters = new java.util.HashMap<>();
		}
		queryParameters.put(key, value);
		return (T) this;
	}

	public T withHeader(String key, String value) {
		if (headers == null) {
			headers = new java.util.HashMap<>();
		}
		headers.put(key, value);
		return (T) this;
	}

	public T withAuthorization(String value) {
		authorization = value;
		return (T) this;
	}

	public T withAuthType(AuthType value) {
		authType = value;
		return (T) this;
	}

	public T withContentType(String value) {
		contentType = value;
		return (T) this;
	}

	public T withAccept(String value) {
		accept = value;
		return (T) this;
	}

	public T withEndpointUrl(String value) {
		endpointUrl = value;
		return (T) this;
	}

	public T withMethod(Method value) {
		method = value;
		return (T) this;
	}

	public T withExpectedStatusCode(int value) {
		expectedStatusCode = value;
		return (T) this;
	}

	public T withBody(Object body) {
		this.body = body;
		return (T) this;
	}

	public T withResultType(Type type) {
		this.resultType = type;
		return (T) this;
	}

	private void configureClient() {
		requestSpecification = io.restassured.RestAssured.given();
		requestSpecification.filters(
				new io.restassured.filter.log.RequestLoggingFilter(),
				new io.restassured.filter.log.ResponseLoggingFilter(),
				new io.restassured.filter.log.ErrorLoggingFilter(),
				new AllureRestAssured()
		);
		if (authorization != null) {
			configureAuthorization();
		}
		if (contentType != null) {
			requestSpecification.contentType(contentType);
		}
		if (accept != null) {
			requestSpecification.accept(accept);
		}
		if (headers != null) {
			requestSpecification.headers(headers);
		}
		if (pathParameters != null) {
			requestSpecification.pathParams(pathParameters);
		}
		if (queryParameters != null) {
			requestSpecification.queryParams(queryParameters);
		}
	}

	private void configureAuthorization() {
		if (authType == AuthType.BEARER || authType == AuthType.BASIC) {
			withHeader("Authorization", String.format("%s %s", authType.getValue(), authorization));
		} else {
			throw new IllegalArgumentException("Unsupported authorization type");
		}
	}

	private static String getBodyString(Object body) {
		String bodyString = null;
		if (body != null) {
			if (body instanceof String) {
				bodyString = (String) body;
			} else {
				bodyString = new Gson().toJson(body);
			}
		}
		return bodyString;
	}

}
