package bg.softuni.hangman.seeder;

import bg.softuni.hangman.service.DatabaseInnitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInnitSeeder implements CommandLineRunner {

    //Initiating the Dictionary class + some test users

    private final DatabaseInnitService innitService;

    public DatabaseInnitSeeder(DatabaseInnitService innitService) {

        this.innitService = innitService;
    }

    @Override
    public void run(String... args) throws Exception {
        innitService.fillDatabase();
    }
}
