package com.example.s3.app.controllers;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.s3.app.services.S3Services;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class S3Controller {

	
	private S3Services s3Services;
	
	
	@Autowired
	public S3Controller(S3Services s3Services) {
		super();
		this.s3Services = s3Services;
	}



	@PostMapping("/uploadfile")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
		log.info("Uploading file : "+file.getOriginalFilename());
		s3Services.uploadFile(file.getOriginalFilename(), file);
		return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded successfull : "+ file.getOriginalFilename());
	}
	
	@GetMapping("/get-objects")
	public ResponseEntity<List<String>> getObjects(){
		List<String> objects = s3Services.getObjectFromS3();
		if(objects.size() < 1) {
			ResponseEntity.status(HttpStatus.FOUND).body("No object found");
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(objects);
	}
}
