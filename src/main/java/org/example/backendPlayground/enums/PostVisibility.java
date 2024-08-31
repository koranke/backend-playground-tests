package org.example.backendPlayground.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostVisibility {
	PUBLIC("public"),
	PRIVATE("private"),
	SHARED("shared")
	;

	private final String visibility;

	public static PostVisibility fromString(String visibility) {
		for (PostVisibility postVisibility : PostVisibility.values()) {
			if (postVisibility.getVisibility().equalsIgnoreCase(visibility)) {
				return postVisibility;
			}
		}
		assert false : "Unknown visibility: " + visibility;
		return null;
	}

}
