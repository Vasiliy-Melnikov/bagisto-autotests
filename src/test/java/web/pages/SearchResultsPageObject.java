package web.pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
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
    ).filter(visible);

    @Step("Проверить, что результаты поиска отображаются")
    public SearchResultsPageObject resultsExist() {
        productLinks.shouldHave(sizeGreaterThan(0));
        return this;
    }

    @Step("Открывается первый товар из списка")
    public ProductPageObject openFirstProduct() {
        productLinks.shouldHave(sizeGreaterThan(0));
        productLinks.first().scrollIntoView("{block:'center'}").click();
        return new ProductPageObject();
    }
}













