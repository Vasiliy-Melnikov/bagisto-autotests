package web.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class HomePageObject {

    private final SelenideElement searchInput =
            $("input[placeholder*='Search'], input[type='search'], input[name='search'], input[name='query']");

    private final SelenideElement newsletterEmail =
            $("input[name='email'], input[type='email']");

    private final SelenideElement cookieBanner =
            $(".js-cookie-consent");

    private final SelenideElement cookieAcceptButton =
            $x("//div[contains(@class,'js-cookie-consent')]//button[contains(normalize-space(),'Accept')]");

    private final SelenideElement prepareDemoButton =
            $x("//*[self::a or self::button][normalize-space()='Prepare Demo']");

    private final SelenideElement settingUpDemoText =
            $x("//*[contains(normalize-space(.),'Setting up demo') or contains(normalize-space(.),'Setting Up Demo')]");

    @Step("Принять cookies (если баннер отображается)")
    public HomePageObject acceptCookiesIfPresent() {
        if (cookieBanner.exists() && cookieBanner.isDisplayed()) {
            cookieBanner.shouldBe(visible);
            cookieAcceptButton.shouldBe(visible, enabled).click();
            cookieBanner.should(disappear);
        }
        return this;
    }

    @Step("Подготовить демо-стенд (если показывается экран Prepare Demo)")
    public HomePageObject prepareDemoIfNeeded() {
        if (prepareDemoButton.exists()) {
            prepareDemoButton.shouldBe(visible).click();
            if (settingUpDemoText.exists()) {
                settingUpDemoText.shouldBe(visible);
                settingUpDemoText.shouldNotBe(visible, Duration.ofSeconds(60));
            }
            searchInput.shouldBe(visible, Duration.ofSeconds(60));
        }
        return this;
    }

    @Step("Открыть главную страницу")
    public HomePageObject openHome() {
        open("");
        prepareDemoIfNeeded();
        acceptCookiesIfPresent();
        return this;
    }

    @Step("Выполнить поиск по запросу: {query}")
    public SearchResultsPageObject search(String query) {
        searchInput.shouldBe(visible, Duration.ofSeconds(20))
                .setValue(query)
                .pressEnter();

        webdriver().shouldHave(urlContaining("/search"));
        return new SearchResultsPageObject();
    }

    @Step("Подписаться на newsletter: {email}")
    public HomePageObject subscribeToNewsletter(String email) {
        newsletterEmail.scrollTo().shouldBe(visible, Duration.ofSeconds(20)).setValue(email);
        newsletterEmail.closest("form")
                .$("button[type='submit']")
                .scrollTo()
                .click();
        return this;
    }
}













