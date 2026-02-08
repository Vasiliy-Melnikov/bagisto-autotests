package api.tests;

import api.helpers.AuthHelper;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static api.specs.Specs.baseAdminReq;
import static io.restassured.RestAssured.given;

@Epic("Bagisto API")
@Feature("Admin protected")
public class AdminProtectedTests extends ApiTestBase {

    @Test
    @DisplayName("GET /admin/get — доступен с Bearer token")
    void adminGetWithToken() {
        Assumptions.assumeTrue(cfg.enableAdminAuth(),
                "Admin auth tests are disabled. Enable with -DenableAdminAuth=true");

        String token = AuthHelper.adminToken(cfg);

        given()
                .spec(baseAdminReq(cfg))
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/admin/get")
                .then()
                .statusCode(200);
    }
}

