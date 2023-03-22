package bg.softuni.hangman.service;

import bg.softuni.hangman.model.dto.PlayerRegisterDto;
import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.repository.PlayerRepository;
import bg.softuni.hangman.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultAdminPass;


    public PlayerService(PlayerRepository playerRepository,
                         RoleRepository roleRepository,
                         PasswordEncoder passwordEncoder,
                         @Value("${hangman.admin.defaultpass}") String defaultAdminPass) {
        this.playerRepository = playerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultAdminPass = defaultAdminPass;
    }



    public void registerUser(PlayerRegisterDto registrationDTO) {
        Player player = new Player().
                setFirstName(registrationDTO.getFirstName()).
                setLastName(registrationDTO.getLastName()).
                setEmail(registrationDTO.getEmail()).
                setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        //roles?

        playerRepository.save(player);
    }
}
