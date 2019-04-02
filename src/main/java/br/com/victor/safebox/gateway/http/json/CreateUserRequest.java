package br.com.victor.safebox.gateway.http.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class CreateUserRequest {

    @JsonProperty("name")
    private String name;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("cellphone")
    private String cellphone;
    @JsonProperty("birthdate")
    private LocalDate birthdate;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
