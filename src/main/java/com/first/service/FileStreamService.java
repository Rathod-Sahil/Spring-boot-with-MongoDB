package com.first.service;

import com.google.common.io.Resources;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileStreamService {

    public void writeFile(String message, MultipartFile multipartFile) throws IOException {

        //        File file = new File(fileName);
//        try (FileWriter fileWriter = new FileWriter(file)) {
//            fileWriter.write(message);
//        }catch (Exception e){
//            throw new IOException(e);
//        }
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
