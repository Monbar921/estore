package ru.isands.test.estore.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.isands.test.estore.service.FileService;

@AllArgsConstructor
@RestController
@Tag(name = "Shop", description = "Сервис для выполнения операций над файлами")
@RequestMapping("/estore/api/file")
public class FileController {

	private final FileService fileService;
	@Operation(summary = "Загрузка данных из csv файлов в архиве", responses = {
			@ApiResponse(description = "Загрузка данных из csv файлов в архиве")
	})
	@RequestMapping(value = "/zip/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Boolean> saveFile(@RequestPart(value = "file") final MultipartFile file) {
		HttpStatus status = HttpStatus.OK;
		boolean result = fileService.saveFile(file);
		if(!result) {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(result, status);
	}
}
