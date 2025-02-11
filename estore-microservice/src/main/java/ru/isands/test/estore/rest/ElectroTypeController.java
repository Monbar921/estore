package ru.isands.test.estore.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.dto.ElectroTypeDTO;
import ru.isands.test.estore.service.ElectroTypeService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Shop", description = "Сервис для выполнения операций над типами электроники")
@RequestMapping("/estore/api/electro_type")
public class ElectroTypeController {
	private final ElectroTypeService electroTypeService;
	@PostMapping("/add")
	@Operation(summary = "Добавление типа электроники", responses = {
		@ApiResponse(description = "Добавление типа электроники")
	})
	public ResponseEntity<String> addPurchaseType(@Valid @RequestBody ElectroTypeDTO electroTypeDTO) {
		electroTypeService.save(electroTypeDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/findAll")
	@Operation(summary = "Просмотр списка всех типов товаров с пагинацией", responses = {
			@ApiResponse(description = "Просмотр списка всех типов товаров с пагинацией")
	})
	public ResponseEntity<List<ElectroTypeDTO>> findAll(@RequestParam Integer page,
														@RequestParam @Size(min = 1) Integer size) {
		return ResponseEntity.ok(electroTypeService.findAllDto(page, size));
	}

	@DeleteMapping ("/delete")
	@Operation(summary = "Удаление типа товара", responses = {
			@ApiResponse(description = "Удаление типа товара")
	})
	public ResponseEntity<String> delete(@RequestParam @NotNull Long id) {
		electroTypeService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
