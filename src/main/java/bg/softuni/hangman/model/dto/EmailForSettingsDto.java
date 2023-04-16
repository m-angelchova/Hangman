package bg.softuni.hangman.model.dto;

import bg.softuni.hangman.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailForSettingsDto {
    @NotBlank
    @Email
    @UniqueEmail
    private String email;

    public String getEmail() {
        return email;
    }

    public EmailForSettingsDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
