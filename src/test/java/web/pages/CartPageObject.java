package web.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class CartPageObject {

    private final SelenideElement cartSummaryTitle =
            $x("//*[normalize-space()='Cart Summary' or normalize-space()='Сводка корзины']");

    private final SelenideElement applyCouponLink =
            $x("//*[@role='button' and contains(normalize-space(),'Apply Coupon')]");

    private final SelenideElement couponModalTitle =
            $x("//h2[contains(normalize-space(),'Apply Coupon')]");

    private final SelenideElement couponInput =
            $("input[placeholder*='Enter']");

    private final SelenideElement applyButton =
            $x("//button[normalize-space()='Apply' or contains(.,'Apply')]");

    private final SelenideElement couponErrorToast =
            $x("//*[contains(.,'Coupon code is invalid') or contains(.,'invalid') or contains(.,'required')]");

    @Step("Открыть корзину")
    public CartPageObject openCart() {
        open("/checkout/cart");
        webdriver().shouldHave(urlContaining("/checkout/cart"));
        cartSummaryTitle.shouldBe(visible);
        return this;
    }

    @Step("Страница корзины открыта")
    public CartPageObject cartPageOpened() {
        webdriver().shouldHave(urlContaining("/checkout/cart"));
        cartSummaryTitle.shouldBe(visible);
        return this;
    }

    @Step("Открыть форму Apply Coupon")
    public CartPageObject openApplyCoupon() {
        applyCouponLink.scrollIntoView("{block: 'center'}").shouldBe(visible).click();
        couponModalTitle.shouldBe(visible);
        couponInput.shouldBe(visible);
        return this;
    }

    @Step("Применить купон: {coupon}")
    public CartPageObject applyCoupon(String coupon) {
        couponInput.shouldBe(visible).setValue(coupon);
        applyButton.shouldBe(enabled).click();
        return this;
    }

    @Step("Ошибка купона отображается")
    public CartPageObject couponErrorVisible() {
        couponErrorToast.shouldBe(visible);
        return this;
    }
}




