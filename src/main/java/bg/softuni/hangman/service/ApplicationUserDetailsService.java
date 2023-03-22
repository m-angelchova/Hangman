package bg.softuni.hangman.service;

import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.model.entity.PlayerRole;
import bg.softuni.hangman.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class ApplicationUserDetailsService implements UserDetailsService {

  private final PlayerRepository playerRepository;

  public ApplicationUserDetailsService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return
        playerRepository.
            findByEmail(email).
            map(this::map).
            orElseThrow(() -> new UsernameNotFoundException("Player with email " + email + " not found!"));
  }

  private UserDetails map(Player player) {
    return new User(
        player.getEmail(),
        player.getPassword(),
        extractAuthorities(player)
    );
  }

  private List<GrantedAuthority> extractAuthorities(Player player) {
    return player.getRoles().stream().map(this::mapRole).toList();
  }

  private GrantedAuthority mapRole(PlayerRole role) {
    return new SimpleGrantedAuthority("ROLE_" + role.getRole().name());
  }
}
