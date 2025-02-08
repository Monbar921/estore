package ru.isands.test.estore.dto;


import lombok.Data;
import javax.validation.constraints.Size;

@Data
public class ShopDTO {
    @Size(min = 1, max = 150)
    private String name;
    private String address;
}
