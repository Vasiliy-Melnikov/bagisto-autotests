package web.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.pages.HomePageObject;

@Epic("Bagisto Web")
@Feature("Search & Catalog")
public class SearchTests extends BaseTest {

    @Test
    @Story("Search")
    @Owner("vasiliy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Поиск: по запросу есть результаты")
    void searchShouldReturnResults() {
        new HomePageObject()
                .openHome()
                .search("shirt")
                .resultsExist();
    }

    @Test
    @Story("Product")
    @Owner("vasiliy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Поиск: можно открыть товар из выдачи")
    void openProductFromSearch() {
        new HomePageObject()
                .openHome()
                .search("shirt")
                .resultsExist()
                .openFirstProduct()
                .productPageOpened();
    }

    @Test
    @Story("Search")
    @Owner("vasiliy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Поиск по частичному слову: можно открыть товар из выдачи")
    void searchByPartialQuery() {
        new HomePageObject()
                .openHome()
                .search("sh")
                .resultsExist()
                .openFirstProduct()
                .productPageOpened();
    }

    @Test
    @Story("Search")
    @Owner("vasiliy")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Поиск по слову в верхнем регистре: есть результаты")
    void searchUppercase() {
        new HomePageObject()
                .openHome()
                .search("SHI")
                .resultsExist();
    }
}

