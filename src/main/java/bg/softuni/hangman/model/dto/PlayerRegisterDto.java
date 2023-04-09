package bg.softuni.hangman.model.dto;

import bg.softuni.hangman.validation.PasswordMatch;
import bg.softuni.hangman.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

@PasswordMatch(password = "password", confirmPassword = "confirmPassword")
public class PlayerRegisterDto {
    @NotBlank
    @Email
    @UniqueEmail
    private String email;

    @NotNull
    @Size(min = 6, max = 15)
    private String password;

    @NotNull
    @Size(min = 6, max = 15)
    private String confirmPassword;

    @NotNull
    @Size(min = 4, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 4, max = 20)
    private String lastName;


    public String getEmail() {
        return email;
    }

    public PlayerRegisterDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PlayerRegisterDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public PlayerRegisterDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public PlayerRegisterDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PlayerRegisterDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
