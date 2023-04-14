package bg.softuni.hangman.model.dto;

import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class GamePlayDto {

    @Size(min = 1, max = 1)
    private String letter;

    public String getLetter() {
        return letter;
    }

    public GamePlayDto setLetter(String letter) {
        this.letter = letter;
        return this;
    }
}
