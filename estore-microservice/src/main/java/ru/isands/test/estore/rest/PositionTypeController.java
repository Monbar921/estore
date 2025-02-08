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
import ru.isands.test.estore.dto.PositionTypeDTO;
import ru.isands.test.estore.dto.PurchaseTypeDTO;
import ru.isands.test.estore.service.PositionTypeService;
import ru.isands.test.estore.service.PurchaseTypeService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@Tag(name = "Shop", description = "Сервис для выполнения операций над должностями")
@RequestMapping("/estore/api/position_type")
public class PositionTypeController {
	private final PositionTypeService positionTypeService;
	@PostMapping("/add")
	@Operation(summary = "Добавление должности", responses = {
		@ApiResponse(description = "Добавление должности")
	})
	public ResponseEntity<String> addPositionType(@Valid @RequestBody PositionTypeDTO positionTypeDTO) {
		positionTypeService.save(positionTypeDTO);
		return ResponseEntity.ok().build();
	}
}
