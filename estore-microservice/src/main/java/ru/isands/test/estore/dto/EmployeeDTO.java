package ru.isands.test.estore.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.isands.test.estore.dao.entity.PositionType;
import ru.isands.test.estore.dao.entity.Shop;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Data
public class EmployeeDTO {
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
    private PositionType positionType;
    @NotNull
    private Shop shop;
    @NotNull
    private Boolean gender;
}
