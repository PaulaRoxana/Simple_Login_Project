package ro.itschool.mapper;

import org.springframework.stereotype.Component;
import ro.itschool.controller.model.MyUserDTO;
import ro.itschool.entity.MyUser;

@Component
public class MyUserMapper {

    public MyUser toEntity(MyUserDTO userDTO) {
        return new MyUser(
                userDTO.getId(),
                userDTO.getPassword(),
                userDTO.getUsername(),
                userDTO.getRole()
        );
    }

    public MyUserDTO toMyUserDTO(MyUser myUser) {
        return new MyUserDTO(
                myUser.getUsername()
        );
    }
}
