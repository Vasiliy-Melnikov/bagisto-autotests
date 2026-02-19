package web.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class ContentPageObject {

    private final SelenideElement main = $("main");
    private final SelenideElement titleOrMain =
            $$("h1,h2,main").filter(visible).first();

    private final SelenideElement newsletterEmail =
            $("input[name='email'], input[type='email']");
    private final SelenideElement newsletterSuccess =
            $x("//*[contains(translate(normalize-space(.)," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'subscrib')" +
                    " and (contains(translate(normalize-space(.)," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'success')" +
                    " or contains(translate(normalize-space(.)," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'thank'))]");

    @Step("Открыть страницу Privacy Policy по прямой ссылке /page/privacy-policy")
    public ContentPageObject openPrivacyPolicyByDirectLink() {
        open("/page/privacy-policy");
        return this;
    }

    @Step("Privacy Policy страница открыта и контент отображается")
    public ContentPageObject privacyPolicyOpened() {
        webdriver().shouldHave(urlContaining("privacy"));
        main.shouldBe(visible, Duration.ofSeconds(30));
        titleOrMain.shouldBe(visible)
                .shouldHave(matchText("(?is).*privacy.*policy.*"));

        return this;
    }
    @Step("Подписаться на newsletter: {email}")
    public ContentPageObject subscribeToNewsletter(String email) {
        newsletterEmail.scrollTo()
                .shouldBe(visible, Duration.ofSeconds(20))
                .setValue(email);

        newsletterEmail.closest("form")
                .$("button[type='submit']")
                .scrollTo()
                .click();
        newsletterSuccess.shouldBe(visible, Duration.ofSeconds(20));
        return this;
    }
}
