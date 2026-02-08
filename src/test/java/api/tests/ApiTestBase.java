package api.tests;

import api.config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

import static api.specs.Specs.shopReqSpec;

public class ApiTestBase {

    protected static final ApiConfig cfg =
            ConfigFactory.create(ApiConfig.class, System.getProperties());

    protected static RequestSpecification shopReq;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = cfg.baseUri();
        RestAssured.basePath = join(cfg.basePath(), cfg.shopApiBasePath());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        shopReq = shopReqSpec();
    }

    private static String join(String left, String right) {
        String l = left == null ? "" : left.trim();
        String r = right == null ? "" : right.trim();

        if (l.endsWith("/")) l = l.substring(0, l.length() - 1);
        if (!r.isEmpty() && !r.startsWith("/")) r = "/" + r;

        return l + r;
    }
}




