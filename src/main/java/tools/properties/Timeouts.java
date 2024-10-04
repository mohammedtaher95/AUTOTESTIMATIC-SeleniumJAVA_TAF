package tools.properties;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Reloadable;
import utilities.LoggingManager;


@LoadPolicy(LoadType.MERGE)
@Sources({"file:src/main/resources/properties/Timeouts.properties",
      "classpath:src/main/resources/properties/Timeouts.properties"})
public interface Timeouts extends Config, Accessible, Reloadable {

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


    default SetProperties set() {
        return new SetProperties();
    }

    class SetProperties {

        private SetProperties() {

        }

        private static void setProperty(String key, String value) {
            var updatedProps = new java.util.Properties();
            updatedProps.setProperty(key, value);
            Properties.timeouts = ConfigFactory.create(Timeouts.class, updatedProps);
            // temporarily set the system property to support hybrid read/write mode
            System.setProperty(key, value);
            LoggingManager.info("Setting \"" + key + "\" property with \"" + value + "\".");
        }

        public SetProperties scriptTimeout(int value) {
            setProperty("scriptTimeout", String.valueOf(value));
            return this;
        }

        public SetProperties pageLoadTimeout(int value) {
            setProperty("pageLoadTimeout", String.valueOf(value));
            return this;
        }

        public SetProperties elementIdentificationTimeout(int value) {
            setProperty("elementIdentificationTimeout", String.valueOf(value));
            return this;
        }

        public SetProperties waitForLazyLoading(boolean value) {
            setProperty("waitForLazyLoading", String.valueOf(value));
            return this;
        }

        public SetProperties lazyLoadingTimeout(int value) {
            setProperty("lazyLoadingTimeout", String.valueOf(value));
            return this;
        }

    }


}
