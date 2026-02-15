package api.tests;

import api.models.catalog.Product;
import api.models.common.PaginatedResponse;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static api.specs.Specs.jsonWithStatus;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Bagisto API")
@Feature("Catalog (Shop API)")
public class CatalogProductsTests extends ApiTestBase {

    @Test
    @DisplayName("GET /products — 200, data не пустой, ошибок нет")
    void getProducts() {
        var json = given()
                .spec(shopReq)
                .redirects().follow(false)
                .when()
                .get("/products")
                .then()
                .spec(jsonWithStatus(200))
                .extract()
                .jsonPath();

        assertThat(json.getList("data"))
                .as("data")
                .isNotNull()
                .isNotEmpty();

        assertThat(json.getString("data[0].name")).as("data[0].name").isNotBlank();
        assertThat(json.getString("data[0].sku")).as("data[0].sku").isNotBlank();
        assertThat(json.getString("data[0].type")).as("data[0].type").isNotBlank();
        assertThat((Object) json.get("data[0].id")).as("data[0].id").isNotNull();
        assertThat((Object) json.get("errors")).as("errors отсутствует").isNull();
    }

    @Test
    @Story("Products")
    @Owner("vasiliy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("GET /products?page=1 — пагинация + data (POJO) корректны")
    void getProductsPage1() {
        PaginatedResponse<Product> resp = given()
                .spec(shopReq)
                .queryParam("page", 1)
                .when()
                .get("/products")
                .then()
                .spec(jsonWithStatus(200))
                .extract()
                .as(new TypeRef<PaginatedResponse<Product>>() {});

        assertThat(resp).isNotNull();
        assertThat(resp.data).as("data").isNotNull().isNotEmpty();

        Product first = resp.data.get(0);
        assertThat(first.name).as("name").isNotBlank();
        assertThat(first.sku).as("sku").isNotBlank();
        assertThat(first.type).as("type").isNotBlank();

        assertThat(resp.meta).as("meta").isNotNull();
        assertThat(resp.meta.currentPage).as("meta.currentPage").isEqualTo(1);

        assertThat(resp.links).as("links").isNotNull();
    }

    @Test
    @Story("Products")
    @Owner("vasiliy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /products — smoke: у первого товара заполнены name/sku/type")
    void productsSmokeFields() {
        var json = given()
                .spec(shopReq)
                .when()
                .get("/products")
                .then()
                .spec(jsonWithStatus(200))
                .extract()
                .jsonPath();

        assertThat((Object) json.get("data[0].id")).as("data[0].id").isNotNull();
        assertThat(json.getString("data[0].name")).as("data[0].name").isNotBlank();
        assertThat(json.getString("data[0].sku")).as("data[0].sku").isNotBlank();
        assertThat(json.getString("data[0].type")).as("data[0].type").isNotBlank();
    }
}




