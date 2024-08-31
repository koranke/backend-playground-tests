package org.example.core;

import org.example.core.configuration.Configuration;
import org.example.core.configuration.Env;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.BeforeSuite;

import java.util.Properties;

public class TestBase {

	static {
		if (System.getProperty("env.id") == null) {
			Env env = ConfigFactory.create(Env.class);
			System.setProperty("env.id", env.envId().getValue());
		}
	}

	@BeforeSuite
	public void setup() {
		Properties properties = new Properties();
		properties.setProperty("USER_DB", Configuration.props.dbUser());
		properties.setProperty("PASSWORD_DB", Configuration.props.dbPassword());
	}

}
