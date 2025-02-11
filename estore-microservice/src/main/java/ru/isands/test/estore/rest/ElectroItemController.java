package ru.isands.test.estore.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.dto.ElectroItemDTO;
import ru.isands.test.estore.service.ElectroItemService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Shop", description = "Сервис для выполнения операций над товарами")
@RequestMapping("/estore/api/electro_item")
public class ElectroItemController {
	private final ElectroItemService electroItemService;
	@PostMapping("/add")
	@Operation(summary = "Добавление товара", responses = {
		@ApiResponse(description = "Добавление товара")
	})
	public ResponseEntity<String> addPurchaseType(@Valid @RequestBody ElectroItemDTO electroItemDTO) {
		electroItemService.save(electroItemDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/findAll")
	@Operation(summary = "Просмотр списка всех товаров с пагинацией", responses = {
			@ApiResponse(description = "Просмотр списка всех товаров с пагинацией")
	})
	public ResponseEntity<List<ElectroItemDTO>> findAll(@RequestParam Integer page,
														@RequestParam @Size(min = 1) Integer size) {
		return ResponseEntity.ok(electroItemService.findAllDto(page, size));
	}

	@DeleteMapping ("/delete")
	@Operation(summary = "Удаление товаров", responses = {
			@ApiResponse(description = "Удаление товаров")
	})
	public ResponseEntity<String> delete(@RequestParam @NotNull Long id) {
		electroItemService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
