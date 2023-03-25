package bg.softuni.hangman.model.dto;

public class DictionaryDto {
    private String word;
    private String description;

    public String getWord() {
        return word;
    }

    public DictionaryDto setWord(String word) {
        this.word = word;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DictionaryDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
