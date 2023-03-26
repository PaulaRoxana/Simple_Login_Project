package ro.itschool.mapper;

import org.springframework.stereotype.Component;
import ro.itschool.controller.model.ProductMyUserDTO;
import ro.itschool.entity.Product;

@Component
public class
ProductMapper {

    public ProductMyUserDTO toProductMyUserDTO(Product product) {
        ProductMyUserDTO productMyUserDTO = new ProductMyUserDTO();
        productMyUserDTO.setProductPrice(product.getProductPrice());
        productMyUserDTO.setProductName(product.getProductName());
        return productMyUserDTO;
    }
}
