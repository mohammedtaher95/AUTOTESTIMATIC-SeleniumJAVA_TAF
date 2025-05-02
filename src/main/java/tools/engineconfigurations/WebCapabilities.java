package tools.engineconfigurations;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Reloadable;
import utilities.LoggingManager;


@LoadPolicy(LoadType.MERGE)
@Sources({
      "file:src/main/resources/properties/Configurations.properties",
      "classpath:Configurations.properties"})
public interface WebCapabilities extends Config, Accessible, Reloadable {

    @Key("TARGET_BROWSER_NAME")
    @DefaultValue("chrome")
    String targetBrowserName();

    @Key("BASE_URL")
    @DefaultValue("")
    String baseUrl();

    @Key("EXECUTION_METHOD")
    @DefaultValue("normal")
    String executionMethod();

    @Key("MOBILE_EMULATION")
    @DefaultValue("false")
    boolean isMobileEmulation();

    @Key("DEVICE_NAME")
    @DefaultValue("")
    String deviceName();

    @Key("CUSTOM_DEVICE")
    @DefaultValue("false")
    boolean isCustomDevice();

    @Key("CUSTOM_DEVICE_WIDTH")
    @DefaultValue("360")
    int customDeviceWidth();

    @Key("CUSTOM_DEVICE_HEIGHT")
    @DefaultValue("640")
    int customDeviceHeight();

    @Key("CUSTOM_DEVICE_PIXEL_RATIO")
    @DefaultValue("3.0")
    float customDevicePixelRatio();

    default SetProperties set() {
        return new SetProperties();
    }

    class SetProperties {

        public SetProperties() {
            // starting class
        }

        private static void setProperty(String key, String value) {
            var updatedProps = new java.util.Properties();
            updatedProps.setProperty(key, value);
            Configurations.web = ConfigFactory.create(WebCapabilities.class, updatedProps);
            Configurations.web.reload();
            // temporarily set the system property to support hybrid read/write mode
            System.setProperty(key, value);
            LoggingManager.info("Setting \"" + key + "\" property with \"" + value + "\".");
        }


        public SetProperties targetBrowserName(String value) {
            setProperty("TARGET_BROWSER_NAME", value);
            return this;
        }

        public SetProperties baseUrl(String value) {
            setProperty("BASE_URL", value);
            return this;
        }

        public SetProperties executionMethod(String value) {
            setProperty("EXECUTION_METHOD", value);
            return this;
        }

        public SetProperties mobileEmulation(boolean value) {
            setProperty("MOBILE_EMULATION", String.valueOf(value));
            return this;
        }

        public SetProperties deviceName(String value) {
            setProperty("DEVICE_NAME", value);
            return this;
        }

        public SetProperties customDevice(boolean value) {
            setProperty("CUSTOM_DEVICE", String.valueOf(value));
            return this;
        }

        public SetProperties customDeviceWidth(int value) {
            setProperty("CUSTOM_DEVICE_WIDTH", String.valueOf(value));
            return this;
        }

        public SetProperties customDeviceHeight(int value) {
            setProperty("CUSTOM_DEVICE_HEIGHT", String.valueOf(value));
            return this;
        }

        public SetProperties customDevicePixelRatio(int value) {
            setProperty("CUSTOM_DEVICE_PIXEL_RATIO", String.valueOf(value));
            return this;
        }

    }
}
