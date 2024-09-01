package org.example.core.configuration;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
	"system:env",
	"classpath:core.properties",
	"classpath:${env.id}.properties"
})
public interface Props extends Config {
	@Key("env.id")
	@DefaultValue("LOCAL")
	TargetEnvironment envId();

	@Key("driver")
	String driver();

	@Key("dbUrl")
	String dbUrl();

	@Key("DB_USER")
	String dbUser();

	@Key("DB_PASSWORD")
	String dbPassword();

	@Key("baseUserServiceUrl")
	String baseUserServiceUrl();

	@Key("basePostServiceUrl")
	String basePostServiceUrl();
}
