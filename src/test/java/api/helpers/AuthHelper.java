package api.helpers;

import api.config.ApiConfig;
import api.models.auth.LoginRequest;
import api.models.auth.LoginResponse;
import io.restassured.response.Response;

import static api.specs.Specs.baseAdminReq;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public final class AuthHelper {

    private static String cachedAdminToken;

    private AuthHelper() {}

    public static synchronized String adminToken(ApiConfig cfg) {
        if (cachedAdminToken != null && !cachedAdminToken.isBlank()) {
            return cachedAdminToken;
        }
        String sysToken = System.getProperty("adminToken", "").trim();
        if (!sysToken.isBlank()) {
            cachedAdminToken = sysToken;
            return cachedAdminToken;
        }
        String cfgToken = (cfg.adminToken() == null) ? "" : cfg.adminToken().trim();
        if (!cfgToken.isBlank()) {
            cachedAdminToken = cfgToken;
            return cachedAdminToken;
        }
        String email = System.getProperty("apiEmail", cfg.email());
        String password = System.getProperty("apiPassword", cfg.password());
        String deviceName = System.getProperty("deviceName", "autotests");

        Response response = given()
                .spec(baseAdminReq(cfg))
                .body(new LoginRequest(email, password, deviceName))
                .when()
                .post("/admin/login")
                .andReturn();

        assertThat(response.statusCode())
                .as("Admin login status. Response: %s", response.asPrettyString())
                .isEqualTo(200);

        LoginResponse resp = response.as(LoginResponse.class);
        assertThat(resp.token).as("token").isNotBlank();

        cachedAdminToken = resp.token;
        return cachedAdminToken;
    }

    public static synchronized void reset() {
        cachedAdminToken = null;
    }
}



