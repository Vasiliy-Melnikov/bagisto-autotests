package api.tests;

import api.models.catalog.Product;
import api.models.common.PaginatedResponse;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static api.specs.Specs.baseShopReq;
import static api.specs.Specs.ok200Json;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Bagisto API")
@Feature("Catalog (Shop API)")
public class CatalogProductsTests extends ApiTestBase {

    @Test
    @DisplayName("GET /products — возвращает JSON")
    void getProducts() {
        given()
                .spec(baseShopReq(cfg))
                .redirects().follow(false)
                .when()
                .get("/products")
                .then()
                .spec(ok200Json())
                .body("data", notNullValue());
    }

    @Test
    @Story("Products")
    @Owner("vasiliy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("GET /products?page=1 — пагинированный ответ и список data")
    void getProductsPage1() {
        @SuppressWarnings("unchecked")
        PaginatedResponse<Product> resp = given()
                .spec(baseShopReq(cfg))
                .queryParam("page", 1)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract()
                .as(PaginatedResponse.class);

        assertThat(resp).isNotNull();
        assertThat(resp.data).isNotNull();
        assertThat(resp.data).isNotEmpty();
        assertThat(resp.meta).isNotNull();
        assertThat(resp.meta.currentPage).isEqualTo(1);
        assertThat(resp.meta.total).isNotNull();
        assertThat(resp.links).isNotNull();
    }

    @Test
    @Story("Products")
    @Owner("vasiliy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /products — smoke: data[0] содержит базовые поля")
    void productsSmokeFields() {
        var json = given()
                .spec(baseShopReq(cfg))
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();

        assertThat(json.getList("data")).isNotNull();
        assertThat(json.getString("data[0].name")).isNotBlank();
        assertThat(json.getString("data[0].sku")).isNotBlank();
        assertThat(json.getString("data[0].type")).isNotBlank();
    }
}


