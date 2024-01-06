package driverfactory.webdriver.helpers;

import utilities.LoggingManager;

import java.util.HashMap;
import java.util.Map;

import static tools.properties.PropertiesHandler.getCapabilities;

public class MobileEmulation {

    private MobileEmulation() {

    }

    public static Map<String, Object> setEmulationSettings() {

        Map<String, Object> mobileEmulation = new HashMap<>();
        if(getCapabilities().isCustomDevice()){
            Map<String, Object> deviceMetrics = new HashMap<>();
            deviceMetrics.put("width", getCapabilities().customDeviceWidth());
            deviceMetrics.put("height", getCapabilities().customDeviceHeight());
            deviceMetrics.put("pixelRatio", getCapabilities().customDevicePixelRatio());
            mobileEmulation.put("deviceMetrics", deviceMetrics);
            mobileEmulation.put("userAgent",
                    "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");
            LoggingManager.info("Running Mobile Emulation via Custom Device");
            LoggingManager.info("Screen size: " + getCapabilities().customDeviceWidth() + "x"
                    + getCapabilities().customDeviceHeight());
            LoggingManager.info("Screen Pixel Ratio: " + getCapabilities().customDevicePixelRatio());
        }
        else {
            mobileEmulation.put("deviceName", getCapabilities().deviceName());
            LoggingManager.info("Running Mobile Emulation via " + getCapabilities().deviceName());
        }

        return mobileEmulation;
    }
}
