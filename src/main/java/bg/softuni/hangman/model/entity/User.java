package bg.softuni.hangman.model.entity;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {


    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @ManyToOne
    private Role role;

    @Column
    private Long score;

    @Column(name = "games_played")
    @OneToMany(mappedBy = "id") //??
    private List<Game> gamesPlayed;

    //Constructor:

    public User() {
        gamesPlayed = new LinkedList<>(); //to keep the games chronologically
        score = 0L;
    }

    //Getters and Setters:

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public Long getScore() {
        return score;
    }

    public User setScore(Long score) {
        this.score = score;
        return this;
    }

    public List<Game> getGamesPlayed() {
        return gamesPlayed;
    }

    public User setGamesPlayed(List<Game> gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
        return this;
    }
}
