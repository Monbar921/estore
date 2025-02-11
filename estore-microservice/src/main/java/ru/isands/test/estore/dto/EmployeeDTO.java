package ru.isands.test.estore.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isands.test.estore.dao.entity.Employee;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    @Size(min = 1, max = 100)
    private String lastName;
    @Size(min = 1, max = 100)
    private String firstName;
    @Size(min = 1, max = 100)
    private String patronymic;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    @NotNull
    private Long positionType;
    @NotNull
    private Long shop;
    @NotNull
    private Boolean gender;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.lastName = employee.getLastName();
        this.firstName = employee.getFirstName();
        this.patronymic = employee.getPatronymic();
        this.birthDate = employee.getBirthDate();
        this.positionType = employee.getPositionType().getId();
        this.shop = employee.getShop().getId();
        this.gender = employee.isGender();
    }
}
