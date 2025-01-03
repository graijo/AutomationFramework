package org.mine.models.api.response;

public class RegisterResponse {
    private int id;
    private String token;

    public RegisterResponse() {
    }

    public RegisterResponse(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "id=" + id +
                ", token='" + token + '\'' +
                '}';
    }
}
