package ru.isands.test.estore.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.dao.entity.PurchaseType;
import ru.isands.test.estore.dao.entity.Shop;
import ru.isands.test.estore.dto.PurchaseDTO;
import ru.isands.test.estore.dto.PurchaseTypeDTO;
import ru.isands.test.estore.service.PurchaseTypeService;
import ru.isands.test.estore.service.ShopService;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Shop", description = "Сервис для выполнения операций над типами покупок")
@RequestMapping("/estore/api/purchase_type")
public class PurchaseTypeController {
	private final PurchaseTypeService purchaseTypeService;
	@PostMapping("/add")
	@Operation(summary = "Добавление типа покупки", responses = {
		@ApiResponse(description = "Добавление типа покупки")
	})
	public ResponseEntity<String> addPurchaseType(@Valid @RequestBody PurchaseTypeDTO purchaseTypeDTO) {
		purchaseTypeService.save(purchaseTypeDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/findAll")
	@Operation(summary = "Просмотр списка всех типов покупок с пагинацией", responses = {
			@ApiResponse(description = "Просмотр списка всех типов покупок с пагинацией")
	})
	public ResponseEntity<List<PurchaseTypeDTO>> findAll(@RequestParam Integer page,
													 @RequestParam @Size(min = 1) Integer size) {
		return ResponseEntity.ok(purchaseTypeService.findAllDto(page, size));
	}
}
