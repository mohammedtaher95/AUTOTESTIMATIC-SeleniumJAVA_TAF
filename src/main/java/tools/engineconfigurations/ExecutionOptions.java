package tools.engineconfigurations;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Reloadable;
import utilities.LoggingManager;


@LoadPolicy(Config.LoadType.MERGE)
@Sources({"file:src/main/resources/properties/Configurations.properties",
    "classpath:src/main/resources/properties/Configurations.properties"})
public interface ExecutionOptions extends Config, Accessible, Reloadable {

    @Key("ENV_TYPE")
    @DefaultValue("LOCAL")
    String environmentType();

    @Key("CROSS_BROWSER_MODE")
    @DefaultValue("OFF")
    String crossBrowserMode();

    @Key("REMOTE_ENV_URL")
    @DefaultValue("")
    String remoteUrl();

    @Key("PROXY_SETTINGS")
    @DefaultValue("")
    String proxySettings();

    default SetProperties set() {
        return new SetProperties();
    }

    class SetProperties {

        private SetProperties() {

        }

        private static void setProperty(String key, String value) {
            var updatedProps = new java.util.Properties();
            updatedProps.setProperty(key, value);
            Configurations.executionOptions = ConfigFactory.create(ExecutionOptions.class,
                  updatedProps);
            System.setProperty(key, value);
            LoggingManager.info("Setting \"" + key + "\" property with \"" + value + "\".");
        }

        public SetProperties crossBrowserMode(String value) {
            setProperty("CROSS_BROWSER_MODE", value);
            return this;
        }

        public SetProperties remoteExecutionAddress(String value) {
            setProperty("REMOTE_ENV_URL", value);
            return this;
        }

        public SetProperties environmentType(String value) {
            setProperty("ENV_TYPE", value);
            return this;
        }

        public SetProperties proxySettings(String value) {
            setProperty("PROXY_SETTINGS", value);
            return this;
        }

    }
}
