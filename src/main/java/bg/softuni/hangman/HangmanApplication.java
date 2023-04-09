package bg.softuni.hangman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HangmanApplication {

    public static void main(String[] args) {
        SpringApplication.run(HangmanApplication.class, args);
    }

}
