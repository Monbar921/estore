package ru.isands.test.estore.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.isands.test.estore.dto.ElectroItemDTO;
import ru.isands.test.estore.dto.EmployeeDTO;
import ru.isands.test.estore.service.ElectroItemService;
import ru.isands.test.estore.service.EmployeeService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@Tag(name = "Employee", description = "Сервис для выполнения операций над сотрудниками магазина")
@RequestMapping("/estore/api/employee")
public class EmployeeController {
	private final EmployeeService employeeService;

	@PostMapping("/add")
	@Operation(summary = "Добавление типа электроники", responses = {
			@ApiResponse(description = "Добавление типа электроники")
	})
	public ResponseEntity<String> addPurchaseType(@Valid @RequestBody EmployeeDTO employeeDTO) {
		employeeService.save(employeeDTO);
		return ResponseEntity.ok().build();
	}
}
