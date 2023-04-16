package bg.softuni.hangman.model.dto;


public class GameProfileDto {

    private String dictionary;
    private Long score;
    private String outcome;

    public String getDictionary() {
        return dictionary;
    }

    public GameProfileDto setDictionary(String dictionary) {
        this.dictionary = dictionary;
        return this;
    }

    public Long getScore() {
        return score;
    }

    public GameProfileDto setScore(Long score) {
        this.score = score;
        return this;
    }

    public String getOutcome() {
        return outcome;
    }

    public GameProfileDto setOutcome(String outcome) {
        this.outcome = outcome;
        return this;
    }
}
