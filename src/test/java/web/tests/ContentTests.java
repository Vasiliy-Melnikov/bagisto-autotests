package web.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.pages.HomePageObject;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@Epic("Bagisto Web")
@Feature("Content & Static pages")
public class ContentTests extends BaseTest {

    @Test
    @Story("Footer pages")
    @Owner("vasiliy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Открывается страница Privacy Policy из футера")
    void openPrivacyPolicy() {
        new HomePageObject().openHome();

        open("/page/privacy-policy");
        $("body").shouldBe(visible);
    }

    @Test
    @Story("Newsletter")
    @Owner("vasiliy")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Подписка на newsletter (позитив/смоук)")
    void newsletterSubscribe() {
        new HomePageObject()
                .openHome()
                .subscribeToNewsletter("test+" + System.currentTimeMillis() + "@example.com");
    }

}

