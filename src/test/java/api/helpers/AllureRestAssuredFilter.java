package api.helpers;

import io.qameta.allure.restassured.AllureRestAssured;

public class AllureRestAssuredFilter {
    private static final AllureRestAssured FILTER = new AllureRestAssured()
            .setRequestTemplate("request.ftl")
            .setResponseTemplate("response.ftl");

    public static AllureRestAssured withTemplates() {
        return FILTER;
    }
}
