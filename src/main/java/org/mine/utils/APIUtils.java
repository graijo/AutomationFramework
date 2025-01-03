package org.mine.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class APIUtils {
    // Configure Gson for handling LocalDateTime
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                // This method handles converting LocalDateTime to JSON string
                @Override
                public void write(JsonWriter out, LocalDateTime value) throws IOException {
                    out.value(value.toString()); // Converts DateTime to string format
                }// Example: LocalDateTime(2024-01-03T10:15:30) → "2024-01-03T10:15:30"

                // This method handles converting JSON string back to LocalDateTime
                @Override
                public LocalDateTime read(JsonReader in) throws IOException {
                    return LocalDateTime.parse(in.nextString());// Converts string back to DateTime
                }// Example: "2024-01-03T10:15:30" → LocalDateTime(2024-01-03T10:15:30)
            })
            .create();//Build the final Gson instance

    /**
     * Sends a POST request with a model object and returns the response as a specific model type
     */
    public static <T, R> R sendPostRequest(
            RequestSpecification requestSpec,
            T requestBody,
            String endpoint,
            Class<R> responseType) {
        try {
            Response response = given()
                    .spec(requestSpec)
                    .body(gson.toJson(requestBody)) // Convert request object to JSON
                    .when()
                    .post(endpoint)
                    .then()
                    .statusCode(200)
                    .extract().response();

            // Convert JSON response to response object
            return gson.fromJson(response.getBody().asString(), responseType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process API request/response", e);
        }
    }

    /**
     * Sends a GET request and returns the response as a specific model type
     */
    public static <T> T sendGetRequest(
            RequestSpecification requestSpec,
            String endpoint,
            Class<T> responseType) {
        try {
            Response response = given()
                    .spec(requestSpec)
                    .when()
                    .get(endpoint)
                    .then()
                    .statusCode(200)
                    .extract().response();

            // Convert JSON response to response object
            return gson.fromJson(response.getBody().asString(), responseType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process API request/response", e);
        }
    }

    /**
     * Validates response against expected model object
     */
    public static void validateResponse(Object actual, Object expected) {
        if (!actual.equals(expected)) {
            throw new AssertionError(String.format(
                    "Response validation failed.\nExpected: %s\nActual: %s",
                    expected.toString(),
                    actual.toString()
            ));
        }
    }
}