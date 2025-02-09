package ru.isands.test.estore.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.isands.test.estore.dao.entity.PositionType;
import ru.isands.test.estore.dao.repo.EmployeeRepository;
import ru.isands.test.estore.dto.BestSellerDTO;
import ru.isands.test.estore.dto.ElectroItemDTO;
import ru.isands.test.estore.dto.ElectroTypeDTO;
import ru.isands.test.estore.dto.EmployeeDTO;
import ru.isands.test.estore.exception.EntityNotExistsException;
import ru.isands.test.estore.service.ElectroItemService;
import ru.isands.test.estore.service.EmployeeService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

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
	public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		employeeService.save(employeeDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/best_quantity_sell")
	@Operation(summary = "Получение списка лучших сотрудников в зависимости от занимаемой должности по" +
			" количеству проданных товаров и году", responses = {
			@ApiResponse(description = "Получение списка лучших сотрудников в зависимости от занимаемой должности по" +
					" количеству проданных товаров и году")
	})
	public ResponseEntity<List<BestSellerDTO>> findBestSellingQuantityEmployeesByPositionAndYear(
			@NotNull @RequestParam Long positionTypeId
			,@NotNull @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate year) {

		return ResponseEntity.ok(employeeService.findBestEmployeesByPositionAndYearAndPurchaseCount(positionTypeId, year));
	}

	@GetMapping("/best_sum_sell")
	@Operation(summary = "Получение списка лучших сотрудников в зависимости от занимаемой должности по" +
			" сумме проданных товаров и году", responses = {
			@ApiResponse(description = "Получение списка лучших сотрудников в зависимости от занимаемой должности по" +
					" сумме проданных товаров и году")
	})
	public ResponseEntity<List<BestSellerDTO>> findBestSellingSumEmployeesByPositionAndYear(
			@NotNull @RequestParam Long positionTypeId
			,@NotNull @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate year) {

		return ResponseEntity.ok(employeeService.findBestEmployeesByPositionAndPurchaseSum(positionTypeId, year));
	}

	@GetMapping("/best_etype_sell")
	@Operation(summary = "Получение лучшего сотрудника в зависимости от занимаемой должности и" +
			" типу товара", responses = {
			@ApiResponse(description = "Получение лучшего сотрудника в зависимости от занимаемой должности и" +
					"типу товара")
	})
	public ResponseEntity<BestSellerDTO> findBestEmployeeByPositionAndElectroType(
			@NotNull @RequestParam Long positionTypeId, @NotNull @RequestParam Long electroTypeId) {
		return ResponseEntity
				.ok(employeeService.findBestEmployeeByPositionAndElectroType(positionTypeId, electroTypeId).orElse(null));
	}

	@GetMapping("/findAll")
	@Operation(summary = "Просмотр списка всех сотрудников с пагинацией", responses = {
			@ApiResponse(description = "Просмотр списка всех сотрудников с пагинацией")
	})
	public ResponseEntity<List<EmployeeDTO>> findAll(@RequestParam Integer page,
														@RequestParam @Size(min = 1) Integer size) {
		return ResponseEntity.ok(employeeService.findAllDto(page, size));
	}
}
