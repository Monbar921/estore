package ru.isands.test.estore.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.dto.ElectroEmployeeDTO;
import ru.isands.test.estore.service.ElectroEmployeeService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Shop", description = "Сервис для выполнения операций над связью сотрудник/тип электроники")
@RequestMapping("/estore/api/electro_employee")
public class ElectroEmployeeController {
	private final ElectroEmployeeService electroEmployeeService;
	@PostMapping("/add")
	@Operation(summary = "Добавление связи сотрудник/тип электроники", responses = {
		@ApiResponse(description = "Добавление связи сотрудник/тип электроники")
	})
	public ResponseEntity<String> addElectroEmployee(@Valid @RequestBody ElectroEmployeeDTO electroEmployeeDTO) {
		electroEmployeeService.save(electroEmployeeDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/findAll")
	@Operation(summary = "Просмотр списка всех связей сотрудник/тип электроники с пагинацией", responses = {
			@ApiResponse(description = "Просмотр списка всех связей магазин/товар с пагинацией")
	})
	public ResponseEntity<List<ElectroEmployeeDTO>> findAll(@RequestParam Integer page,
														@RequestParam @Size(min = 1) Integer size) {
		return ResponseEntity.ok(electroEmployeeService.findAllDto(page, size));
	}

	@DeleteMapping ("/delete")
	@Operation(summary = "Удаление связи сотрудник/тип электроники", responses = {
			@ApiResponse(description = "Удаление связи сотрудник/тип электроники")
	})
	public ResponseEntity<String> delete(@RequestParam @NotNull Long electroEmployee, @RequestParam @NotNull Long electroType) {
		electroEmployeeService.deleteByEmployeeAndElectroType(electroEmployee, electroType);
		return ResponseEntity.ok().build();
	}
}
