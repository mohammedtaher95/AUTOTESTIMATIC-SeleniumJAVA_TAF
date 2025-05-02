package driverfactory.webdriver.helpers;

import java.util.HashMap;
import java.util.Map;
import tools.engineconfigurations.Configurations;
import utilities.LoggingManager;

public class MobileEmulation {

    private MobileEmulation() {

    }

    public static Map<String, Object> setEmulationSettings() {

        Map<String, Object> mobileEmulation = new HashMap<>();
        if (Configurations.web.isCustomDevice()) {
            Map<String, Object> deviceMetrics = new HashMap<>();
            deviceMetrics.put("width", Configurations.web.customDeviceWidth());
            deviceMetrics.put("height", Configurations.web.customDeviceHeight());
            deviceMetrics.put("pixelRatio", Configurations.web.customDevicePixelRatio());
            mobileEmulation.put("deviceMetrics", deviceMetrics);
            mobileEmulation.put("userAgent",
                  "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) "
                        + "AppleWebKit/535.19 (KHTML, like Gecko) "
                        + "Chrome/18.0.1025.166 Mobile Safari/535.19");
            LoggingManager.info("Running Mobile Emulation via Custom Device");
            LoggingManager.info("Screen size: " + Configurations.web.customDeviceWidth() + "x"
                  + Configurations.web.customDeviceHeight());
            LoggingManager.info("Screen Pixel Ratio: " + Configurations.web.customDevicePixelRatio());
        } else {
            mobileEmulation.put("deviceName", Configurations.web.deviceName());
            LoggingManager.info("Running Mobile Emulation via " + Configurations.web.deviceName());
        }

        return mobileEmulation;
    }
}
