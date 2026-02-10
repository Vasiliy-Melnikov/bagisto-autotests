package web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SearchResultsPageObject {
    private final ElementsCollection productLinks = $$x(
            "//main//a[@href and " +
                    "not(starts-with(@href,'#')) and " +
                    "not(contains(@href,'/search')) and " +
                    "not(contains(@href,'/checkout')) and " +
                    "not(contains(@href,'/customer/')) and " +
                    "not(contains(@href,'/wishlist')) and " +
                    "not(contains(@href,'/compare')) and " +
                    "not(contains(@href,'/page/'))" +
                    "]"
    );

    private SelenideElement firstVisibleProductLink() {
        productLinks.shouldHave(sizeGreaterThan(0), Duration.ofSeconds(30));
        return productLinks.filter(visible).first().shouldBe(visible, Duration.ofSeconds(30));
    }

    @Step("Проверить, что результаты поиска отображаются")
    public SearchResultsPageObject resultsExist() {
        productLinks.filter(visible).shouldHave(sizeGreaterThan(0), Duration.ofSeconds(30));
        return this;
    }

    @Step("Открывается первый товар из списка")
    public ProductPageObject openFirstProduct() {
        SelenideElement first = firstVisibleProductLink();
        executeJavaScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});", first);
        sleep(200);
        executeJavaScript("arguments[0].click();", first);

        return new ProductPageObject();
    }
}














