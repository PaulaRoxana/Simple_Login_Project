package ro.itschool.mapper;

import org.springframework.stereotype.Component;
import ro.itschool.controller.model.ProductMyUserDTO;
import ro.itschool.entity.Product;

@Component
public class ProductMyUserMapper {

    public Product toProductEntity(ProductMyUserDTO productMyUserDTO) {
        Product product = new Product();
        product.setProductName(productMyUserDTO.getProductName());
        product.setProductPrice(productMyUserDTO.getProductPrice());
        return product;
    }


}
