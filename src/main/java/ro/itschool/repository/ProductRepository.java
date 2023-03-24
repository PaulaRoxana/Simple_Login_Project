package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.Product;

public interface ProductRepository extends JpaRepository <Product, Long> {
}
