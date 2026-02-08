package api.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "system:properties",
        "classpath:config/api.properties"
})
public interface ApiConfig extends Config {

    @Key("api.baseUri")
    @DefaultValue("https://demo.bagisto.com")
    String baseUri();

    @Key("api.basePath")
    @DefaultValue("/bagisto-api-demo-common")
    String basePath();

    @Key("api.shopApiBasePath")
    @DefaultValue("/api/v1")
    String shopApiBasePath();
}
