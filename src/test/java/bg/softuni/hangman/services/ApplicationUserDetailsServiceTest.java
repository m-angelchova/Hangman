package bg.softuni.hangman.services;

import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.model.entity.PlayerRole;
import bg.softuni.hangman.model.enums.PlayerRoleEnum;
import bg.softuni.hangman.repository.PlayerRepository;
import bg.softuni.hangman.service.ApplicationUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationUserDetailsServiceTest {

  private final String NOT_EXISTING_EMAIL = "pesho@example.com";

  private ApplicationUserDetailsService toTest;

  @Mock
  private PlayerRepository mockPlayerRepository;

  @BeforeEach
  void setUp() {
    toTest = new ApplicationUserDetailsService(
            mockPlayerRepository
    );
  }

  @Test
  void testUserFound() {

    // ARRANGE
    PlayerRole testAdminRole = new PlayerRole().setRole(PlayerRoleEnum.ADMIN);
    PlayerRole testUserRole = new PlayerRole().setRole(PlayerRoleEnum.USER);

    String EXISTING_EMAIL = "admin@example.com";
    Player testPlayer = new Player().
        setEmail(EXISTING_EMAIL).
        setPassword("defaultPass").
        setRoles(List.of(testAdminRole, testUserRole));


    when(mockPlayerRepository.findByEmail(EXISTING_EMAIL)).
        thenReturn(Optional.of(testPlayer));
    // EO: ARRANGE


    // ACT
    UserDetails adminDetails =
        toTest.loadUserByUsername(EXISTING_EMAIL);
    // EO: ACT

    // ASSERT
    Assertions.assertNotNull(adminDetails);
    Assertions.assertEquals(EXISTING_EMAIL, adminDetails.getUsername());
    Assertions.assertEquals(testPlayer.getPassword(), adminDetails.getPassword());

    Assertions.assertEquals(2,
        adminDetails.getAuthorities().size(),
        "The authorities are supposed to be just two - ADMIN/USER.");

    assertRole(adminDetails.getAuthorities(), "ROLE_ADMIN");
    assertRole(adminDetails.getAuthorities(), "ROLE_USER");
    // EO: ASSERT
  }

  private void assertRole(Collection<? extends GrantedAuthority> authorities,
    String role) {
    authorities.
        stream().
        filter(a -> role.equals(a.getAuthority())).
        findAny().
        orElseThrow(() -> new AssertionFailedError("Role " + role + " not found!"));
  }


  @Test
  void testUserNotFound() {
    assertThrows(
        UsernameNotFoundException.class,
        () -> toTest.loadUserByUsername(NOT_EXISTING_EMAIL)
    );
  }
}
