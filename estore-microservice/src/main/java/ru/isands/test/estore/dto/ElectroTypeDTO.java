package ru.isands.test.estore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isands.test.estore.dao.entity.ElectroType;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectroTypeDTO {
    private Long id;
    @Size(min = 1, max = 150)
    private String name;

    public ElectroTypeDTO(ElectroType entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
