package org.mine.models.api.builder.author;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorResponse_Builder_Jackson {
    // Fields are final to maintain immutability
    private final Integer id;
    private final Integer idBook;
    private final String firstName;
    private final String lastName;
    private final String createdAt;

    // Private constructor to enforce builder usage
    //Private Constructor: The constructor that takes an AuthorResponseBuilder is private, ensuring that instances can only be created through the builder.
    private AuthorResponse_Builder_Jackson(AuthorResponseBuilder builder) {
        this.id = builder.id;
        this.idBook = builder.idBook;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.createdAt = builder.createdAt;
    }

    // Public constructor for Jackson
    //Public Constructor: The constructor annotated with @JsonCreator is public, allowing Jackson to instantiate objects directly from JSON data.
    @JsonCreator
    public AuthorResponse_Builder_Jackson(
            @JsonProperty("id") Integer id,
            @JsonProperty("idBook") Integer idBook,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("createdAt") String createdAt) {
        this.id = id;
        this.idBook = idBook;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
    }

    // Getters
    public Integer getId() { return id; }
    public Integer getIdBook() { return idBook; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCreatedAt() { return createdAt; }
    //Setters.
    //setters are typically not included for a couple of important reasons, especially considering
    // the design principles you are following with the builder pattern and immutability.
//    public void setId(Integer id) { this.id = id; }
//    public void setIdBook(Integer idBook) { this.idBook = idBook; }
//    public void setFirstName(String firstName) { this.firstName = firstName; }
//    public void setLastName(String lastName) { this.lastName = lastName; }
//    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }


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

        public AuthorResponse_Builder_Jackson build() {
            return new AuthorResponse_Builder_Jackson(this);
        }
    }
}