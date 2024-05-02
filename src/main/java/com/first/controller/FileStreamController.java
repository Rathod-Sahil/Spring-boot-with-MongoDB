package com.first.controller;

import com.first.decorator.DataResponse;
import com.first.decorator.FileDto;
import com.first.decorator.Response;
import com.first.service.FileStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequestMapping("/file")
@RequiredArgsConstructor
@RestController
public class FileStreamController {

    private final FileStreamService fileStreamService;

    @PostMapping
    public DataResponse<Object> writeFile(@RequestPart String message,@RequestPart MultipartFile file) throws IOException {
        fileStreamService.writeFile(message, file);
        return new DataResponse<>(null,Response.getOkResponse("File write successfully"));
    }

    @GetMapping
    private DataResponse<String> readFile(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return new DataResponse<>(fileStreamService.readFile(multipartFile),Response.getOkResponse("File read successfully"));
    }
}
