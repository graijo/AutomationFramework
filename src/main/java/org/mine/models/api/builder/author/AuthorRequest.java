package org.mine.models.api.builder.author;

public class AuthorRequest {
    private final Integer id;
    private final Integer idBook;
    private final String firstName;
    private final String lastName;

    // Private constructor to enforce builder usage
    private AuthorRequest(AuthorRequestBuilder builder) {
        this.id = builder.id;
        this.idBook = builder.idBook;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    // Getters
    public Integer getId() { return id; }
    public Integer getIdBook() { return idBook; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    // Static Builder Class
    public static class AuthorRequestBuilder {
        private Integer id;
        private Integer idBook;
        private String firstName;
        private String lastName;

        public AuthorRequestBuilder() {}

        public AuthorRequestBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public AuthorRequestBuilder idBook(Integer idBook) {
            this.idBook = idBook;
            return this;
        }

        public AuthorRequestBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AuthorRequestBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AuthorRequest build() {
            return new AuthorRequest(this);
        }
    }
}