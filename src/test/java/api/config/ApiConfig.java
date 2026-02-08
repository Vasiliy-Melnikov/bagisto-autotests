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

    @Key("api.email")
    @DefaultValue("admin@example.com")
    String email();

    @Key("api.password")
    @DefaultValue("admin123")
    String password();

    @Key("api.adminApiBasePath")
    @DefaultValue("/api/v1")
    String adminApiBasePath();

    @Key("api.shopApiBasePath")
    @DefaultValue("/api/v1")
    String shopApiBasePath();

    @Key("api.enableAdminAuth")
    @DefaultValue("false")
    boolean enableAdminAuth();

    @Key("api.adminToken")
    @DefaultValue("")
    String adminToken();
}
