package org.example.core.utilities;

import java.util.concurrent.TimeUnit;

public final class SystemHelper {

	private SystemHelper() {	}

	public static void sleep(long milliseconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(milliseconds);
		} catch (Exception e) {
			//do nothing
		}
	}
}
