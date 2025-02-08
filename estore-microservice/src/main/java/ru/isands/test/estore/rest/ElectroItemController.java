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
import ru.isands.test.estore.dto.ElectroItemDTO;
import ru.isands.test.estore.dto.ElectroTypeDTO;
import ru.isands.test.estore.service.ElectroItemService;
import ru.isands.test.estore.service.ElectroTypeService;

import javax.validation.Valid;

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
}
