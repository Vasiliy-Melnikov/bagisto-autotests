package web.pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;

public class SearchResultsPageObject {

    private final ElementsCollection productLinks = $$x(
            "//main//a[" +
                    "not(contains(@href,'/search')) " +
                    "and not(contains(@href,'/checkout')) " +
                    "and not(contains(@href,'#'))" +
                    "]"
    ).filter(visible);

    @Step("Проверить, что результаты поиска отображаются")
    public SearchResultsPageObject resultsExist() {
        productLinks.shouldHave(sizeGreaterThan(0));
        return this;
    }

    @Step("Открыть первый товар из результатов поиска")
    public ProductPageObject openFirstProduct() {
        productLinks.first().shouldBe(visible).click();
        return new ProductPageObject().productPageOpened();
    }
}













