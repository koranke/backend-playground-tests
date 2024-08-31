package org.example.core.configuration;

import org.aeonbits.owner.ConfigCache;

public class Configuration {
	public static final Props props = ConfigCache.getOrCreate(Props.class);

	public static final String BASE_USER_SERVICE_URL = props.baseUserServiceUrl();
	public static final String BASE_POST_SERVICE_URL = props.basePostServiceUrl();
}
