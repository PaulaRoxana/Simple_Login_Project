package ro.itschool.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductMyUserDTO extends MyUserDTO {

    private Long id;

    private String productName;

    private Float productPrice;
}
