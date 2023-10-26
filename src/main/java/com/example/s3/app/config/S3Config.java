package com.example.s3.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

	@Value("${aws.access-key}")
	private String accessKey;
	
	@Value("${aws.secret-key}")
	private String secretKey;

	@Value("${aws.region}")
	private Regions awsRegion;

	@Bean
	public AmazonS3 amazonS3() {
		AWSCredentials awsCredencial = new BasicAWSCredentials(accessKey, secretKey);
		//return new AmazonS3Client(awsCredencial);
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCredencial))
				.withRegion(awsRegion)
				.build();
	}
}
