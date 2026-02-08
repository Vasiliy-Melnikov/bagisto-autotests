package api.helpers;

import io.qameta.allure.restassured.AllureRestAssured;

public class AllureRestAssuredFilter {
    public static AllureRestAssured withTemplates() {
        AllureRestAssured filter = new AllureRestAssured();
        filter.setRequestTemplate("request.ftl");
        filter.setResponseTemplate("response.ftl");
        return filter;
    }
}
