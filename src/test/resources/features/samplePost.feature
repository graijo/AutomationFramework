Feature: Sample POST API Testing

  Scenario: Verify POST request with valid data
    Given the base URI is "https://fakerestapi.azurewebsites.net"
    And the headers are:
      | key            | value            |
      | accept         | text/plain       |
      | Content-Type   | application/json |
    And the request body is:
      """
      {
        "id": 0,
        "title": "welcome -123",
        "dueDate": "2025-01-03T11:40:49.097Z",
        "completed": true
      }
      """
    When a POST request is made to "/api/v1/Activities"
    Then the response status code should be 200
    And the response body field "title" should equal "welcome -123"
