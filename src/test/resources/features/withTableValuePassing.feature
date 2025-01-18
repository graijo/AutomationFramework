Feature: Sample POST API Testing

  Scenario: Verify POST request with valid data With Table Value Passing Steps
    Given 1the base URI is "https://fakerestapi.azurewebsites.net"
    And 1the headers are:
      | key            | value            |
      | accept         | text/plain       |
      | Content-Type   | application/json |
    And 1the request body is:
      """
      {
        "id": 0,
        "title": "welcome -123",
        "dueDate": "2025-01-03T11:40:49.097Z",
        "completed": true
      }
      """
    When 1a POST request is made to "/api/v1/Activities"
    Then 1the response status code should be 200
    And 1the response body field "title" should equal "welcome -123"
