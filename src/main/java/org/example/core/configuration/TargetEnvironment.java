package org.example.core.configuration;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TargetEnvironment {
	LOCAL("local"),
	TEST("test"),
	INT("int"),
	PERF("perf")
	;

	private final String value;
}
