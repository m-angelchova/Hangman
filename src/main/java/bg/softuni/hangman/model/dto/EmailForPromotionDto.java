package bg.softuni.hangman.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailForPromotionDto {
    @NotBlank
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public EmailForPromotionDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
