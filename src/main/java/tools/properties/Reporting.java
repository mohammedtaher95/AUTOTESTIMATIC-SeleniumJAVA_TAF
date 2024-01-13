package tools.properties;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.*;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Reloadable;
import utilities.LoggingManager;

@LoadPolicy(LoadType.MERGE)
@Sources({"file:src/main/resources/properties/Reporting.properties",
        "classpath:src/main/resources/properties/Reporting.properties"})
public interface Reporting extends Config, Accessible, Reloadable {

    @Key("OPEN_ALLURE_REPORT_AFTER_EXECUTION")
    @DefaultValue("true")
    boolean automaticOpenAllureReport();

    @Key("CLEAN_ALLURE_REPORT_BEFORE_EXECUTION")
    @DefaultValue("true")
    boolean cleanAllureReport();

    @Key("GENERATE_EMAILABLE_REPORT")
    @DefaultValue("false")
    boolean generateEmailableReport();

    @Key("OPEN_EXTENT_REPORT_AFTER_EXECUTION")
    @DefaultValue("true")
    boolean automaticOpenExtentReport();

    default SetProperties set() {
        return new SetProperties();
    }

    class SetProperties {
        
        private SetProperties() {
            
        }

        private static void setProperty(String key, boolean value) {
            var updatedProps = new java.util.Properties();
            updatedProps.setProperty(key, String.valueOf(value));
            Properties.reporting = ConfigFactory.create(Reporting.class, updatedProps);
            // temporarily set the system property to support hybrid read/write mode
            System.setProperty(key, String.valueOf(value));
            LoggingManager.info("Setting \"" + key + "\" property with \"" + value + "\".");
        }
        public SetProperties automaticOpenAllureReport(boolean value) {
            setProperty("OPEN_ALLURE_REPORT_AFTER_EXECUTION", value);
            return this;
        }

        public SetProperties cleanAllureReport(boolean value) {
            setProperty("CLEAN_ALLURE_REPORT_BEFORE_EXECUTION", value);
            return this;
        }

        public SetProperties generateEmailableReport(boolean value) {
            setProperty("GENERATE_EMAILABLE_REPORT", value);
            return this;
        }

        public SetProperties automaticOpenExtentReport(boolean value) {
            setProperty("OPEN_EXTENT_REPORT_AFTER_EXECUTION", value);
            return this;
        }
    }
}
