package com.first.service;

import com.google.common.io.Resources;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
@Component
public class TemplateResolveService {

    public String getResolvedTemplate(String templateName){
        try {
            return Resources.toString(Resources.getResource(templateName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
