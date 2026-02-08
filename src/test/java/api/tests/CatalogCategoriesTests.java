package api.tests;

import api.models.catalog.Category;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static api.specs.Specs.baseShopReq;
import static api.specs.Specs.ok200Json;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Bagisto API")
@Feature("Catalog (Shop API)")
public class CatalogCategoriesTests extends ApiTestBase {

    @Test
    @DisplayName("GET /categories — возвращает JSON")
    void getCategories() {
        given()
                .spec(baseShopReq(cfg))
                .redirects().follow(false)
                .when()
                .get("/categories")
                .then()
                .spec(ok200Json());
    }

    @Test
    @Story("Categories")
    @Owner("vasiliy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("GET /categories — маппится в POJO и содержит translations")
    void getCategoriesAsPojo() {
        var json = given()
                .spec(baseShopReq(cfg))
                .when()
                .get("/categories")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();

        List<Category> categories = json.getList("data", Category.class);

        assertThat(categories).isNotNull();
        assertThat(categories).isNotEmpty();

        Category first = categories.get(0);
        assertThat(first.id).isNotNull();
        assertThat(first.name).isNotBlank();
        assertThat(first.translations).isNotNull();
    }

    @Test
    @Story("Categories")
    @Owner("vasiliy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /categories?page=1 — smoke: meta.pagination присутствует")
    void categoriesPaginationMetaPresent() {
        var json = given()
                .spec(baseShopReq(cfg))
                .when()
                .get("/categories?page=1")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();

        assertThat(json.getInt("meta.current_page")).isEqualTo(1);
        assertThat(json.getInt("meta.per_page")).isGreaterThan(0);
        assertThat(json.getInt("meta.total")).isGreaterThan(0);
        assertThat(json.getString("links.next")).isNotNull();
    }

    @Test
    @Story("Categories")
    @Owner("vasiliy")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("DELETE /categories/{id} - method not supported (500)")
    void deleteCategoryNotSupported() {
        given()
                .spec(baseShopReq(cfg))
                .when()
                .delete("/categories/1")
                .then()
                .statusCode(500);
    }
}


