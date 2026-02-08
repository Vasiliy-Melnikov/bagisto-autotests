package api.tests;

import api.config.ApiConfig;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class ApiTestBase {

    protected static final ApiConfig cfg =
            ConfigFactory.create(ApiConfig.class, System.getProperties());

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}



