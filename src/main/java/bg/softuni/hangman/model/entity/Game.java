package bg.softuni.hangman.model.entity;

import bg.softuni.hangman.model.constant.GameOutcomeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


@Entity
@Table(name = "games")
public class Game extends BaseEntity {


    @ManyToOne //?
    private Player player;


    @ManyToOne //?
    private Dictionary dictionary;

    @Column
//    @Min(0)
//    @Max(200)
    private Long score;

    @Enumerated(EnumType.STRING)
    private GameOutcomeEnum outcome;

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

    public GameOutcomeEnum getOutcome() {
        return outcome;
    }

    public Game setOutcome(GameOutcomeEnum outcome) {
        this.outcome = outcome;
        return this;
    }
}
