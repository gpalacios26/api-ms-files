package com.gregpalacios.files.dto;

public class FileDTO {

	private String filePath;

	private String fileName;

	private String fileFormat;

	private String fileContentBase64;

	public FileDTO() {
	}

	public FileDTO(String filePath, String fileName, String fileFormat, String fileContentBase64) {
		this.filePath = filePath;
		this.fileName = fileName;
		this.fileFormat = fileFormat;
		this.fileContentBase64 = fileContentBase64;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getFileContentBase64() {
		return fileContentBase64;
	}

	public void setFileContentBase64(String fileContentBase64) {
		this.fileContentBase64 = fileContentBase64;
	}
}
