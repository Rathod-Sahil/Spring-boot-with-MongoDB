package com.first.helper;

import com.samskivert.mustache.Mustache;

public class MustacheHelper {

    public static String setTemplateContent(String content, Object object) {
        return Mustache.compiler().defaultValue("").escapeHTML(false).compile(content).execute(object);
    }
}
