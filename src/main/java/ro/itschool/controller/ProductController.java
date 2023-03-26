package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.function.EntityResponse;
import ro.itschool.controller.model.MyUserDTO;
import ro.itschool.controller.model.ProductMyUserDTO;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.Product;
import ro.itschool.mapper.ProductMapper;
import ro.itschool.mapper.ProductMyUserMapper;
import ro.itschool.repository.MyUserRepository;
import ro.itschool.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {
  //@Autowired
  private final ProductRepository productRepository;
  //@Autowired
  private final MyUserRepository myUserRepository;

  private final ProductMyUserMapper productMyUserMapper;

    private final ProductMapper productMapper;


  @GetMapping(value = "/all-products")
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @PostMapping(value = "/all-simple")
  public ResponseEntity<?> getAllProductsPerUser(@RequestBody MyUser myUser) {
    Optional<MyUser> dbUser = myUserRepository.findByUsername(myUser.getUsername());
    return
//      (dbUser.isPresent()) ?
//        ((dbUser.get().getPassword().equals(myUser.getPassword())) ?
//          new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK):new ResponseEntity<>("Password is incorrect", HttpStatus.BAD_REQUEST))
//        : new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);

      dbUser.map(user -> ((user.getPassword().equals(myUser.getPassword())) ?
          new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK) : new ResponseEntity<>("Password is incorrect", HttpStatus.FORBIDDEN)))
        .orElseGet(() -> new ResponseEntity<>("User not found", HttpStatus.FORBIDDEN));
  }

  @PostMapping(value = "/all-with-roles")
  public ResponseEntity<?> getAllProductsWithRoles(@RequestBody MyUser myUser) {
    Optional<MyUser> dbUser = myUserRepository.findByUsername(myUser.getUsername());
    if (dbUser.isPresent()) {
      BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
      boolean passwordMatches = bCrypt.matches(myUser.getPassword(), dbUser.get().getPassword());
      //bCrypt encripteaza parola din FE si verifica daca e aceeasi cu cea din DB
      if (passwordMatches)
        if (dbUser.get().getRole().equals("user")) {
          return new ResponseEntity<>(productRepository.findAllForUser(), HttpStatus.OK);
        } else
          return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
      else
        return new ResponseEntity<>("Incorrect password", HttpStatus.FORBIDDEN);
    } else return new ResponseEntity<>("User not found", HttpStatus.FORBIDDEN);

  }

//  @PostMapping(value = "/add")//save product in Product table
//  public ResponseEntity<?> saveProduct(@RequestBody Product product){
//
//  }


    @PostMapping(value = "/all")
    public ResponseEntity<?> getAllProducts(@RequestBody MyUserDTO myUserDTO) {
        Optional<MyUser> databaseUser = myUserRepository.findByUsername(myUserDTO.getUsername());
        if (databaseUser.isPresent()) {
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            boolean passwordMatches = bcrypt.matches(myUserDTO.getPassword(), databaseUser.get().getPassword());
            if (passwordMatches)
                if (databaseUser.get().getRole().equals("user")) {
                    List<Product> allProducts = productRepository.findAllForUser();
                    List<ProductMyUserDTO> allProductsDTO = allProducts
                            .stream()
                            .map(productMapper::toProductMyUserDTO)
                            .toList();
                    return new ResponseEntity<>(allProductsDTO, HttpStatus.OK);
                } else {
                    List<Product> allProducts = productRepository.findAll();
                    List<ProductMyUserDTO> allProductsDTO = allProducts
                            .stream()
                            .map(productMapper::toProductMyUserDTO)
                            .toList();
                    return new ResponseEntity<>(allProductsDTO, HttpStatus.OK);
                }
            else
                return new ResponseEntity<>("Password is incorrect", HttpStatus.FORBIDDEN);
        } else
            return new ResponseEntity<>("Username not found", HttpStatus.FORBIDDEN);
    }

  @PostMapping(value = "/add")
  public ResponseEntity<?> saveProduct(@RequestBody ProductMyUserDTO productUserDTO) {
    Optional<MyUser> databaseUser = myUserRepository.findByUsername(productUserDTO.getUsername());
    if (databaseUser.isPresent()) {
      BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
      boolean passwordMatches = bcrypt.matches(productUserDTO.getPassword(), databaseUser.get().getPassword());
      if (passwordMatches)
        if (databaseUser.get().getRole().equals("admin")) {
          return new ResponseEntity<>(
            productRepository.save(productMyUserMapper.toProductEntity(productUserDTO)),
            HttpStatus.OK);

        } else
          return new ResponseEntity<>("Only admin can insert", HttpStatus.FORBIDDEN);
      else
        return new ResponseEntity<>("Password is incorrect", HttpStatus.FORBIDDEN);
    } else
      return new ResponseEntity<>("Username not found", HttpStatus.FORBIDDEN);
  }
}
