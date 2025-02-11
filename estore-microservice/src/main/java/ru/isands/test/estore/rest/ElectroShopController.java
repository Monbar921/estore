package ru.isands.test.estore.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.dto.ElectroShopDTO;
import ru.isands.test.estore.service.ElectroShopService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Shop", description = "Сервис для выполнения операций над связью магазин/товар")
@RequestMapping("/estore/api/electro_shop")
public class ElectroShopController {
	private final ElectroShopService electroShopService;
	@PostMapping("/add")
	@Operation(summary = "Добавление связи магазин/товар", responses = {
		@ApiResponse(description = "Добавление связи магазин/товар")
	})
	public ResponseEntity<String> addElectroShop(@Valid @RequestBody ElectroShopDTO electroShopDTO) {
		electroShopService.save(electroShopDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/findAll")
	@Operation(summary = "Просмотр списка всех связей магазин/товар с пагинацией", responses = {
			@ApiResponse(description = "Просмотр списка всех связей магазин/товар с пагинацией")
	})
	public ResponseEntity<List<ElectroShopDTO>> findAll(@RequestParam Integer page,
														@RequestParam @Size(min = 1) Integer size) {
		return ResponseEntity.ok(electroShopService.findAllDto(page, size));
	}

	@DeleteMapping ("/delete")
	@Operation(summary = "Удаление связи магазин/товар", responses = {
			@ApiResponse(description = "Удаление связи магазин/товар")
	})
	public ResponseEntity<String> delete(@RequestParam @NotNull Long electroShop, @RequestParam @NotNull Long electroItem) {
		electroShopService.deleteByEmployeeAndElectroType(electroShop, electroItem);
		return ResponseEntity.ok().build();
	}
}
