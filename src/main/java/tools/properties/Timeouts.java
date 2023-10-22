package tools.properties;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(LoadType.MERGE)
@Sources({"file:src/main/resources/properties/Timeouts.properties",
        "classpath:src/main/resources/properties/Timeouts.properties"})
public interface Timeouts extends Config, Accessible {

    @Key("scriptTimeout")
    @DefaultValue("60")
    int scriptTimeout();

    @Key("pageLoadTimeout")
    @DefaultValue("60")
    int pageLoadTimeout();

    @Key("elementIdentificationTimeout")
    @DefaultValue("60")
    int elementIdentificationTimeout();

    @Key("waitForLazyLoading")
    @DefaultValue("true")
    boolean waitForLazyLoading();

    @Key("lazyLoadingTimeout")
    @DefaultValue("30")
    int lazyLoadingTimeout();


}
