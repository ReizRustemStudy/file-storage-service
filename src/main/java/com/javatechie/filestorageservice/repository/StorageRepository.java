package com.javatechie.filestorageservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatechie.filestorageservice.entity.ImageData;

public interface StorageRepository extends JpaRepository<ImageData, Long> {
	Optional<ImageData> findByName(String fileName);
}
