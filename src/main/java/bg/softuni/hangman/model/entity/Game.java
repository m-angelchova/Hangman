package bg.softuni.hangman.model.entity;

import bg.softuni.hangman.model.enums.GameOutcomeEnum;
import jakarta.persistence.*;


@Entity
@Table(name = "games")
public class Game extends BaseEntity {


    @ManyToOne //?
    private Dictionary dictionary;

    @Column
    private Long score;

    @Enumerated(EnumType.STRING)
    private GameOutcomeEnum outcome;





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
