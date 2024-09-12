package bg.web.radoweb.model.Dto;


import bg.web.radoweb.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {
    @NotBlank(message = "First Name cannot be empty")
    @Size(min = 2, max = 10, message = "First Name must be between 2 and 10 characters long")
    private String firstName;
    @NotBlank(message = "Second Name cannot be empty")
    @Size(min = 2, max = 10, message = "Second Name must be between 2 and 10 characters long")
    private String secondName;
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 4, max = 10, message = "Second Name must be between 4 and 10 characters long")
    private String username;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 4, max = 50, message = "Password must be between 4 and 50 characters long")
    private String password;
    @NotBlank(message = "Confirm Password cannot be empty")
    @Size(min = 4, max = 50, message = "Confirm Password must be between 4 and 50 characters long")
    private String confirmPassword;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;
    @Min(value = 18, message = "Age must be at least 18")
    private int age;
    private Role role;
    public UserRegisterDTO(){

    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getSecondName() {
        return secondName;
    }

    public UserRegisterDTO setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public int getAge() {
        return age;
    }

    public UserRegisterDTO setAge(int age) {
        this.age = age;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserRegisterDTO setRole(Role role) {
        this.role = role;
        return this;
    }
}
