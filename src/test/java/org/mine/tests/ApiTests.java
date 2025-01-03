package org.mine.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.mine.models.api.builder.author.AuthorRequest;
import org.mine.models.api.builder.author.AuthorResponse;
import org.mine.models.api.builder.author.AuthorResponse_Builder_Jackson;
import org.mine.models.api.request.ActivitiesRequest;
import org.mine.models.api.request.RegisterRequest;
import org.mine.models.api.response.ActivitiesResponse;
import org.mine.models.api.response.RegisterResponse;
import org.mine.utils.APIUtils;
import org.mine.utils.JsonUtils;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ApiTests extends BaseAPITest{

    @Test
    public void getRequest(){
        Response response = given()
                    .header("Content-Type", "application/json")
                    .header("Accept","application/json")
                .when()
                    .get("/api/users?page=2")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("data[1].email",equalTo("lindsay.ferguson@reqres.in"))
                    .body("total_pages",equalTo(2))
                .extract().response();

        logger.info("response.getBody().asString()-- "+response.getBody().asString());
        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.getBody().asString());
        assertEquals(response.getBody().jsonPath().getString("data[1].email"),"lindsay.ferguson@reqres.in");
        assertEquals(response.getBody().jsonPath().getString("support.url"),"https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral");
    }

    @Test
    public void postRequest(){
        // Create Register request
        RegisterRequest registerRequest=new RegisterRequest("eve.holt@reqres.in","pistol");

        // Send request and get response
        Response registerResponse =given()
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .body(registerRequest)
                .when()
                .post("/api/register")
                .then()
                .assertThat()
                .statusCode(400)
                .extract().response();


        logger.info("registerResponse is "+registerResponse);
        logger.info("registerResponse statusCode is "+registerResponse.statusCode());
        //Only .asString() can give string value of Response value.
        logger.info("registerResponse getBody is "+registerResponse.getBody().asString());
        logger.info("registerResponse getBody is "+registerResponse.getBody().toString());
        logger.info("registerResponse getBody is "+registerRequest.toString());

    }
    @Test
    public void model_Class_for_requestBuilding_and_response_extraction(){
         RestAssured.baseURI="https://fakerestapi.azurewebsites.net";
        // Create  request
        ActivitiesRequest activitiesRequest=new ActivitiesRequest(4,"title-A","2025-01-03T11:49:10.349Z",true);

        // Send request and get response
        ActivitiesResponse activitiesResponse=given()
                .header("accept"," text/plain")
                .header("Content-Type","application/json")
                .body(activitiesRequest)
                .when()
                .post("/api/v1/Activities")
                .then()
                .assertThat()
                .statusCode(200)
                .body("title",equalTo("title-A"))
                .extract().as(ActivitiesResponse.class);

       assertEquals(4, activitiesResponse.getId(), "ID should match expected value");
       assertEquals("title-A", activitiesResponse.getTitle(), "title should match expected value");
       assertEquals(true, activitiesResponse.isCompleted(), "Is Completed should match expected value");

        logger.info("registerResponse is "+activitiesResponse.toString());

    }
    @Test
    public void sample_post() {
        RestAssured.baseURI="https://fakerestapi.azurewebsites.net";

        Response response=given()
                .header("accept"," text/plain")
                .header("Content-Type","application/json")
                .body("{\n" +
                        "  \"id\": 0,\n" +
                        "  \"title\": \"string\",\n" +
                        "  \"dueDate\": \"2025-01-03T11:40:49.097Z\",\n" +
                        "  \"completed\": true\n" +
                        "}")
                .when()
                .post("/api/v1/Activities")
                .then()
                .assertThat()
                .statusCode(200)
                .body("title",equalTo("string"))
                .extract().response();
        //        assertNotNull(registerResponse.getToken(), "Token should not be null");
//        assertEquals(4, registerResponse.getId(), "ID should match expected value");
    }
    @Test
    public void Using_APIUtil_Functions(){
        RestAssured.baseURI="https://fakerestapi.azurewebsites.net";
        // Basic request setup
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://fakerestapi.azurewebsites.net")
                .setContentType("application/json")
                .build();
        // Create  request
        ActivitiesRequest activitiesRequest=new ActivitiesRequest(5,"title-A5","2025-01-03T11:49:10.349Z",true);
        // Send request and get response
        ActivitiesResponse activitiesResponse = APIUtils.sendPostRequest(
                requestSpec,activitiesRequest,"/api/v1/Activities",ActivitiesResponse.class);

        assertEquals(5, activitiesResponse.getId(), "ID should match expected value");
        assertEquals("title-A5", activitiesResponse.getTitle(), "title should match expected value");
        assertEquals(true, activitiesResponse.isCompleted(), "Is Completed should match expected value");
        //both will give response body as toString is implimented by the response model class
        logger.info("ActivitiesResponse is "+activitiesResponse.toString());
        logger.info("ActivitiesResponse is- "+activitiesResponse);
    }
    @Test
    public void UsingBuilderClass(){
        RestAssured.baseURI="https://fakerestapi.azurewebsites.net";
        // Create request object using builder
        AuthorRequest authorRequest=new AuthorRequest.AuthorRequestBuilder()
                .id(5)
                .idBook(7)
                .firstName("John")
                .lastName("Dani")
                .build();
        // Make API call and verify response
        AuthorResponse authorResponse = given()
                .contentType(ContentType.JSON)
                .body(authorRequest)
                .when()
                .post("/api/v1/Authors")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthorResponse.class);

        // Verify response using assertions
        assert authorResponse.getId().equals(authorRequest.getId());
        assert authorResponse.getIdBook().equals(authorRequest.getIdBook());
        assert authorResponse.getFirstName().equals(authorRequest.getFirstName());
        assert authorResponse.getLastName().equals(authorRequest.getLastName());



    }
    @Test
    public void UsingBuilderClass_withJackson(){
        RestAssured.baseURI="https://fakerestapi.azurewebsites.net";
        // Create request object using builder
        AuthorRequest authorRequest=new AuthorRequest.AuthorRequestBuilder()
                .id(5)
                .idBook(7)
                .firstName("John")
                .lastName("Dani")
                .build();
        // Make API call and verify response
        AuthorResponse_Builder_Jackson authorResponse = given()
                .contentType(ContentType.JSON)
                .body(authorRequest)
                .when()
                .post("/api/v1/Authors")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthorResponse_Builder_Jackson.class);

        // Verify response using assertions
        assert authorResponse.getId().equals(authorRequest.getId());
        assert authorResponse.getIdBook().equals(authorRequest.getIdBook());
        assert authorResponse.getFirstName().equals(authorRequest.getFirstName());
        assert authorResponse.getLastName().equals(authorRequest.getLastName());



    }
    @Test
    public void verifyPostSendWith_Jsonbody_fromJsonFile(){
        RestAssured.baseURI="https://fakerestapi.azurewebsites.net";
        // get request body from json file
        String authorRequestBody=JsonUtils.readJsonFile("src/test/resources/testdata/requestsJson/create_author.json");
        logger.info("authorRequestBody is "+"\n"+authorRequestBody);
        // Make API call and verify response
        AuthorResponse_Builder_Jackson authorResponse = given()
                .contentType(ContentType.JSON)
                .body(authorRequestBody)
                .when()
                .post("/api/v1/Authors")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthorResponse_Builder_Jackson.class);

        // Verify response using assertions
        assert authorResponse.getId().equals(56);
        assert authorResponse.getIdBook().equals(100);
        assert authorResponse.getFirstName().equals("mufasa");
        assert authorResponse.getLastName().equals("lion king");

    }
    @Test
    public void verifyPostSendWithUpdated_requestBody_OfJsonFile_with_TestData_From_excel(){

    }
}
