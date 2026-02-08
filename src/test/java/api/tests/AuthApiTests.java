package api.tests;

import api.helpers.AuthHelper;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Bagisto API")
@Feature("Auth (Admin API v1)")
public class AuthApiTests extends ApiTestBase {

    @Test
    @Story("Auth")
    @Owner("vasiliy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("POST /admin/login — возвращает token (200)")
    void loginShouldReturnToken() {
        Assumptions.assumeTrue(cfg.enableAdminAuth(),
                "Admin auth tests are disabled. Enable with -DenableAdminAuth=true");

        String token = AuthHelper.adminToken(cfg);
        assertThat(token).isNotBlank();
    }

}







