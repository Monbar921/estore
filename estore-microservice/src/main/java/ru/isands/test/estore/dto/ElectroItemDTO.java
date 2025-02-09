package ru.isands.test.estore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isands.test.estore.dao.entity.ElectroItem;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectroItemDTO {
    private Long id;
    @Size(min = 1, max = 150)
    private String name;
    @NotNull
    private Long electroType;
    @NotNull
    @Min(value = 0)
    private Long price;
    @NotNull
    @Min(value = 0)
    private Integer count;
    @NotNull
    private Boolean archived;
    private String description;

    public ElectroItemDTO(ElectroItem electroItem) {
        this.id = electroItem.getId();
        this.name = electroItem.getName();
        this.electroType = electroItem.getElectroType().getId();
        this.price = electroItem.getPrice();
        this.count = electroItem.getCount();
        this.archived = electroItem.isArchived();
        this.description = electroItem.getDescription();
    }
}
