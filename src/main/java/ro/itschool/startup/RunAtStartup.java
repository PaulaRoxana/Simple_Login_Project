package ro.itschool.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ro.itschool.entity.MyUser;
import ro.itschool.repository.MyUserRepository;

@Component//adaugam @Component pt ca nu e recunoscut ca si Bean cand porneste aplicatia
@RequiredArgsConstructor
public class RunAtStartup {

    private final MyUserRepository myUserRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        String encryptedPassword = new BCryptPasswordEncoder().encode("password1");
        MyUser myUser1 = new MyUser(encryptedPassword, "user1", "user");
        myUserRepository.save(myUser1);

        encryptedPassword = new BCryptPasswordEncoder().encode("password2");
        MyUser myUser2 = new MyUser(encryptedPassword, "user2", "admin");
        myUserRepository.save(myUser2);
//ht
        encryptedPassword = new BCryptPasswordEncoder().encode("passwordSA1");
        MyUser myUserSA1 = new MyUser(encryptedPassword, "user3", "super_admin");
        myUserRepository.save(myUserSA1);
    }
}
