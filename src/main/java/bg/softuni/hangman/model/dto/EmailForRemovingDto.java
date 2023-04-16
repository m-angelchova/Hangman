package bg.softuni.hangman.model.dto;

import bg.softuni.hangman.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailForRemovingDto {
    @NotBlank
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public EmailForRemovingDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
