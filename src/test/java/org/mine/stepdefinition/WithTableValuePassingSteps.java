package org.mine.stepdefinition;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.equalTo;
import java.util.Map;

public class WithTableValuePassingSteps extends BaseSteps {
    protected Response response;
    private String baseUri;
    private Map<String, String> headers;
    private String requestBody;

    @Given("1the base URI is {string}")
    public void setBaseUri(String uri) {
//        setUpRestAssured(uri);
        this.baseUri=uri;
        RestAssured.baseURI = baseUri;
    }

    @And("1the headers are:")
    public void setHeaders(DataTable dataTable) {
        // Use dataTable.asMap() if the data table has exactly 2 columns, as a single map is sufficient.
        // Use dataTable.asMaps() if the data table has more than 2 columns, as a list of maps is required.
        this.headers = dataTable.asMap();
        System.out.println("With Table Value Passing  ----- "+dataTable.asMap());
        System.out.println("Header 1st key      "+dataTable.asMaps().get(0).get("key"));
        System.out.println("Header 1st value   "+dataTable.asMaps().get(0).get("value"));
        System.out.println("Header 2nd key      "+dataTable.asMaps().get(1).get("key"));
        System.out.println("Header 2nd value   "+dataTable.asMaps().get(1).get("value"));
        // Set headers in RestAssured if needed
        // headers.forEach((key, value) -> RestAssured.given().header(key, value));
        // Don't set headers here - they should be set in the actual request
    }

    @And("1the request body is:")
    public void setRequestBody(String body) {
        this.requestBody = body;
    }

    @When("1a POST request is made to {string}")
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

    @Then("1the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        response.then().assertThat().statusCode(expectedStatusCode);
    }

    @And("1the response body field {string} should equal {string}")
    public void verifyResponseBodyField(String field, String expectedValue) {
        response.then().assertThat().body(field, equalTo(expectedValue));
    }
}

