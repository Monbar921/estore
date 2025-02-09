package ru.isands.test.estore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isands.test.estore.dao.entity.ElectroShop;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectroShopDTO {

    @NotNull
    private Long shop;
    @NotNull
    private Long electroItem;
    @NotNull
    @Min(value = 0)
    private Integer count;

    public ElectroShopDTO(ElectroShop electroShop) {
        this.shop = electroShop.getShop().getId();
        this.electroItem = electroShop.getElectroItem().getId();
        this.count = electroShop.getCount();
    }
}
