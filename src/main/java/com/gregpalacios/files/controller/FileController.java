package com.gregpalacios.files.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gregpalacios.files.dto.FileDTO;
import com.gregpalacios.files.util.FileUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/files")
@Tag(name = "File Controller", description = "Operaciones para el manejo de archivos")
public class FileController {

	@Value("${upload.path.documentos}")
	private String basePath;

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Operation(description = "Subir archivo")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Archivo guardado exitosamente", content = @Content()),
			@ApiResponse(responseCode = "401", description = "No estas autorizado para acceder a este recurso", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Está prohibido acceder al recurso", content = @Content()),
			@ApiResponse(responseCode = "404", description = "No se encuentra el recurso que intentabas alcanzar", content = @Content()) })
	@PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws Exception, IOException {

		String fileName = null;
		Map<String, Object> response = new HashMap<>();

		String mimeType = file.getContentType();

		if (mimeType.contains("pdf")) {
			try {
				byte[] newFile = file.getBytes();
				FileDTO fileDTO = new FileDTO();
				fileDTO.setFilePath(basePath);
				fileDTO.setFileFormat("pdf");

				fileName = FileUtil.createFile(newFile, fileDTO);

				response.put("estado", "1");
				response.put("mensaje", "Archivo guardado correctamente");
				response.put("archivo", fileName);
			} catch (IOException e) {
				logger.error(e.getMessage());
				response.put("estado", "0");
				response.put("mensaje", "Error al guardar el archivo");
			}
		} else {
			response.put("estado", "0");
			response.put("mensaje", "Formato inválido. Debe subir un archivo pdf");
		}

		return ResponseEntity.ok(response);
	}

	@Operation(description = "Descargar archivo")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Archivo descargado exitosamente", content = @Content()),
			@ApiResponse(responseCode = "401", description = "No estas autorizado para acceder a este recurso", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Está prohibido acceder al recurso", content = @Content()),
			@ApiResponse(responseCode = "404", description = "No se encuentra el recurso que intentabas alcanzar", content = @Content()) })
	@GetMapping("/download/{name}")
	public ResponseEntity<InputStreamResource> download(@PathVariable("name") String name)
			throws Exception, IOException {

		File file = new File(basePath + name);
		InputStream in = new FileInputStream(file.getPath());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=" + name);

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}

}
