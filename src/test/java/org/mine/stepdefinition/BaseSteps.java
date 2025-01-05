package org.mine.stepdefinition;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BaseSteps {
    protected Response response;
    // Add common step definition methods used across different feature files
    protected void setUpRestAssured(String baseUri) {
        RestAssured.baseURI = baseUri;
    }
}
