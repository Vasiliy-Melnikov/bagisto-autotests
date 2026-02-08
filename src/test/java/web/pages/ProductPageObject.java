package web.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ClickOptions;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class ProductPageObject {

    private final SelenideElement productTitle =
            $("main h1:not(.shimmer), main .product-title:not(.shimmer)");

    private final SelenideElement addToCartButton =
            $x("//button[contains(.,'Add To Cart') or contains(.,'Add to Cart') or contains(.,'В корзину')]");

    private final SelenideElement qtyPlus =
            $x("//*[(self::button or self::span) and (@role='button' or self::button) and " +
                    "(contains(@aria-label,'Increase Quantity') or contains(@class,'icon-plus') or normalize-space(.)='+')]");

    private final SelenideElement addedToast =
            $x("//*[contains(.,'Item Added Successfully') or contains(.,'Added Successfully')]");

    @Step("Страница товара открыта")
    public ProductPageObject productPageOpened() {
        productTitle.shouldBe(visible);
        return this;
    }

    @Step("Выбрать любой Age Group (если есть)")
    public ProductPageObject selectAnyAgeGroup() {
        var optionsRoot = $("#selected_configurable_option");
        if (!optionsRoot.exists()) {
            return this;
        }

        var optionsContainer = optionsRoot.closest("div");
        optionsContainer.$$x(".//h2").shouldBe(sizeGreaterThan(0));
        var ageHeaders = optionsContainer.$$x(
                ".//h2[contains(translate(normalize-space(.), 'A-Z', 'a-z'), 'age group')]"
        ).filter(visible);

        if (!ageHeaders.isEmpty()) {
            selectFirstOption(ageHeaders.first().closest("div"));
            return this;
        }

        var allHeaders = optionsContainer.$$x(".//h2").filter(visible);
        for (var header : allHeaders) {
            selectFirstOption(header.closest("div"));
        }
        return this;
    }

    private boolean selectFirstOption(SelenideElement block) {
        if (block == null || !block.exists()) {
            return false;
        }

        var select = block.$("select");
        if (select.exists()) {
            select.scrollIntoView("{block: 'center'}").shouldBe(visible, enabled);
            var options = select.$$x(".//option[normalize-space(@value)!='']").filter(not(disabled));
            if (!options.isEmpty()) {
                select.selectOptionByValue(options.first().getAttribute("value"));
                return true;
            }
        }

        var labels = block.$$x(".//label[.//input[@type='radio' and not(@disabled)]]").filter(visible);
        if (!labels.isEmpty()) {
            labels.first().scrollIntoView("{block: 'center'}").click();
            return true;
        }

        var buttons = block.$$x(".//button[not(@disabled)]").filter(visible);
        if (!buttons.isEmpty()) {
            buttons.first().scrollIntoView("{block: 'center'}").click();
            return true;
        }

        return false;
    }

    @Step("Установить количество: {count}")
    public ProductPageObject setQuantity(int count) {
        if (count <= 1) return this;

        qtyPlus.scrollIntoView("{block: 'center'}").shouldBe(visible, enabled);
        for (int i = 0; i < count - 1; i++) {
            qtyPlus.click();
        }
        return this;
    }

    @Step("Нажать Add To Cart")
    public CartPageObject addToCart() {
        addToCartButton.scrollIntoView("{block: 'center'}").shouldBe(visible, enabled);
        addToCartButton.click(ClickOptions.usingJavaScript());
        addedToast.shouldBe(visible);
        return new CartPageObject();
    }
}





