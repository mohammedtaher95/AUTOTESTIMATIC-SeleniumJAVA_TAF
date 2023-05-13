package tools.properties;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.*;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(LoadType.MERGE)
@Sources({"file:src/main/resources/properties/Reporting.properties",
        "classpath:src/main/resources/properties/Reporting.properties"})
public interface Reporting extends Config, Accessible {

    @Key("OPEN_ALLURE_REPORT_AFTER_EXECUTION")
    @DefaultValue("true")
    boolean automaticOpenAllureReport();

    @Key("CLEAN_ALLURE_REPORT_BEFORE_EXECUTION")
    @DefaultValue("true")
    boolean cleanAllureReport();

    @Key("GENERATE_EMAILABLE_REPORT")
    @DefaultValue("false")
    boolean generateEmailableReport();

}
