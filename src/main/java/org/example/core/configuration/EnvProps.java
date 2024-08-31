package org.example.core.configuration;

import org.aeonbits.owner.ConfigFactory;

public class EnvProps {

	public static void load() {
		Env env = ConfigFactory.create(Env.class);
		System.setProperty("env.id", env.envId().getValue());
		Props props = ConfigFactory.create(Props.class);
	}
}
