package tools.properties;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.*;
import org.aeonbits.owner.Config.Sources;


@LoadPolicy(LoadType.MERGE)
@Sources({
          "file:src/main/resources/properties/WebCapabilities.properties",
          "classpath:src/main/resources/properties/WebCapabilities.properties"})

public interface WebCapabilities extends Config, Accessible {

    @Key("TARGET_BROWSER_NAME")
    @DefaultValue("chrome")
    String targetBrowserName();

    @Key("BASE_URL")
    @DefaultValue("")
    String baseURL();

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

}
