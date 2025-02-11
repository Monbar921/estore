package ru.isands.test.estore.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isands.test.estore.dao.entity.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
    private Long id;
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

    public PurchaseDTO(Purchase purchase) {
        this.id = purchase.getId();
        this.electroItem = purchase.getElectroItem().getId();
        this.employee = purchase.getEmployee().getId();
        this.shop = purchase.getShop().getId();
        this.purchaseDate = purchase.getPurchaseDate();
        this.purchaseType = purchase.getPurchaseType().getId();
    }
}
