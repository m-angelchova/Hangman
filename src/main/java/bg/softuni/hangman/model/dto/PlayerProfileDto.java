package bg.softuni.hangman.model.dto;

import bg.softuni.hangman.model.entity.Game;

import java.util.List;

public class PlayerProfileDto {
    private String fullName;
    private Long score;
    private List<GameProfileDto> gamesPlayed;

    public String getFullName() {
        return fullName;
    }

    public PlayerProfileDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Long getScore() {
        return score;
    }

    public PlayerProfileDto setScore(Long score) {
        this.score = score;
        return this;
    }

    public List<GameProfileDto> getGamesPlayed() {
        return gamesPlayed;
    }

    public PlayerProfileDto setGamesPlayed(List<GameProfileDto> gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
        return this;
    }
}
