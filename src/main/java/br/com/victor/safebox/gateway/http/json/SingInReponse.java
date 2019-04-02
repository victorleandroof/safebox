package br.com.victor.safebox.gateway.http.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SingInReponse {

    @JsonProperty("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
