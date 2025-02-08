package ru.isands.test.estore.dto;


import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ElectroItemDTO {
    @Size(min = 1, max = 150)
    private String name;
    @NotNull
    private ElectroTypeDTO electroType;
    @NotNull
    @Min(value = 0)
    private Long price;
    @NotNull
    @Min(value = 0)
    private Integer count;
    @NotNull
    private Boolean archived;
    private String description;
}
