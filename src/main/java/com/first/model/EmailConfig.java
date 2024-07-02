package com.first.model;

import com.first.constant.TemplateConstants;
import com.first.utils.FileLoader;
import lombok.Data;

@Data
public class EmailConfig {

    TemplateModel otpTemplate;
    TemplateModel emailTemplate;
    TemplateModel userRegisterTemplate;

    public EmailConfig() {
        otpTemplate = new TemplateModel("OTP for Password change", FileLoader.loadFile(TemplateConstants.OTP));
        emailTemplate = new TemplateModel("Registration",FileLoader.loadFile(TemplateConstants.EMAIL));
        userRegisterTemplate = new TemplateModel("Registration",FileLoader.loadFile(TemplateConstants.USER_REGISTRATION));
    }
}
