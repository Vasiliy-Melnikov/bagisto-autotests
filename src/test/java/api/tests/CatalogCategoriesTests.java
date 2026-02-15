package api.tests;

import api.models.catalog.Category;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static api.specs.Specs.jsonWithStatus;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Bagisto API")
@Feature("Catalog (Shop API)")
public class CatalogCategoriesTests extends ApiTestBase {

    @Test
    @DisplayName("GET /categories — возвращает 200 и непустой список data")
    void getCategories() {
        var json = given()
                .spec(shopReq)
                .redirects().follow(false)
                .when()
                .get("/categories")
                .then()
                .spec(jsonWithStatus(200))
                .extract()
                .jsonPath();

        assertThat(json.getList("data")).isNotNull().isNotEmpty();

        Object errors = json.get("errors");
        assertThat(errors).as("errors отсутствует").isNull();
    }

    @Test
    @Story("Categories")
    @Owner("vasiliy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("GET /categories — маппится в POJO и содержит ключевые поля")
    void getCategoriesAsPojo() {
        var json = given()
                .spec(shopReq)
                .when()
                .get("/categories")
                .then()
                .spec(jsonWithStatus(200))
                .extract()
                .jsonPath();

        List<Category> categories = json.getList("data", Category.class);

        assertThat(categories).isNotNull().isNotEmpty();

        Category first = categories.get(0);
        assertThat(first.id).as("id").isNotNull();
        assertThat(first.name).as("name").isNotBlank();
        assertThat(first.translations).as("translations").isNotNull();
        assertThat(first.translations).as("translations size").isNotEmpty();
    }

    @Test
    @Story("Categories")
    @Owner("vasiliy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /categories?page=1 — pagination meta корректна")
    void categoriesPaginationMetaPresent() {
        var json = given()
                .spec(shopReq)
                .queryParam("page", 1)
                .when()
                .get("/categories")
                .then()
                .spec(jsonWithStatus(200))
                .extract()
                .jsonPath();

        Object links = json.get("links");
        assertThat(links).isNotNull();
        assertThat(json.getString("links.first")).isNotBlank();
    }

    @Test
    @Story("Categories")
    @Owner("vasiliy")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("DELETE /categories/{id} — негатив: метод не поддерживается")
    void deleteCategoryNotSupported() {
        given()
                .spec(shopReq)
                .when()
                .delete("/categories/1")
                .then()
                .spec(jsonWithStatus(500));
    }
}




