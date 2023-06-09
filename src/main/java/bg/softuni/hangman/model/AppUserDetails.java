package bg.softuni.hangman.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AppUserDetails extends User {
    private String modifiableUsername;

    private String firstName;
    private Long score;

    public AppUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.modifiableUsername = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public AppUserDetails setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Long getScore() {
        return score;
    }

    public AppUserDetails setScore(Long score) {
        this.score = score;
        return this;
    }

    @Override
    public String getUsername() {
        return this.modifiableUsername;
    }

    public void setUsername(final String username) {
        this.modifiableUsername = username;
    }

}
