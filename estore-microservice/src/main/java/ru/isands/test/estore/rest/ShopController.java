package ru.isands.test.estore.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.dto.BestSellerDTO;
import ru.isands.test.estore.dto.PurchaseTypeDTO;
import ru.isands.test.estore.dto.ShopDTO;
import ru.isands.test.estore.service.ShopService;
import ru.isands.test.estore.dao.entity.Shop;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

	@GetMapping("/sum_by_ptype")
	@Operation(summary = "Получение суммы денежных средств, полученной магазином через оплату определенным способом", responses = {
			@ApiResponse(description = "Получение суммы денежных средств, полученной магазином через оплату " +
					"определенным способом")
	})
	public ResponseEntity<Long> findPurchaseSumByPurchaseType(
			@NotNull @RequestParam Long shopId, @NotNull @RequestParam Long electroTypeId) {
		return ResponseEntity
				.ok(shopService.findPurchaseSumByPurchaseType(shopId, electroTypeId));
	}

	@GetMapping("/findAll")
	@Operation(summary = "Просмотр списка всех магазинов с пагинацией", responses = {
			@ApiResponse(description = "Просмотр списка всех магазинов с пагинацией")
	})
	public ResponseEntity<List<ShopDTO>> findAll(@RequestParam Integer page,
														 @RequestParam @Size(min = 1) Integer size) {
		return ResponseEntity.ok(shopService.findAllDto(page, size));
	}
}
