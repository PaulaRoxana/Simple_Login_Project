package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.itschool.controller.model.MyUserDTO;
import ro.itschool.entity.MyUser;
import ro.itschool.mapper.MyUserMapper;
import ro.itschool.repository.MyUserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/my-user")
public class MyUserController {

  private final MyUserRepository myUserRepository;

  private final MyUserMapper myUserMapper;

  @PostMapping(value = "/register-simple")
  public void registerUserSimple(@RequestBody MyUser myUser) {
    String encryptedPassword = new BCryptPasswordEncoder().encode(myUser.getPassword());
    if (myUser.getRole() == null) {
      myUser.setRole("user");
    }
    myUser.setPassword(encryptedPassword);
    myUserRepository.save(myUser);
  }

  @GetMapping(value = "/all-users")
  public List<MyUser> getAllUsers1() {
    return myUserRepository.findAll();
  }


  @PostMapping(value = "/register")
  public void registerUser(@RequestBody MyUserDTO myUserDTO) {//register: introduce date din FE in BE (POST method)
    String encryptedPassword = new BCryptPasswordEncoder().encode(myUserDTO.getPassword());
    if (myUserDTO.getRole() == null) {
      myUserDTO.setRole("user");
    }
    myUserDTO.setPassword(encryptedPassword);
    myUserRepository.save(myUserMapper.toEntity(myUserDTO));
  }

  @GetMapping(value = "/all")
  public List<String> getAllUsers() {
    return myUserRepository.findAll()
      .stream()
      .map(myUserMapper::toMyUserDTO)
      .map(MyUserDTO::getUsername)
      .toList();
  }

  //ht
  @PostMapping("/users")
  public ResponseEntity<?> saveUserBySuperAdmin(@RequestBody MyUserDTO myUserDTO, @AuthenticationPrincipal MyUser currentUser) {
    Optional<MyUser> dbUser = myUserRepository.findByUsername(myUserDTO.getUsername());
    Optional<MyUser> dbCurrentUser = myUserRepository.findByUsername(currentUser.getUsername());

      if (dbCurrentUser.isPresent() &&
        dbCurrentUser.get().getRole().equals("super_admin") &&
        myUserDTO.getRole().equals("user") &&
        dbUser.isEmpty()) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(myUserDTO.getPassword());
        myUserDTO.setPassword(encryptedPassword);
        return new ResponseEntity<>(
          myUserRepository.save(myUserMapper.toEntity(myUserDTO)), HttpStatus.OK);
      } else
        return new ResponseEntity<>("You are not allowed perform this operation", HttpStatus.FORBIDDEN);


  }

}
