package com.first.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileStreamServiceImpl implements  FileStreamService{

    public void writeFile(String message, MultipartFile multipartFile) throws IOException {

        /*        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(message);
        }catch (Exception e){
            throw new IOException(e);
        }*/
    }

    public String readFile(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String readLine;
            while ((readLine = reader.readLine()) != null) {
                log.info(readLine);
                builder.append(readLine);
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
        return String.valueOf(builder);
    }
}
