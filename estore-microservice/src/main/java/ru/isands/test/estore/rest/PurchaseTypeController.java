package ru.isands.test.estore.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.dto.PurchaseTypeDTO;
import ru.isands.test.estore.service.PurchaseTypeService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "PurchaseType", description = "Сервис для выполнения операций над типами покупок")
@RequestMapping("/estore/api/purchase_type")
public class PurchaseTypeController {
    private final PurchaseTypeService purchaseTypeService;

    @PostMapping("/add")
    @Operation(summary = "Добавление типа покупки", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ: возвращает пустую строку",
                    content = @Content(schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные параметры запроса",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Не найдены сущности, передаваемые в запросе добавления записи",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Найдено несколько сущностей при добавлении записи",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content
            )
    })
    public ResponseEntity<String> addPurchaseType(@Parameter(description = "DTO для сохранения", required = true)
                                                  @Valid @RequestBody PurchaseTypeDTO purchaseTypeDTO) {
        purchaseTypeService.save(purchaseTypeDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAll")
    @Operation(summary = "Просмотр списка всех типов покупок с пагинацией", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ: возвращает список записей",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = PurchaseTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные параметры запроса",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content
            )
    })
    public ResponseEntity<List<PurchaseTypeDTO>> findAll(@Parameter(description = "Номер страницы", required = true)
                                                         @RequestParam Integer page,
                                                         @Parameter(description = "Количество записей на странице", required = true)
                                                         @RequestParam @Size(min = 1) Integer size) {
        return ResponseEntity.ok(purchaseTypeService.findAllDto(page, size));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Удаление типа покупки", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ: возвращает пустую строку",
                    content = @Content(schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные параметры запроса",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Не найдены сущности, передаваемые в запросе удаления записи",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content
            )
    })
    public ResponseEntity<String> delete(@Parameter(description = "id типа покупки", required = true) @RequestParam @NotNull Long id) {
        purchaseTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
