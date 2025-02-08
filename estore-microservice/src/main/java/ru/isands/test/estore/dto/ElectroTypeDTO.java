package ru.isands.test.estore.dto;


import lombok.Data;
import javax.validation.constraints.Size;

@Data
public class ElectroTypeDTO {
    @Size(min = 1, max = 150)
    private String name;
}
