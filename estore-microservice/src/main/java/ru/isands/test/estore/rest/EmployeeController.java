package ru.isands.test.estore.rest;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.isands.test.estore.dto.BestSellerDTO;
import ru.isands.test.estore.dto.EmployeeDTO;
import ru.isands.test.estore.service.EmployeeService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Employee", description = "Сервис для выполнения операций над сотрудниками магазина")
@RequestMapping("/estore/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/add")
    @Operation(summary = "Добавление сотрудника", responses = {
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
    public ResponseEntity<String> addEmployee(@Parameter(description = "DTO для сохранения", required = true)
                                              @RequestBody @Valid EmployeeDTO employeeDTO) {
        employeeService.save(employeeDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/best_quantity_sell")
    @Operation(summary = "Получение списка лучших сотрудников в зависимости от занимаемой должности по" +
            " количеству проданных товаров и году", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ: возвращает список записей",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = BestSellerDTO.class)
                    )
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
    public ResponseEntity<List<BestSellerDTO>> findBestSellingQuantityEmployeesByPositionAndYear(
            @Parameter(description = "id должности сотрудника", required = true) @NotNull @RequestParam Long positionTypeId
            , @Parameter(description = "Год поиска лучшего сотрудника", required = true)
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate year) {

        return ResponseEntity.ok(employeeService.findBestEmployeesByPositionAndYearAndPurchaseCount(positionTypeId, year));
    }

    @GetMapping("/best_sum_sell")
    @Operation(summary = "Получение списка лучших сотрудников в зависимости от занимаемой должности по" +
            " сумме проданных товаров и году", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ: возвращает список записей",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = BestSellerDTO.class)
                    )
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
    public ResponseEntity<List<BestSellerDTO>> findBestSellingSumEmployeesByPositionAndYear(
            @Parameter(description = "id должности сотрудника", required = true) @NotNull @RequestParam Long positionTypeId
            , @Parameter(description = "Год поиска лучшего сотрудника", required = true)
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate year) {

        return ResponseEntity.ok(employeeService.findBestEmployeesByPositionAndPurchaseSum(positionTypeId, year));
    }

    @GetMapping("/best_etype_sell")
    @Operation(summary = "Получение лучшего сотрудника в зависимости от занимаемой должности и" +
            " типу товара", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ: возвращает список записей",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BestSellerDTO.class)
                    )
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
    public ResponseEntity<BestSellerDTO> findBestEmployeeByPositionAndElectroType(
            @Parameter(description = "id должности сотрудника", required = true)
            @NotNull @RequestParam Long positionTypeId,
            @Parameter(description = "id типа товара", required = true)
            @NotNull @RequestParam Long electroTypeId) {
        return ResponseEntity
                .ok(employeeService.findBestEmployeeByPositionAndElectroType(positionTypeId, electroTypeId).orElse(null));
    }

    @GetMapping("/findAll")
    @Operation(summary = "Просмотр списка всех сотрудников с пагинацией", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ: возвращает список записей",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = EmployeeDTO.class)
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
    public ResponseEntity<List<EmployeeDTO>> findAll(@Parameter(description = "Номер страницы", required = true)
                                                     @RequestParam Integer page,
                                                     @Parameter(description = "Количество записей на странице", required = true)
                                                     @RequestParam @Size(min = 1) Integer size) {
        return ResponseEntity.ok(employeeService.findAllDto(page, size));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Удаление сотрудника", responses = {
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
    public ResponseEntity<String> delete(@Parameter(description = "id сотрудника", required = true)
                                         @RequestParam @NotNull Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
