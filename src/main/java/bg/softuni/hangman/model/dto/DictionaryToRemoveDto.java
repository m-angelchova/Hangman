package bg.softuni.hangman.model.dto;

import jakarta.validation.constraints.NotNull;

public class DictionaryToRemoveDto {
    @NotNull
    private String word;

    public String getWord() {
        return word;
    }

    public DictionaryToRemoveDto setWord(String word) {
        this.word = word;
        return this;
    }
}
