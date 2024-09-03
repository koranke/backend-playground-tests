package org.example.backendPlayground.domain;

import com.google.gson.Gson;

public class BaseDomain {

	public String toJson() {
		return new Gson().toJson(this);
	}
}
