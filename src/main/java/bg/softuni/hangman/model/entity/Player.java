package bg.softuni.hangman.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.data.repository.cdi.Eager;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {
// User entity

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "players_roles",
            joinColumns = { @JoinColumn(name = "player_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private List<PlayerRole> roles;

    @Column
    private Long score;

//    @Column(name = "games_played")
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "players_games",
            joinColumns = { @JoinColumn(name = "player_id") },
            inverseJoinColumns = { @JoinColumn(name = "game_id") }
    )
    private List<Game> gamesPlayed;



    public Player() {
        gamesPlayed = new LinkedList<>(); //to keep the games chronologically
        score = 0L;
    }

    //Getters and Setters:

    public String getEmail() {
        return email;
    }

    public Player setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Player setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Player setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Player setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<PlayerRole> getRoles() {
        return roles;
    }

    public Player setRoles(List<PlayerRole> roles) {
        this.roles = roles;
        return this;
    }

    public Long getScore() {
        return score;
    }

    public Player setScore(Long score) {
        this.score = score;
        return this;
    }

    public List<Game> getGamesPlayed() {
        return gamesPlayed;
    }

    public Player setGamesPlayed(List<Game> gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
        return this;
    }

}
