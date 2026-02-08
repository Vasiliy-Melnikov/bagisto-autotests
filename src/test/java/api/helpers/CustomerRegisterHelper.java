package api.helpers;

import api.models.auth.RegisterCustomerRequest;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerRegisterHelper {

    private static final Faker faker = new Faker();
    private static final String STATIC_PASSWORD = "Password123!";

    private CustomerRegisterHelper() {
    }

    public static String registerNewCustomer(RequestSpecification spec) {

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = "autotest_" + System.currentTimeMillis() + "@mail.com";

        RegisterCustomerRequest body = new RegisterCustomerRequest(
                firstName,
                lastName,
                email,
                STATIC_PASSWORD
        );

        Response response = given()
                .spec(spec)
                .body(body)
                .when()
                .post("/customer/register")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String message = response.jsonPath().getString("message");

        assertThat(message)
                .as("Register message. Response: %s", response.asString())
                .containsIgnoringCase("created");

        return email;
    }

    public static String staticPassword() {
        return STATIC_PASSWORD;
    }
}


