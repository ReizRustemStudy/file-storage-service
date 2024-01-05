package com.javatechie.filestorageservice.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javatechie.filestorageservice.entity.ImageData;
import com.javatechie.filestorageservice.repository.StorageRepository;
import com.javatechie.filestorageservice.util.ImageUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageService {
	
	private final StorageRepository repository;
	
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
 }
