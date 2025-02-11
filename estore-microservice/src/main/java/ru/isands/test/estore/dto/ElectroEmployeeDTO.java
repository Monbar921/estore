package ru.isands.test.estore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isands.test.estore.dao.entity.ElectroEmployee;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectroEmployeeDTO {
    @NotNull
    private Long employee;
    @NotNull
    private Long electroType;

    public ElectroEmployeeDTO(ElectroEmployee electroEmployee) {
        this.employee = electroEmployee.getEmployee().getId();
        this.electroType = electroEmployee.getElectroType().getId();
    }
}
