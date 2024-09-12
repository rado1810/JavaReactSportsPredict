package bg.web.radoweb.model.Dto;

import bg.web.radoweb.model.Role;

import java.util.Set;

public class UserResponseDTO {
    private String username;
    private Set<String> roles;
    private String token;
    public UserResponseDTO(){

    }

    public String getUsername() {
        return username;
    }

    public UserResponseDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public UserResponseDTO setRoles(Set<String> roles) {
        this.roles = roles;
        return this;
    }

    public String getToken() {
        return token;
    }

    public UserResponseDTO setToken(String token) {
        this.token = token;
        return this;
    }
}
