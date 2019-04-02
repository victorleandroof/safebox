package br.com.victor.safebox.gateway.http.json;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class CreateUserResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("username")
    private String username;
    @JsonProperty("public_key")
    private String publicKey;
    @JsonProperty("cellphone")
    private String cellphone;
    @JsonProperty("birthdate")
    private LocalDate birthdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}
