package ru.isands.test.estore.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Purchase", description = "Сервис для выполнения операций над покупками")
@RequestMapping("/estore/api/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/add")
    @Operation(summary = "Добавление покупки", responses = {
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
    public ResponseEntity<String> addPurchase(@Parameter(description = "DTO для сохранения", required = true)
                                              @Valid @RequestBody PurchaseDTO purchaseDTO) {
        purchaseService.save(purchaseDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAll")
    @Operation(summary = "Просмотр списка всех покупок с пагинацией", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ: возвращает список записей",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = PurchaseDTO.class)
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
    public ResponseEntity<List<PurchaseDTO>> findAll(@Parameter(description = "Номер страницы", required = true)
                                                     @RequestParam Integer page,
                                                     @Parameter(description = "Количество записей на странице", required = true)
                                                     @RequestParam @Size(min = 1) Integer size) {
        return ResponseEntity.ok(purchaseService.findAllDto(page, size));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Удаление покупки", responses = {
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
    public ResponseEntity<String> delete(@Parameter(description = "id покупки", required = true) @RequestParam @NotNull Long id) {
        purchaseService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sort_by_dates")
    @Operation(summary = "Сортировка покупок внутри диапазона дат с пагинацией", responses = {
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
                    description = "Неверно указаны даты",
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
    public ResponseEntity<List<PurchaseDTO>> findAllInsideDates(
            @Parameter(description = "Дата начала диапазона", required = true)
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate from,
            @Parameter(description = "Дата конца диапазона", required = true)
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate to,
            @Parameter(description = "Страница выборки", required = true)
            @RequestParam Integer page,
            @Parameter(description = "Размер выборки", required = true)
            @RequestParam @Size(min = 1) Integer size,
            @Parameter(description = "true - по возрастанию, false - по убыванию", required = true)
            @RequestParam(required = false) Boolean isAsc) {
        return ResponseEntity.ok(purchaseService.findAllInsideDates(from, to, page, size, isAsc));
    }
}
