package org.example.core.configuration;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
	"system:env",
	"classpath:core.properties",
})
public interface Env extends Config {
	@Key("env.id")
	TargetEnvironment envId();
}
