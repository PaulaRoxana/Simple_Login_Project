package ro.itschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Main {//Main-ul trebuie sa anunte ca avem un SpringBootApplication si ca ruleaza totul intr-un Spring env

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}