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

public abstract class ApiEndpoint<T> {
	private RequestSpecification requestSpecification;
	protected String baseUrl;
	protected Method method = Method.GET;
	protected int successStatusCode = 200;
	protected String contentType = "application/json; charset=UTF-8";
	protected String accept;
	protected String authorization;
	protected AuthType authType;
	protected Map<String, String> headers;
	protected Map<String, String> queryParameters;
	protected String parentId;
	protected String id;
	protected Object body;
	protected Type resultType;

	public ApiEndpoint(String baseUrl, Type type) {
		this.baseUrl = baseUrl;
		this.resultType = type;
	}

	protected T callSingle() {
		return tryCall()
				.then()
				.statusCode(successStatusCode)
				.extract()
				.as(resultType);
	}

	protected List<T> callList() {
		return tryCall()
				.then()
				.statusCode(successStatusCode)
				.extract()
				.as(resultType);
	}

	protected Response callEmpty() {
		return tryCall()
				.then()
				.statusCode(successStatusCode)
				.extract()
				.response();
	}

	public Response tryCall() {
		if (parentId != null && !parentId.isEmpty()) {
			baseUrl = String.format(baseUrl, parentId);
		}
		return call(id);
	}


	public T withParentId(Long parentId) {
		this.parentId = parentId.toString();
		return (T) this;
	}

	public T withId(Long id) {
		this.id = id.toString();
		return (T) this;
	}

	public T withId(String id) {
		this.id = id;
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

	public T withBaseUrl(String value) {
		baseUrl = value;
		return (T) this;
	}

	public T withMethod(Method value) {
		method = value;
		return (T) this;
	}

	public T withSuccessStatusCode(int value) {
		successStatusCode = value;
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

	protected Response call(String endpoint) {
		configureClient();
		endpoint = formatEndpoint(endpoint);
		if (body != null) {
			String bodyString = getBodyString(body);
			requestSpecification.body(bodyString);
		}
		return requestSpecification.request(method, baseUrl + endpoint);
	}

	private void configureClient() {
		requestSpecification = io.restassured.RestAssured.given();
		requestSpecification.filters(
				new io.restassured.filter.log.RequestLoggingFilter(),
				new io.restassured.filter.log.ResponseLoggingFilter(),
				new io.restassured.filter.log.ErrorLoggingFilter(),
				new AllureRestAssured()
		);
		requestSpecification.baseUri(baseUrl);
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
		if (queryParameters != null) {
			requestSpecification.queryParams(queryParameters);
		}
	}

	private void configureAuthorization() {
		if (authType == AuthType.BEARER) {
			withHeader("Authorization", String.format("%s %s", authType.getValue(), authorization));
		} else if (authType == AuthType.BASIC) {
			requestSpecification.auth().preemptive().basic(authorization, "");
		} else {
			throw new IllegalArgumentException("Unsupported authorization type");
		}
	}

	private static String formatEndpoint(String endpoint) {
		if (endpoint == null) {
			endpoint = "";
		}
		if (!endpoint.isEmpty() && !endpoint.startsWith("/")) {
			endpoint = "/" + endpoint;
		}
		return endpoint;
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
