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
import ru.isands.test.estore.dto.ElectroTypeDTO;
import ru.isands.test.estore.service.ElectroTypeService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "ElectroType", description = "Сервис для выполнения операций над типами электроники")
@RequestMapping("/estore/api/electro_type")
public class ElectroTypeController {
    private final ElectroTypeService electroTypeService;

    @PostMapping("/add")
    @Operation(summary = "Добавление типа электроники", responses = {
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
                                                  @Valid @RequestBody ElectroTypeDTO electroTypeDTO) {
        electroTypeService.save(electroTypeDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAll")
    @Operation(summary = "Просмотр списка всех типов товаров с пагинацией", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ: возвращает список записей",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = ElectroTypeDTO.class)
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
    public ResponseEntity<List<ElectroTypeDTO>> findAll(@Parameter(description = "Номер страницы", required = true)
                                                        @RequestParam Integer page,
                                                        @Parameter(description = "Количество записей на странице", required = true)
                                                        @RequestParam @Size(min = 1) Integer size) {
        return ResponseEntity.ok(electroTypeService.findAllDto(page, size));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Удаление типа товара", responses = {
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
    public ResponseEntity<String> delete(@Parameter(description = "id типа товара", required = true)
                                         @RequestParam @NotNull Long id) {
        electroTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
