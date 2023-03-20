package bg.softuni.hangman.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "dictionary")
public class Dictionary extends BaseEntity {

    @Column(nullable = false)
    private String word;

    @Column(columnDefinition = "TEXT")
    private String definition;

    //Getters and Setters:

    public String getWord() {
        return word;
    }

    public Dictionary setWord(String word) {
        this.word = word;
        return this;
    }

    public String getDefinition() {
        return definition;
    }

    public Dictionary setDefinition(String definition) {
        this.definition = definition;
        return this;
    }
}
