package bg.softuni.hangman.services;

import bg.softuni.hangman.model.dto.PlayerRegisterDto;
import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.model.entity.PlayerRole;
import bg.softuni.hangman.model.enums.PlayerRoleEnum;
import bg.softuni.hangman.repository.PlayerRepository;
import bg.softuni.hangman.repository.RoleRepository;
import bg.softuni.hangman.service.EmailService;
import bg.softuni.hangman.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    mockRoleRepository.save(new PlayerRole().setRole(PlayerRoleEnum.ADMIN));
    mockRoleRepository.save(new PlayerRole().setRole(PlayerRoleEnum.USER));
  }

  @Test
  void testPlayerRegistration_SaveInvoked() {

    //LEFT for reference

    // ARRANGE
    PlayerRegisterDto testRegistrationDTO = new PlayerRegisterDto().
        setEmail("test@example.com").
        setFirstName("Test").
        setLastName("Testov").
        setPassword("defaultPass");

    //ACT
    mockRoleRepository.save(new PlayerRole().setRole(PlayerRoleEnum.ADMIN));
    mockRoleRepository.save(new PlayerRole().setRole(PlayerRoleEnum.USER));

    toTest.registerPlayer(testRegistrationDTO);

    //ASSERT
    verify(mockPlayerRepository).save(any());
  }

  @Test
  void testUserRegistration_SaveInvoked_Version2() {

    // ARRANGE

    String testPassword = "topsecret";
    String encodedPassword = "encoded_password";
    String email = "test@example.com";
    String firstName = "Test";
    String lastName = "Testov";

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
}
