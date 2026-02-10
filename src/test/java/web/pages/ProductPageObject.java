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
            $("h1, .product-title, [class*='product'] h1");

    private final ElementsCollection ageGroupOptions =
            $$x("//h2[contains(normalize-space(.),'Select Age Group')]/..//label");
    private final SelenideElement buyNowButton =
            $x("//button[contains(@class,'primary-button')][normalize-space()='Buy Now']");

    private final SelenideElement qtyPlus =
            $(".icon-plus");
    private final SelenideElement addedToast =
            $x("//*[contains(.,'Item Added Successfully') or contains(.,'Added Successfully')]");

    @Step("Открыта страница товара")
    public ProductPageObject productPageOpened() {
        productTitle.shouldBe(visible);
        return this;
    }

    @Step("Выбрать случайный Age Group")
    public ProductPageObject selectRandomAgeGroup() {
        ageGroupOptions.filter(visible)
                .shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(10));

        ElementsCollection visibleOptions = ageGroupOptions.filter(visible);
        int size = visibleOptions.size();

        if (size > 0) {
            int randomIndex = new Random().nextInt(size);
            SelenideElement randomOption = visibleOptions.get(randomIndex);
            randomOption.scrollIntoView("{block: 'center'}").click();
        } else {
            throw new RuntimeException("Не найдено доступных опций Age Group (список пуст после ожидания)!");
        }
        return this;
    }

    @Step("Установить количество: {count}")
    public ProductPageObject setQuantity(int count) {
        if (count <= 1) return this;
        qtyPlus.shouldBe(visible);
        for (int i = 0; i < count - 1; i++) {
            qtyPlus.click();
        }
        return this;
    }

    @Step("Нажать Buy Now")
    public CartPageObject clickBuyNow() {
        buyNowButton.shouldBe(visible);
        buyNowButton.scrollIntoView("{block: 'center'}").click();
        return new CartPageObject();
    }
}











