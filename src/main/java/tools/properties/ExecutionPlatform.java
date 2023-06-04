package tools.properties;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.*;

@LoadPolicy(Config.LoadType.MERGE)
@Sources({"file:src/main/resources/properties/ExecutionPlatform.properties",
        "classpath:src/main/resources/properties/ExecutionPlatform.properties"})
public interface ExecutionPlatform extends Config, Accessible {

    @Key("ENV_TYPE")
    @DefaultValue("LOCAL")
    String environmentType();

    @Key("CROSS_BROWSER_MODE")
    @DefaultValue("OFF")
    String crossBrowserMode();

    @Key("REMOTE_ENV_URL")
    @DefaultValue("")
    String remoteURL();

    @Key("RUN_ALL_TESTS")
    @DefaultValue("false")
    boolean runAllTests();

}
