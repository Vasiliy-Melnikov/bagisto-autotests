package web.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.data.TestData;
import web.pages.HomePageObject;

import static com.codeborne.selenide.Selenide.open;


@Epic("Bagisto Web")
@Feature("Cart")
public class CartTests extends BaseTest {
        private final TestData testData = new TestData();

    @Test
    @Story("Add to cart")
    @Owner("vasiliy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Добавление товара в корзину из поиска")
    void addToCartFromSearch() {
        new HomePageObject()
                .openHome()
                .search("shirt")
                .resultsExist()
                .openFirstProduct()
                .selectAnyAgeGroup()
                .setQuantity(1)
                .addToCart()
                .openCart()
                .cartPageOpened();
    }

    @Test
    @Story("Coupon")
    @Owner("vasiliy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Попытка применить невалидный купон")
    void applyInvalidCoupon() {

        String coupon = testData.invalidCoupon();

        new HomePageObject()
                .openHome()
                .search("shirt")
                .resultsExist()
                .openFirstProduct()
                .selectAnyAgeGroup()
                .setQuantity(1)
                .addToCart()
                .openCart()
                .openApplyCoupon()
                .applyCoupon(coupon)
                .couponErrorVisible();
    }


    @Test
    @Story("Cart")
    @Owner("vasiliy")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Корзина открывается по ссылке cart")
    void cartOpenByLink() {
        new HomePageObject()
                .openHome();
        open("/checkout/cart");
    }
}





