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

    @Step("Проверить, что результаты поиска отображаются")
    public SearchResultsPageObject resultsExist() {
        productLinks.shouldHave(sizeGreaterThan(0), Duration.ofSeconds(30));
        productLinks.filter(visible).shouldHave(sizeGreaterThan(0), Duration.ofSeconds(30));
        return this;
    }

    @Step("Открыть первый товар из списка")
    public ProductPageObject openFirstProduct() {
        resultsExist();

        SelenideElement first = productLinks.filter(visible).first()
                .shouldBe(visible, enabled);
        executeJavaScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", first);
        sleep(200);
        executeJavaScript("arguments[0].click();", first);

        return new ProductPageObject();
    }
}















