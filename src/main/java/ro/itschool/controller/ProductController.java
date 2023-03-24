package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.itschool.entity.Product;
import ro.itschool.repository.ProductRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {
  @Autowired
  private final ProductRepository productRepository;

  @GetMapping
  List<Product> getAllProducts() {
    return productRepository.findAll();
  }

}
