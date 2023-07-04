package driverfactory.webdriver.localdriver;

import constants.DriverType;

public class DriverFactory {

    private DriverFactory(){

    }

    public static DriverAbstract getDriverFactory(DriverType driverType){
        switch (driverType) {
            case CHROME -> {
                return new ChromeDriverFactory();
            }
            case FIREFOX -> {
                return new FirefoxDriverFactory();
            }
            case EDGE -> {
                return new EdgeDriverFactory();
            }
            default -> throw new IllegalStateException("Unexpected value: " + driverType);
        }
    }
}
