package api.specs;

import api.config.ApiConfig;
import api.helpers.AllureRestAssuredFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specs {

    public static RequestSpecification baseShopReq(ApiConfig cfg) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setBaseUri(cfg.baseUri())
                .setBasePath(join(cfg.basePath(), cfg.shopApiBasePath()))
                .addFilter(AllureRestAssuredFilter.withTemplates())
                .build();
    }

    public static RequestSpecification baseAdminReq(ApiConfig cfg) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setBaseUri(cfg.baseUri())
                .setBasePath(join(cfg.basePath(), cfg.adminApiBasePath()))
                .addFilter(AllureRestAssuredFilter.withTemplates())
                .build();
    }

    public static ResponseSpecification ok200Json() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }

    private static String join(String left, String right) {
        String l = left == null ? "" : left.trim();
        String r = right == null ? "" : right.trim();

        if (l.endsWith("/")) l = l.substring(0, l.length() - 1);
        if (!r.isEmpty() && !r.startsWith("/")) r = "/" + r;

        return l + r;
    }
}






