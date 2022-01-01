package com.example.evolvice.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.evolvice.util.IUtilityService;

@RestController
@RequestMapping("files")
public class RestApiController {
	
	@Autowired
	private IUtilityService utilityService;

	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping
	public ResponseEntity<Void> addFile(@RequestBody() MultipartFile file) throws IOException {
		utilityService.processFile(file);

		return ResponseEntity.ok().build();
	}
}
