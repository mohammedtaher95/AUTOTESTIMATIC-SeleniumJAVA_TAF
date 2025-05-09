package tools.engineconfigurations;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Reloadable;

@LoadPolicy(LoadType.MERGE)
@Sources({"file:src/main/resources/properties/Configurations.properties",
      "classpath:src/main/resources/properties/Configurations.properties"})
public interface TestNg extends Config, Accessible, Reloadable {

    @Key("setParallel")
    @DefaultValue("NONE")
    String parallel();

    @Key("setThreadCount")
    @DefaultValue("1")
    int threadCount();

    @Key("setVerbose")
    @DefaultValue("1")
    Integer verbose();

    @Key("setPreserveOrder")
    @DefaultValue("true")
    boolean preserveOrder();

    @Key("setGroupByInstances")
    @DefaultValue("true")
    boolean groupByInstances();

    @Key("setDataProviderThreadCount")
    @DefaultValue("1")
    int dataProviderThreadCount();

    @Key("setRetryFailedTestAttempts")
    @DefaultValue("0")
    int retryFailedTestAttempts();

}
