package com.javatechie.filestorageservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatechie.filestorageservice.entity.FileData;

public interface FileDataRepository extends JpaRepository<FileData, Long> {
	Optional<FileData> findByName(String fileName);
}
