package bg.softuni.hangman.model.entity;

import bg.softuni.hangman.model.constant.PlayerRoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class PlayerRole extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private PlayerRoleEnum role;


    //Getters and Setters:

    public PlayerRoleEnum getRole() {
        return role;
    }

    public PlayerRole setRole(PlayerRoleEnum role) {
        this.role = role;
        return this;
    }
}
