package web.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "system:properties",
        "classpath:config/${env}.properties"
})
public interface WebDriverConfig extends Config {

    @Key("baseUrl")
    String baseUrl();

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("browserVersion")
    @DefaultValue("")
    String browserVersion();

    @Key("browserSize")
    @DefaultValue("1920x1080")
    String browserSize();

    @Key("pageLoadStrategy")
    @DefaultValue("eager")
    String pageLoadStrategy();

    @Key("isRemote")
    @DefaultValue("false")
    boolean isRemote();

    @Key("remoteUrl")
    @DefaultValue("")
    String remoteUrl();

    @Key("enableVNC")
    @DefaultValue("true")
    boolean enableVNC();

    @Key("enableVideo")
    @DefaultValue("true")
    boolean enableVideo();
}

