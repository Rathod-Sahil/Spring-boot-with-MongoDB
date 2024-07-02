package com.first.utils;

import com.first.exception.FileNotFoundException;
import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FileLoader {

    public static String loadFile(String file) {
        try {
            return Resources.toString(Resources.getResource(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileNotFoundException(e.toString());
        }
    }
}
