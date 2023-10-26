package com.example.s3.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class S3Services {

	
	private AmazonS3 amazonS3;
	
	
	private final String bucketName;
	
	@Autowired
	public S3Services(AmazonS3 amazonS3, @Value("${s3.bucket-name}")String bucketName) {
		this.amazonS3 = amazonS3;
		this.bucketName = bucketName;
	}
	
	public void uploadFile(String key, MultipartFile file) {
		try {
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(file.getSize());
			amazonS3.putObject(new PutObjectRequest(bucketName, key, file.getInputStream(), objectMetadata));
		} catch (Exception e) {
			log.error("Failed to upload file. "+e.getMessage());
		}
	}
	
	
	public List<String> getObjectFromS3() {
		
		ObjectListing objectList = amazonS3.listObjects(bucketName);
		
		List<String> objects = new ArrayList<>();
		
		for(S3ObjectSummary objectSummary : objectList.getObjectSummaries()) {
			objects.add(objectSummary.getKey());
		}
		
		return objects;
	}
	
	
}
