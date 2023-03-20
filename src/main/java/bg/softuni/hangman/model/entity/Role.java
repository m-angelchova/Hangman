package bg.softuni.hangman.model.entity;

import bg.softuni.hangman.model.constant.UserRoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public Role() {
    }

    //Getters and Setters:

    public UserRoleEnum getRole() {
        return role;
    }

    public Role setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
