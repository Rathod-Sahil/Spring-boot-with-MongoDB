package com.first.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStreamService {

    void writeFile(String message, MultipartFile multipartFile) throws IOException;
    String readFile(MultipartFile multipartFile) throws IOException;

}
