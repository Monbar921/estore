package ru.isands.test.estore.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.dto.ShopDTO;
import ru.isands.test.estore.service.ShopService;
import ru.isands.test.estore.dao.entity.Shop;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@Tag(name = "Shop", description = "Сервис для выполнения операций над магазинами")
@RequestMapping("/estore/api/shop")
public class ShopController {
	private final ShopService shopService;
	@PostMapping("/add")
	@Operation(summary = "Добавление магазина", responses = {
		@ApiResponse(description = "Добавление магазина")
	})
	public ResponseEntity<String> addShop(@Valid @RequestBody ShopDTO shop) {
		shopService.save(shop);
		return ResponseEntity.ok().build();
	}
}
