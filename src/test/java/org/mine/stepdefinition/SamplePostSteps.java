package org.mine.stepdefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.equalTo;
import java.util.Map;
public class SamplePostSteps extends BaseSteps {
    protected Response response;
    private String baseUri;
    private Map<String, String> headers;
    private String requestBody;

    @Given("the base URI is {string}")
    public void setBaseUri(String uri) {
//        setUpRestAssured(uri);
        this.baseUri=uri;
        RestAssured.baseURI = baseUri;
    }

    @And("the headers are:")
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
        // Set headers in RestAssured if needed
        // headers.forEach((key, value) -> RestAssured.given().header(key, value));
        // Don't set headers here - they should be set in the actual request
    }

    @And("the request body is:")
    public void setRequestBody(String body) {
        this.requestBody = body;
    }

    @When("a POST request is made to {string}")
    public void makePostRequest(String endpoint) {

        RequestSpecification request = RestAssured.given();
        // Apply headers to this specific request
        headers.forEach(request::header);


        response =RestAssured.given()
                .headers(headers)
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        response.then().assertThat().statusCode(expectedStatusCode);
    }

    @And("the response body field {string} should equal {string}")
    public void verifyResponseBodyField(String field, String expectedValue) {
        response.then().assertThat().body(field, equalTo(expectedValue));
    }
}
