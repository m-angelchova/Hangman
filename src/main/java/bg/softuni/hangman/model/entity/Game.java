package bg.softuni.hangman.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


@Entity
@Table(name = "games")
public class Game extends BaseEntity {


    @OneToOne //?
    private Player player;


    @OneToOne //?
    private Dictionary dictionary;

    @Column
    @Min(0)
    @Max(200)
    private Long score;

    @Column
    private Boolean outcome; // true for win, false for loss

    public Player getPlayer() {
        return player;
    }

    public Game setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public Game setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        return this;
    }

    public Long getScore() {
        return score;
    }

    public Game setScore(Long score) {
        this.score = score;
        return this;
    }

    public Boolean getOutcome() {
        return outcome;
    }

    public Game setOutcome(Boolean outcome) {
        this.outcome = outcome;
        return this;
    }
}
