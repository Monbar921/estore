package ru.isands.test.estore.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.dto.PurchaseDTO;
import ru.isands.test.estore.service.PurchaseService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Employee", description = "Сервис для выполнения операций над покупками")
@RequestMapping("/estore/api/purchase")
public class PurchaseController {
	private final PurchaseService purchaseService;

	@PostMapping("/add")
	@Operation(summary = "Добавление покупки", responses = {
			@ApiResponse(description = "Добавление покупки")
	})
	public ResponseEntity<String> addPurchase(@Valid @RequestBody PurchaseDTO purchaseDTO) {
		purchaseService.save(purchaseDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/findAll")
	@Operation(summary = "Просмотр списка всех покупок с пагинацией", responses = {
			@ApiResponse(description = "Просмотр списка всех покупок с пагинацией")
	})
	public ResponseEntity<List<PurchaseDTO>> findAll(@RequestParam Integer page,
														 @RequestParam @Size(min = 1) Integer size) {
		return ResponseEntity.ok(purchaseService.findAllDto(page, size));
	}

	@DeleteMapping ("/delete")
	@Operation(summary = "Удаление покупки", responses = {
			@ApiResponse(description = "Удаление покупки")
	})
	public ResponseEntity<String> delete(@RequestParam @NotNull Long id) {
		purchaseService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping ("/sort_by_dates")
	@Operation(summary = "Сортировка покупок внутри диапазона дат с пагинацией", responses = {
			@ApiResponse(description = "Сортировка покупок внутри диапазона дат с пагинацией")
	})
	public ResponseEntity<List<PurchaseDTO>> findAllInsideDates(
									@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate from,
									@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate to,
									@RequestParam Integer page,
									@RequestParam @Size(min = 1) Integer size,
									@RequestParam(required = false) Boolean isAsc) {
		return ResponseEntity.ok(purchaseService.findAllInsideDates(from, to, page, size, isAsc));
	}
}
