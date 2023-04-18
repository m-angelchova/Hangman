package bg.softuni.hangman.services;

import bg.softuni.hangman.model.dto.PlayerRegisterDto;
import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.model.entity.PlayerRole;
import bg.softuni.hangman.model.enums.PlayerRoleEnum;
import bg.softuni.hangman.repository.PlayerRepository;
import bg.softuni.hangman.repository.RoleRepository;
import bg.softuni.hangman.service.EmailService;
import bg.softuni.hangman.service.PlayerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

  @Mock
  private PasswordEncoder mockPasswordEncoder;

  @Mock
  private PlayerRepository mockPlayerRepository;
  @Mock
  private RoleRepository mockRoleRepository;

  @Mock
  private EmailService mockEmailService;



  @Captor
  private ArgumentCaptor<Player> playerArgumentCaptor;

  private PlayerService toTest;

  @BeforeEach
  void setUp() {
    toTest = new PlayerService(
            mockPlayerRepository,
            mockRoleRepository,
            mockPasswordEncoder,
        mockEmailService);
  }


  @Test
  void testUserRegistration_SaveInvoked() {

    // ARRANGE
    String testPassword = "topsecret";
    String encodedPassword = "encoded_password";
    String email = "test@example.com";
    String firstName = "Test";
    String lastName = "Testov";


    PlayerRole testUserRole = new PlayerRole().setRole(PlayerRoleEnum.USER);
    when(mockRoleRepository.findByRole(PlayerRoleEnum.USER)).
            thenReturn(Optional.of(testUserRole));

    PlayerRegisterDto testRegistrationDTO = new PlayerRegisterDto().
        setEmail("test@example.com").
        setFirstName(firstName).
        setLastName(lastName).
        setPassword(testPassword);

    when(mockPasswordEncoder.encode(testRegistrationDTO.getPassword())).
        thenReturn(encodedPassword);

    //ACT

    toTest.registerPlayer(testRegistrationDTO);

    //ASSERT
    verify(mockPlayerRepository).save(playerArgumentCaptor.capture());

    Player actualSavedUser = playerArgumentCaptor.getValue();
    assertEquals(testRegistrationDTO.getEmail(), actualSavedUser.getEmail());
    assertEquals(encodedPassword, actualSavedUser.getPassword());

    verify(mockEmailService).
        sendRegistrationEmail(email, firstName + " " + lastName);

  }


  @Test
  void testPromoteToAdmin() {
    String email = "test@example.com";
    String firstName = "Test";
    String lastName = "Testov";
    String testPassword = "topsecret";

    PlayerRole testUserRole = new PlayerRole().setRole(PlayerRoleEnum.USER);
    PlayerRole testAdminRole = new PlayerRole().setRole(PlayerRoleEnum.ADMIN);

    List<PlayerRole> roles = new ArrayList<>();
    roles.add(testUserRole);

    when(mockRoleRepository.findByRole(PlayerRoleEnum.ADMIN)).
            thenReturn(Optional.of(testAdminRole));


    Player player = new Player().
            setEmail(email).
            setFirstName(firstName).
            setLastName(lastName).
            setPassword(testPassword)
            .setRoles(roles);

    when(mockPlayerRepository.findByEmail(email)).thenReturn(Optional.of(player));


    toTest.promoteToAdmin(email);

    //ASSERT
    verify(mockPlayerRepository).save(playerArgumentCaptor.capture());
    Player actualSavedUser = playerArgumentCaptor.getValue();

    assertEquals(2, actualSavedUser.getRoles().size());
    assertTrue(actualSavedUser.getRoles().contains(testAdminRole));
  }

  @Test
  void testRemoveAdmin() {
    String email = "test@example.com";
    String firstName = "Test";
    String lastName = "Testov";
    String testPassword = "topsecret";

    PlayerRole testUserRole = new PlayerRole().setRole(PlayerRoleEnum.USER);
    PlayerRole testAdminRole = new PlayerRole().setRole(PlayerRoleEnum.ADMIN);

    List<PlayerRole> roles = new ArrayList<>();
    roles.add(testUserRole);
    roles.add(testAdminRole);

    when(mockRoleRepository.findByRole(PlayerRoleEnum.ADMIN)).
            thenReturn(Optional.of(testAdminRole));



    Player player = new Player().
            setEmail(email).
            setFirstName(firstName).
            setLastName(lastName).
            setPassword(testPassword).
            setRoles(roles);

    when(mockPlayerRepository.findByEmail(email)).thenReturn(Optional.of(player));


    toTest.removeAdmin(email);

    //ASSERT
    verify(mockPlayerRepository).save(playerArgumentCaptor.capture());
    Player actualSavedUser = playerArgumentCaptor.getValue();

    assertEquals(1, actualSavedUser.getRoles().size());
    assertFalse(actualSavedUser.getRoles().contains(testAdminRole));
  }
}
