package driverfactory.webdriver.localdriver;

import constants.DriverType;

public class DriverFactory {

    private DriverFactory() {

    }

    public static DriverAbstract getDriverFactory(DriverType driverType) {
        return switch (driverType) {
            case CHROME -> new ChromeDriverFactory();
            case FIREFOX -> new FirefoxDriverFactory();
            case EDGE -> new EdgeDriverFactory();
            default -> throw new IllegalStateException("Unexpected value: " + driverType);
        };
    }
}
