package bg.softuni.hangman.model.entity;

import bg.softuni.hangman.model.constant.UserRoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class PlayerRole extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;


    //Getters and Setters:

    public UserRoleEnum getRole() {
        return role;
    }

    public PlayerRole setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
