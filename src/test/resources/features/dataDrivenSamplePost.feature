Feature: Send multiple POST API requests
as a UserRole
  I want to complete data driven testing
  So that to demo bdd data driven
  Scenario Outline: : Verify multiple POST API requests with valid data
    Given the base URI is  for data driven testing
    And the headers are:  for data driven testing
      | key            | value            |
      | accept         | text/plain       |
      | Content-Type   | application/json |
    And the request body is:  for data driven testing
      """
      {
        "id": <id>,
        "title": "<title>",
        "dueDate": "<dueDate>",
        "completed": <completed>
      }
      """
    When a POST request for data driven testing
    Then the response status code should be <Status> for data driven testing
#    And the response body field "title" should equal "<title>"  for data driven testing
    Examples:
      | id | title | dueDate | completed | Status |
      | 1 | Welcome | 2025-01-19T11:40:49.097Z | true | 200 |
      | 101 | Welcome 123 | 2025-01-19 | false | 200 |
      | qq | 123 | qwe | 1234 | 400 |
      | .. | .. | .. | .. | 400 |
      |   |   |   |   | 400 |
