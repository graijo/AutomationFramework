package org.mine.api.responses;

import io.restassured.response.Response;

public class ResponseHandler {
    public static <T> T extractResponse(Response response, Class<T> responseClass) {
        return response.as(responseClass);
    }

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }
}
