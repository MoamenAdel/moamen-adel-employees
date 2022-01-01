package com.example.evolvice.util;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IUtilityService {

	public void processFile(MultipartFile file) throws IOException;
}
