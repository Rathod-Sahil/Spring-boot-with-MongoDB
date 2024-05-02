package com.first.service;

import com.first.decorator.EmailDto;
import com.samskivert.mustache.Mustache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MustacheHelper {

    private final TemplateResolveService templateResolveService;
    public String setMessageContent(String templateName, EmailDto emailDto) {
            String content = templateResolveService.getResolvedTemplate(templateName);
            return Mustache.compiler().defaultValue("").escapeHTML(false).compile(content).execute(emailDto);
    }
}
