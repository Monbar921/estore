package ru.isands.test.estore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isands.test.estore.dao.entity.Shop;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {
    private Long id;
    @Size(min = 1, max = 150)
    private String name;
    private String address;

    public ShopDTO(Shop entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = entity.getAddress();
    }
}
