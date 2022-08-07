package com.gregpalacios.files.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.gregpalacios.files.dto.FileDTO;

@Component
public class FileUtil {

	public static String createFile(byte[] archivoByte, FileDTO fileDTO) throws IOException {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String fileNameUniq = "file-" + timestamp.getTime() + "." + fileDTO.getFileFormat();
		String filePath = fileDTO.getFilePath() + fileNameUniq;

		Path path = Paths.get(filePath);
		Files.createFile(path);

		OutputStream out = new FileOutputStream(filePath);
		out.write(archivoByte);
		out.close();

		return fileNameUniq;
	}

	public static String createFileFromBase64(FileDTO fileDTO) throws IOException {

		byte[] archivoBytes = Base64.getDecoder().decode(fileDTO.getFileContentBase64());

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String fileNameUniq = "file-" + timestamp.getTime() + "." + fileDTO.getFileFormat();
		String filePath = fileDTO.getFilePath() + fileNameUniq;

		File file = new File(filePath);

		OutputStream out = new FileOutputStream(file);
		out.write(archivoBytes);
		out.close();

		return fileNameUniq;
	}
}
