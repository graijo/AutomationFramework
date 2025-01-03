package org.mine.models.api.request;

import groovy.transform.builder.Builder;

public class RegisterRequest {
    private String email;
    private String password;

    // Default constructor
    public RegisterRequest() {}

    // Constructor with parameters
    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

     public void setEmail(String email) {
        this.email = email;
    }
    // toString method for debugging
    @Override
    public String toString() {
        return "RegisterRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
