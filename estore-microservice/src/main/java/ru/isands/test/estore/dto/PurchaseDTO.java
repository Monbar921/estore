package ru.isands.test.estore.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.isands.test.estore.dao.entity.ElectroItem;
import ru.isands.test.estore.dao.entity.Employee;
import ru.isands.test.estore.dao.entity.PurchaseType;
import ru.isands.test.estore.dao.entity.Shop;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class PurchaseDTO {
    @NotNull
    private Long electroItem;
    @NotNull
    private Long employee;
    @NotNull
    private Long shop;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate purchaseDate;
    @NotNull
    private Long purchaseType;
}
