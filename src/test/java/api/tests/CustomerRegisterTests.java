package api.tests;

import api.helpers.CustomerRegisterHelper;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Bagisto API")
@Feature("Customer registration")
public class CustomerRegisterTests extends ApiTestBase {

    @Test
    @Story("Register")
    @Owner("vasiliy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("POST /customer/register — успешная регистрация нового клиента")
    void customerCanRegister() {
        String email = CustomerRegisterHelper.registerNewCustomer(shopReq);
        assertThat(email).isNotBlank().contains("@");
    }
}


