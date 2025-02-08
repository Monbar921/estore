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
import ru.isands.test.estore.dao.entity.ElectroType;
import ru.isands.test.estore.dto.ElectroTypeDTO;
import ru.isands.test.estore.dto.PurchaseTypeDTO;
import ru.isands.test.estore.service.ElectroTypeService;
import ru.isands.test.estore.service.PurchaseTypeService;

import javax.validation.Valid;

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
}
