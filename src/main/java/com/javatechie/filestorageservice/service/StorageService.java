package com.javatechie.filestorageservice.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javatechie.filestorageservice.entity.FileData;
import com.javatechie.filestorageservice.entity.ImageData;
import com.javatechie.filestorageservice.repository.FileDataRepository;
import com.javatechie.filestorageservice.repository.StorageRepository;
import com.javatechie.filestorageservice.util.ImageUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageService {
	
	private static final String FOLDER_PATH = "C:/Users/reizr/AppData/Local/file-upload-service/files/";
	
	private final StorageRepository repository;
	private final FileDataRepository fileDataRepository;
	
	
	public String uploadImage(MultipartFile file) throws IOException {
		ImageData imageData = repository.save(ImageData.builder()
				.name(file.getOriginalFilename())
				.type(file.getContentType())
				.imageData(ImageUtils.compressImage(file.getBytes()))
				.build());
		if(imageData != null) {
			return "file uploaded successfully: " + file.getOriginalFilename();
		}
		return null;
	}
	
	public byte[] downloadImage(String fileName) {
		Optional<ImageData> dbImageData = repository.findByName(fileName);
		byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
		return images;
	}
	
	public String uploadImageToFileSystem(MultipartFile file) throws IOException {
		String filePath = FOLDER_PATH + file.getOriginalFilename();
		
		FileData fileData = fileDataRepository.save(FileData.builder()
				.name(file.getOriginalFilename())
				.type(file.getContentType())
				.filePath(filePath)
				.build());
		file.transferTo(new File(filePath));
		if(fileData != null) {
			return "file uploaded successfully: " + filePath;
		}
		return null;
	}
	
	public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
		Optional<FileData> fileData = fileDataRepository.findByName(fileName);
		String filePath = fileData.get().getFilePath();		
		byte[] images = Files.readAllBytes(new File(filePath).toPath());
		return images;
	}
 }
