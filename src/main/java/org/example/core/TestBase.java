package org.example.core;

import org.example.core.configuration.Configuration;
import org.example.core.configuration.Env;
import org.aeonbits.owner.ConfigFactory;
import org.example.core.utilities.Log;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.util.Properties;

public class TestBase {
	protected static Log log = Log.getInstance();

	static {
		if (System.getProperty("env.id") == null) {
			Env env = ConfigFactory.create(Env.class);
			System.setProperty("env.id", env.envId().getValue());
		}
		log.info("Environment: " + System.getProperty("env.id"));
	}

	@BeforeSuite
	public void setup() {
		Properties properties = new Properties();
		properties.setProperty("USER_DB", Configuration.props.dbUser());
		properties.setProperty("PASSWORD_DB", Configuration.props.dbPassword());

		log.info("BASE_POST_SERVICE_URL: " + Configuration.BASE_POST_SERVICE_URL);
	}

	@BeforeMethod(alwaysRun = true)
	public void methodSetup(Method method) {
		log.startTest(getTestName(method));
	}

	@AfterMethod(alwaysRun = true)
	public void methodCleanup(Method method) {
		log.endTest(getTestName(method));
	}

	private String getTestName(Method method) {
		return String.format("%s.%s", method.getDeclaringClass().getSimpleName(), method.getName());
	}

}
