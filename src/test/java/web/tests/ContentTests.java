package web.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.pages.ContentPageObject;
import web.pages.HomePageObject;

@Epic("Bagisto Web")
@Feature("Content & Static pages")
public class ContentTests extends BaseTest {

    @Test
    @Story("Footer pages")
    @Owner("vasiliy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Открывается страница Privacy Policy")
    void openPrivacyPolicy() {
        new HomePageObject().openHome();
        new ContentPageObject()
                .openPrivacyPolicyByDirectLink()
                .privacyPolicyOpened();
    }


    @Test
    @Story("Newsletter")
    @Owner("vasiliy")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Подписка на newsletter (позитив/смоук)")
    void newsletterSubscribe() {
        new HomePageObject()
                .openHome();
        new ContentPageObject()
                .subscribeToNewsletter("test+" + System.currentTimeMillis() + "@example.com");
    }

}

