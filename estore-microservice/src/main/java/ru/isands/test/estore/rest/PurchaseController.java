package ru.isands.test.estore.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.isands.test.estore.dto.EmployeeDTO;
import ru.isands.test.estore.dto.PurchaseDTO;
import ru.isands.test.estore.service.EmployeeService;
import ru.isands.test.estore.service.PurchaseService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@Tag(name = "Employee", description = "Сервис для выполнения операций над покупками")
@RequestMapping("/estore/api/purchase")
public class PurchaseController {
	private final PurchaseService purchaseService;

	@PostMapping("/add")
	@Operation(summary = "Добавление покупки", responses = {
			@ApiResponse(description = "Добавление покупки")
	})
	public ResponseEntity<String> addPurchase(@Valid @RequestBody PurchaseDTO purchaseDTO) {
		purchaseService.save(purchaseDTO);
		return ResponseEntity.ok().build();
	}
}
