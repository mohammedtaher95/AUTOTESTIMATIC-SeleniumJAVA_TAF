package driverfactory.webdriver.helpers;

import java.util.HashMap;
import java.util.Map;
import tools.properties.Properties;
import utilities.LoggingManager;

public class MobileEmulation {

    private MobileEmulation() {

    }

    public static Map<String, Object> setEmulationSettings() {

        Map<String, Object> mobileEmulation = new HashMap<>();
        if (Properties.web.isCustomDevice()) {
            Map<String, Object> deviceMetrics = new HashMap<>();
            deviceMetrics.put("width", Properties.web.customDeviceWidth());
            deviceMetrics.put("height", Properties.web.customDeviceHeight());
            deviceMetrics.put("pixelRatio", Properties.web.customDevicePixelRatio());
            mobileEmulation.put("deviceMetrics", deviceMetrics);
            mobileEmulation.put("userAgent",
                  "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) "
                        + "AppleWebKit/535.19 (KHTML, like Gecko) "
                        + "Chrome/18.0.1025.166 Mobile Safari/535.19");
            LoggingManager.info("Running Mobile Emulation via Custom Device");
            LoggingManager.info("Screen size: " + Properties.web.customDeviceWidth() + "x"
                  + Properties.web.customDeviceHeight());
            LoggingManager.info("Screen Pixel Ratio: " + Properties.web.customDevicePixelRatio());
        } else {
            mobileEmulation.put("deviceName", Properties.web.deviceName());
            LoggingManager.info("Running Mobile Emulation via " + Properties.web.deviceName());
        }

        return mobileEmulation;
    }
}
