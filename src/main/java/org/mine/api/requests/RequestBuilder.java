package org.mine.api.requests;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RequestBuilder {
    private static RequestSpecification getBaseRequest() {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + System.getProperty("api.token"));
    }
    public static RequestSpecification buildPostRequest(String body) {
        return getBaseRequest().body(body);
    }

    // Add other request builders as needed
}
