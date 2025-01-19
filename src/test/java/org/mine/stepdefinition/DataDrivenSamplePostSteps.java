package org.mine.stepdefinition;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class DataDrivenSamplePostSteps extends BaseSteps {
    protected Response response;
    private String baseUri;
    private Map<String, String> headers;
    private String requestBody;

    @Given("the base URI is  for data driven testing")
    public void setBaseUri() {
//        setUpRestAssured(uri);
        String uri = "https://fakerestapi.azurewebsites.net";
        this.baseUri=uri;
        RestAssured.baseURI = baseUri;
    }

    @And("the headers are:  for data driven testing")
    public void setHeaders(DataTable dataTable) {
        // Use dataTable.asMap() if the data table has exactly 2 columns, as a single map is sufficient.
        // Use dataTable.asMaps() if the data table has more than 2 columns, as a list of maps is required.
        this.headers = dataTable.asMap();
        System.out.println("With Table Value Passing  ----- "+dataTable.asMap());
        System.out.println("Header 1st key      "+dataTable.asMaps().get(0).get("key"));
        System.out.println("Header 1st value   "+dataTable.asMaps().get(0).get("value"));
        System.out.println("Header 2nd key      "+dataTable.asMaps().get(1).get("key"));
        System.out.println("Header 2nd value   "+dataTable.asMaps().get(1).get("value"));

    }

    @And("the request body is:  for data driven testing")
    public void setRequestBody(String body) {
        this.requestBody = body;
    }

    @When("a POST request for data driven testing")
    public void makePostRequest() {
        String endpoint = "/api/v1/Activities";
        RequestSpecification request = RestAssured.given();
        // Apply headers to this specific request
        headers.forEach(request::header);
       // Apply all headers from the list of maps
//        for (Map<String, String> headerMap : headers) {
//            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
//                request.header(entry.getKey(), entry.getValue());
//                System.out.println(" request.header(entry.getKey(), entry.getValue()) "+ entry.getKey()+entry.getValue());
//            }
//        }
        System.out.println("requestBody is \n"+requestBody+"\n");
        response =RestAssured.given()
                .headers(headers)
                 .body(requestBody)
                .when()
                .post(endpoint);
        System.out.println("response body  is "+response.statusCode()+"\n"+
                "Response as Pretty string\n"+response.asPrettyString()+"\n"+
                "Responsebody \n"+"\n"+response.getBody()+"\n"+"\n");
    }

    @Then("the response status code should be {int} for data driven testing")
    public void verifyStatusCode(int expectedStatusCode) {
        response.then().assertThat().statusCode(expectedStatusCode);
    }

    @And("the response body field {string} should equal {string}  for data driven testing")
    public void verifyResponseBodyField(String field, String expectedValue) {
        response.then().assertThat().body(field, equalTo(expectedValue));
    }
}

