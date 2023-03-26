package ro.itschool.entity;

import jakarta.persistence.*;
import jakarta.servlet.Filter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.config.annotation.SecurityBuilder;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyUser implements SecurityBuilder<Filter> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Column(unique = true)
    private String username;

    private String role;

    public MyUser(String password, String username, String role) {
        this.password = password;
        this.username = username;
        this.role = role;
    }

    @Override
    public Filter build() throws Exception {
        return null;
    }
}
