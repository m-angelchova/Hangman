package bg.softuni.hangman.model.dto;

public class ScoreboardPlayersDto {
    private String fullName;
    private Long score;
    private Long gamesPlayed;

    public String getFullName() {
        return fullName;
    }

    public ScoreboardPlayersDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Long getScore() {
        return score;
    }

    public ScoreboardPlayersDto setScore(Long score) {
        this.score = score;
        return this;
    }

    public Long getGamesPlayed() {
        return gamesPlayed;
    }

    public ScoreboardPlayersDto setGamesPlayed(Long gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
        return this;
    }
}
