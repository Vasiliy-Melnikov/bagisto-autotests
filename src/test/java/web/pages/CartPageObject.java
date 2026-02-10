package web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CartPageObject {
    private ElementsCollection applyCouponCandidates() {
        return $$x("//*[self::span or self::a or self::button]" +
                "[contains(normalize-space(.),'Apply Coupon')]");
    }

    private SelenideElement visibleApplyCoupon() {
        return applyCouponCandidates()
                .filter(visible)
                .first();
    }
    private SelenideElement visibleCouponInput() {
        return $$("input[placeholder='Enter your code'], input[name='code']")
                .filter(visible)
                .first();
    }
    private SelenideElement applyButtonNearInput(SelenideElement input) {
        SelenideElement container = input.closest("form");
        if (!container.exists()) {
            container = input.closest("div");
        }
        return container.$x(".//button[normalize-space()='Apply']")
                .shouldBe(visible);
    }

    private final SelenideElement couponInlineError =
            $x("//*[contains(normalize-space(.),'The code field is required') or contains(normalize-space(.),'required')]");

    private final SelenideElement couponToast =
            $x("//*[contains(.,'Coupon code is invalid') or contains(.,'invalid')]");

    @Step("Открыть модалку купона")
    public CartPageObject openCouponModal() {
        SelenideElement toggle = visibleApplyCoupon()
                .shouldBe(visible, Duration.ofSeconds(30))
                .scrollIntoView("{block:'center'}");

        executeJavaScript("arguments[0].click()", toggle);
        SelenideElement input = visibleCouponInput()
                .shouldBe(visible);
        executeJavaScript("arguments[0].focus()", input);

        return this;
    }

    @Step("Применить купон: {coupon}")
    public CartPageObject applyCoupon(String coupon) {
        openCouponModal();

        SelenideElement input = visibleCouponInput()
                .shouldBe(visible, enabled)
                .scrollIntoView("{block:'center'}");
        executeJavaScript(
                "arguments[0].value='';" +
                        "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
                        "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
                input
        );

        input.setValue(coupon);
        executeJavaScript(
                "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
                        "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
                input
        );

        input.shouldHave(value(coupon), Duration.ofSeconds(5));

        SelenideElement applyBtn = applyButtonNearInput(input)
                .shouldBe(visible, enabled);

        executeJavaScript("arguments[0].click()", applyBtn);

        return this;
    }

    @Step("Ошибка купона отображается")
    public CartPageObject couponErrorVisible() {
        if (couponInlineError.exists()) {
            couponInlineError.shouldBe(visible, Duration.ofSeconds(10));
        } else {
            couponToast.shouldBe(visible, Duration.ofSeconds(10));
        }
        return this;
    }
}









