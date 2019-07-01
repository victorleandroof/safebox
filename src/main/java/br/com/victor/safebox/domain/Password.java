package br.com.victor.safebox.domain;



import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


public class Password implements Serializable {


    private String id;
    @NotNull(message = "Name cannot be null!")
    @Size(min = 3, max = 80, message = "Name Me must be between 3 and 80 characters")
    private String name;
    @NotNull(message = "url cannot be null!!")
    private String url;
    @NotNull(message = "Username cannot be null!")
    @Size(min = 3, max = 80, message = "Username Me must be between 3 and 80 characters")
    private String username;
    @NotNull(message = "Password cannot be null!")
    private String password;
    private String icon;
    private Mono<Client> client;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Mono<Client> getClient() {
        return client;
    }

    public void setClient(Mono<Client> client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Password{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
