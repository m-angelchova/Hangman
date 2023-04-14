package bg.softuni.hangman.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DictionaryDto {
    @NotNull
    private String word;

    @NotNull
    @Size(max = 500)
    private String definition;

    public String getWord() {
        return word;
    }

    public DictionaryDto setWord(String word) {
        this.word = word;
        return this;
    }

    public String getDefinition() {
        return definition;
    }

    public DictionaryDto setDefinition(String definition) {
        this.definition = definition;
        return this;
    }
}
