package com.first.controller;

import com.first.annotations.Access;
import com.first.constant.ResponseConstants;
import com.first.decorator.DataResponse;
import com.first.enums.Role;
import com.first.helper.Response;
import com.first.service.FileStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/file")
@RequiredArgsConstructor
@RestController
public class FileStreamController {

    private final FileStreamService fileStreamService;

    @Access(role = Role.ADMIN)
    @PostMapping
    public DataResponse<Object> writeFile(@RequestPart String message, @RequestPart MultipartFile file) throws IOException {
        fileStreamService.writeFile(message, file);
        return new DataResponse<>(null, Response.getOkResponse(ResponseConstants.WRITE_FILE));
    }

    @Access(role = {Role.ADMIN, Role.USER})
    @GetMapping
    private DataResponse<String> readFile(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return new DataResponse<>(fileStreamService.readFile(multipartFile), Response.getOkResponse(ResponseConstants.READ_FILE));
    }
}
