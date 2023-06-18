package tools.properties;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(LoadType.MERGE)
@Sources({"file:src/main/resources/properties/log4j2.properties",
        "classpath:src/main/resources/properties/log4j2.properties"})
public interface Log4j extends Config, Accessible {

    @Key("name")
    @DefaultValue("PropertiesConfig")
    String name();

    @Key("appender.console.type")
    @DefaultValue("Console")
    String appenderConsoleType();

    @Key("appender.console.name")
    @DefaultValue("STDOUT")
    String appenderConsoleName();

    @Key("appender.console.target")
    @DefaultValue("SYSTEM_OUT")
    String appenderConsoleTarget();

    @Key("appender.console.layout.type")
    @DefaultValue("PatternLayout")
    String appenderConsoleLayoutType();

    @Key("appender.console.layout.disableAnsi")
    @DefaultValue("false")
    boolean appenderConsoleLayoutDisableAnsi();

    @Key("appender.console.layout.pattern")
    @DefaultValue("%highlight{[%p]}{FATAL=red blink, ERROR=red bold, WARN=yellow bold, INFO=fg_#0060a8 bold, DEBUG=fg_#43b02a bold, TRACE=black} %style{%m} %style{| @%d{hh:mm:ss a}}{bright_black} %n")
    String appenderConsoleLayoutPattern();

    @Key("appender.console.filter.threshold.type")
    @DefaultValue("ThresholdFilter")
    String appenderConsoleFilterThresholdType();

    @Key("appender.console.filter.threshold.level")
    @DefaultValue("info")
    String appenderConsoleFilterThresholdLevel();

    @Key("appender.file.type")
    @DefaultValue("File")
    String appenderFileType();

    @Key("appender.file.name")
    @DefaultValue("LOGFILE")
    String appenderFileName();

    @Key("appender.file.fileName")
    @DefaultValue("target/logs/log4j.log")
    String appenderFileFileName();

    @Key("appender.file.layout.type")
    @DefaultValue("PatternLayout")
    String appenderFileLayoutType();

    @Key("appender.file.layout.pattern")
    @DefaultValue("[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1} - %msg%n")
    String appenderFileLayoutPattern();

    @Key("appender.file.filter.threshold.type")
    @DefaultValue("ThresholdFilter")
    String appenderFileFilterThresholdType();

    @Key("appender.file.filter.threshold.level")
    @DefaultValue("debug")
    String appenderFileFilterThresholdLevel();

    @Key("logger.selenium.name")
    @DefaultValue("org.openqa.selenium.remote.LoggingWebDriver")
    String seleniumLoggerName();

    @Key("logger.selenium.level")
    @DefaultValue("DEBUG")
    String seleniumLoggerLevel();

    @Key("logger.selenium.target")
    @DefaultValue("SYSTEM_OUT")
    String seleniumLoggerTarget();

    @Key("logger.selenium.appenderRef.stdout.ref")
    @DefaultValue("STDOUT")
    String seleniumLoggerStdoutAppenderRef();

    @Key("logger.selenium.appenderRef.file.ref")
    @DefaultValue("LOGFILE")
    String seleniumLoggerFileAppenderRef();

    @Key("logger.selenium.manager.name")
    @DefaultValue("org.openqa.selenium.manager.SeleniumManager")
    String seleniumManagerLoggerName();

    @Key("logger.selenium.manager.level")
    @DefaultValue("WARN")
    String seleniumManagerLoggerLevel();

    @Key("logger.selenium.manager.target")
    @DefaultValue("SYSTEM_OUT")
    String seleniumManagerLoggerTarget();

    @Key("logger.selenium.manager.appenderRef.stdout.ref")
    @DefaultValue("STDOUT")
    String seleniumManagerLoggerStdoutAppenderRef();

    @Key("logger.selenium.manager.appenderRef.file.ref")
    @DefaultValue("LOGFILE")
    String seleniumManagerLoggerFileAppenderRef();

    @Key("logger.selenium.cdp.name")
    @DefaultValue("org.openqa.selenium.devtools.CdpVersionFinder")
    String cdpLoggerName();

    @Key("logger.selenium.cdp.level")
    @DefaultValue("WARN")
    String cdpLoggerLevel();

    @Key("logger.selenium.cdp.appenderRef.stdout.ref")
    @DefaultValue("STDOUT")
    String cdpLoggerStdoutAppenderRef();

    @Key("logger.selenium.cdp.appenderRef.file.ref")
    @DefaultValue("LOGFILE")
    String cdpLoggerFileAppenderRef();

    @Key("rootLogger.level")
    @DefaultValue("DEBUG")
    String rootLoggerLevel();

    @Key("rootLogger.target")
    @DefaultValue("SYSTEM_OUT")
    String rootLoggerTarget();

    @Key("rootLogger.appenderRef.stdout.ref")
    @DefaultValue("STDOUT")
    String rootLoggerStdoutAppenderRef();

    @Key("rootLogger.appenderRef.file.ref")
    @DefaultValue("LOGFILE")
    String rootLoggerFileAppenderRef();

    @Key("rootLogger.loggerRef.selenium.ref")
    @DefaultValue("STDOUT")
    String seleniumLoggerRef();

    @Key("rootLogger.loggerRef.selenium.manager.ref")
    @DefaultValue("STDOUT")
    String seleniumManagerLoggerRef();

    @Key("rootLogger.loggerRef.selenium.cdp.ref")
    @DefaultValue("STDOUT")
    String cdpLoggerRef();

    @Key("logger.app.name")
    @DefaultValue("org.apache.http.impl.client")
    String appLoggerName();

    @Key("logger.app.level")
    @DefaultValue("WARN")
    String appLoggerLevel();

    @Key("logger.app.additivity")
    @DefaultValue("false")
    boolean appLoggerAdditivity();

    @Key("logger.app.appenderRef.stdout.ref")
    @DefaultValue("STDOUT")
    String appLoggerStdoutAppenderRef();

    @Key("logger.app.appenderRef.file.ref")
    @DefaultValue("LOGFILE")
    String appLoggerFileAppenderRef();

}
