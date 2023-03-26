package ro.itschool.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyUserDTO {

    private Long id;

    private String password;

    private String username;

    private String role;


    public MyUserDTO(String username) {
        this.username = username;

    }
}
