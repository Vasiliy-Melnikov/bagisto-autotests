package api.tests;

import api.helpers.CustomerRegisterHelper;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Bagisto API")
@Feature("Customer registration")
public class CustomerRegisterTests extends ApiTestBase {

    @Test
    @Story("Register")
    @Owner("vasiliy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("POST /customer/register — успешная регистрация нового клиента")
    void customerCanRegister() {
        CustomerRegisterHelper.registerNewCustomer(shopReq);
    }
}

