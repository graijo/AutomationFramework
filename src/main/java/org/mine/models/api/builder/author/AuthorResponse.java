package org.mine.models.api.builder.author;

public class AuthorResponse {
    // Fields are private but not final to allow Jackson deserialization
    private  Integer id;
    private  Integer idBook;
    private  String firstName;
    private  String lastName;
    private String createdAt;
    // Default constructor required for Jackson
    public AuthorResponse() {}

    // Private constructor to enforce builder usage
    private AuthorResponse(AuthorResponseBuilder builder) {
        this.id = builder.id;
        this.idBook = builder.idBook;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.createdAt=builder.createdAt;
    }

    // Getters
    public Integer getId() { return id; }
    public Integer getIdBook() { return idBook; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    //Setters
    public void setId(Integer id) { this.id = id; }
    public void setIdBook(Integer idBook) { this.idBook = idBook; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }


    // Static Builder Class
    public static class AuthorResponseBuilder {
        private Integer id;
        private Integer idBook;
        private String firstName;
        private String lastName;
        private String createdAt;

        public AuthorResponseBuilder() {}

        public AuthorResponseBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public AuthorResponseBuilder idBook(Integer idBook) {
            this.idBook = idBook;
            return this;
        }

        public AuthorResponseBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AuthorResponseBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public AuthorResponseBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AuthorResponse build() {
            return new AuthorResponse(this);
        }
    }
}