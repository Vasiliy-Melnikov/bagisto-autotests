package web.tests;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.data.TestData;
import web.pages.HomePageObject;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

@Epic("Bagisto Web")
@Feature("Cart")
public class CartTests extends BaseTest {

    private final TestData testData = new TestData();

    @Test
    @Story("Add to cart")
    @Owner("vasiliy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Покупка через Buy Now")
    void addToCartFromSearch() {
        new HomePageObject()
                .openHome()
                .search("shirt")
                .resultsExist()
                .openFirstProduct()
                .productPageOpened()
                .selectRandomAgeGroup()
                .setQuantity(2)
                .clickBuyNow();
    }

    @Test
    @Story("Coupon")
    @Owner("vasiliy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Попытка применить купон (негативный кейс)")
    void applyInvalidCoupon() {
        String coupon = testData.invalidCoupon();

        new HomePageObject()
                .openHome()
                .search("shirt")
                .resultsExist()
                .openFirstProduct()
                .productPageOpened()
                .selectRandomAgeGroup()
                .setQuantity(2)
                .clickBuyNow()
                .applyCoupon(coupon)
                .couponErrorVisible();
    }

    @Test
    @Story("Cart")
    @Owner("vasiliy")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Корзина открывается по прямой ссылке /checkout/cart")
    void cartOpenByLink() {
        new HomePageObject().openHome();

        open("/checkout/cart");
        webdriver().shouldHave(urlContaining("/checkout/cart"));
        $("main#main").shouldBe(visible);
        $("ol li[aria-current='page']").shouldHave(text("Cart"));

        SelenideElement empty = $("p[role='heading']");
        ElementsCollection cartContent = $$("main [data-testid*='cart'], main table, main [class*='cart'], main [class*='checkout']");

        Wait().until(driver -> empty.isDisplayed() || cartContent.filter(visible).size() > 0);

        if (empty.isDisplayed()) {
            empty.shouldHave(text("You don’t have a product in your cart."));
        } else {
            cartContent.filter(visible).shouldHave(sizeGreaterThan(0));
        }
    }

}








