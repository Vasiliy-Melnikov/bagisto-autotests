package web.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProductPageObject {

    private final SelenideElement productTitle =
            $("main h1, h1, .product-title, [class*='product'] h1");

    private final SelenideElement buyNowButton =
            $x("//main//button[normalize-space()='Buy Now' and contains(@class,'primary-button')]");

    private final SelenideElement qtyPlus =
            $("main .icon-plus");

    private final ElementsCollection ageGroupOptions =
            $$x("//main//h2[contains(normalize-space(.),'Select Age Group')]/..//label");

    private final SelenideElement notFoundLike =
            $x("//main//*[self::h1 or self::h2 or self::p or self::div]" +
                    "[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'not found') " +
                    " or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'no results')]");

    @Step("Открыта страница товара")
    public ProductPageObject productPageOpened() {
        productTitle.shouldBe(visible, Duration.ofSeconds(30))
                .shouldHave(matchText(".*\\S.*"), Duration.ofSeconds(30));
        if (notFoundLike.exists() && notFoundLike.isDisplayed()) {
            throw new AssertionError("Открылась страница not found/no results вместо карточки товара");
        }
        buyNowButton.shouldBe(visible, Duration.ofSeconds(30)); // ключевое: ждем Buy Now
        return this;
    }

    @Step("Выбрать случайный Age Group (если есть)")
    public ProductPageObject selectRandomAgeGroup() {
        if (ageGroupOptions.isEmpty()) {
            return this;
        }

        ageGroupOptions.filter(visible)
                .shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(10));

        ElementsCollection visibleOptions = ageGroupOptions.filter(visible);
        int size = visibleOptions.size();

        int randomIndex = new Random().nextInt(size);
        SelenideElement randomOption = visibleOptions.get(randomIndex);

        executeJavaScript("arguments[0].scrollIntoView({block:'center'});", randomOption);
        sleep(150);
        executeJavaScript("arguments[0].click();", randomOption);

        return this;
    }

    @Step("Установить количество: {count}")
    public ProductPageObject setQuantity(int count) {
        if (count <= 1) return this;

        if (!qtyPlus.exists()) {
            return this;
        }

        qtyPlus.shouldBe(visible, Duration.ofSeconds(10));

        for (int i = 0; i < count - 1; i++) {
            executeJavaScript("arguments[0].click();", qtyPlus);
            sleep(100);
        }
        return this;
    }

    @Step("Нажать Buy Now")
    public CartPageObject clickBuyNow() {
        buyNowButton.shouldBe(visible).shouldBe(enabled);
        executeJavaScript("arguments[0].scrollIntoView({block:'center'});", buyNowButton);
        sleep(150);
        executeJavaScript("arguments[0].click();", buyNowButton);

        return new CartPageObject();
    }
}












