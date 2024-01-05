package com.javatechie.filestorageservice.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.javatechie.filestorageservice.service.StorageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class StorageController {

	private final StorageService service;
	
	@PostMapping
	ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
		String uploadImage = service.uploadImage(file);
		return ResponseEntity.ok(uploadImage);
	}
	
	@GetMapping("/{fileName}")
	ResponseEntity<?> downloadImage(@PathVariable String fileName) {
		byte[] imageData = service.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.IMAGE_PNG)
				.body(imageData);
	}
	
	@PostMapping("/fileSystem")
	ResponseEntity<?> uploadImageToFileSystem(@RequestParam("image") MultipartFile file) throws IOException {
		String uploadImage = service.uploadImageToFileSystem(file);
		return ResponseEntity.ok(uploadImage);
	}
	
	@GetMapping("/fileSystem/{fileName}")
	ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
		byte[] imageData = service.downloadImageFromFileSystem(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.IMAGE_PNG)
				.body(imageData);
	}
}
