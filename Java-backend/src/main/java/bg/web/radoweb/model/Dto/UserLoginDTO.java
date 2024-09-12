package bg.web.radoweb.model.Dto;

public class UserLoginDTO {
    private String username;
    private String password;
    public UserLoginDTO(){

    }

    public String getUsername() {
        return username;
    }

    public UserLoginDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
