package api.tests;

import api.models.catalog.Product;
import api.models.common.PaginatedResponse;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
                .spec(shopReq)
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
    @DisplayName("GET /products?page=1 — пагинированный ответ и список data (POJO)")
    void getProductsPage1() {
        PaginatedResponse<Product> resp = given()
                .spec(shopReq)
                .queryParam("page", 1)
                .when()
                .get("/products")
                .then()
                .spec(ok200Json())
                .extract()
                .as(new TypeRef<PaginatedResponse<Product>>() {});

        assertThat(resp).isNotNull();
        assertThat(resp.data).isNotNull().isNotEmpty();
        assertThat(resp.data.get(0).name).isNotBlank();
        assertThat(resp.meta).isNotNull();
        assertThat(resp.meta.currentPage).isEqualTo(1);
        assertThat(resp.links).isNotNull();
    }

    @Test
    @Story("Products")
    @Owner("vasiliy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /products — smoke: data[0] содержит базовые поля")
    void productsSmokeFields() {
        var json = given()
                .spec(shopReq)
                .when()
                .get("/products")
                .then()
                .spec(ok200Json())
                .extract()
                .jsonPath();

        assertThat(json.getList("data")).isNotNull();
        assertThat(json.getString("data[0].name")).isNotBlank();
        assertThat(json.getString("data[0].sku")).isNotBlank();
        assertThat(json.getString("data[0].type")).isNotBlank();
    }
}



