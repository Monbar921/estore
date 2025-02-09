package ru.isands.test.estore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isands.test.estore.dao.entity.Employee;

@Data
@NoArgsConstructor
public class BestSellerDTO {
    private String lastName;
    private String firstName;
    private String patronymic;
    private long purchaseCount;
    private long purchaseSum;

    public BestSellerDTO(Employee employee, long purchaseCount, long purchaseSum) {
        this.lastName = employee.getLastName();
        this.firstName = employee.getFirstName();
        this.patronymic = employee.getPatronymic();
        this.purchaseCount = purchaseCount;
        this.purchaseSum = purchaseSum;
    }

}
