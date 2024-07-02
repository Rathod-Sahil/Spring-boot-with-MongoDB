package com.first.helper;

import com.first.model.AdminConfig;
import com.first.model.EmailConfig;
import com.first.model.TemplateModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

@Slf4j
public class AdminConfigEmailTemplateDataHelper {
    public static final EmailConfig DEFAULT_EMAIL_CONFIG = new EmailConfig();

    private static EmailConfig getEmailConfig(AdminConfig adminConfig) {
        EmailConfig emailConfig = adminConfig.getEmailConfig();
        return ObjectUtils.isNotEmpty(emailConfig) ? emailConfig : DEFAULT_EMAIL_CONFIG;
    }

    public static TemplateModel getOtpTemplate(AdminConfig adminConfig) {
        TemplateModel otpTemplate = getEmailConfig(adminConfig).getOtpTemplate();
        return getTemplateModel(otpTemplate, DEFAULT_EMAIL_CONFIG.getOtpTemplate());
//        return ObjectUtils.isNotEmpty(getEmailConfig(adminConfig).getOtpTemplate()) ? getEmailConfig(adminConfig).getOtpTemplate() : DEFAULT_EMAIL_CONFIG.getOtpTemplate();
    }

    public static TemplateModel getEmailTemplate(AdminConfig adminConfig) {
        TemplateModel emailTemplate = getEmailConfig(adminConfig).getEmailTemplate();
        return getTemplateModel(emailTemplate, DEFAULT_EMAIL_CONFIG.getEmailTemplate());
//        return ObjectUtils.isNotEmpty(emailTemplate) ? emailTemplate : DEFAULT_EMAIL_CONFIG.getEmailTemplate();
    }

    public static TemplateModel getUserRegisterTemplate(AdminConfig adminConfig) {
        TemplateModel userRegisterTemplate = getEmailConfig(adminConfig).getUserRegisterTemplate();
        return getTemplateModel(userRegisterTemplate, DEFAULT_EMAIL_CONFIG.getUserRegisterTemplate());
//        return ObjectUtils.isNotEmpty(userRegisterTemplate) ? userRegisterTemplate : DEFAULT_EMAIL_CONFIG.getUserRegisterTemplate();
    }

    private static TemplateModel getTemplateModel(TemplateModel templateModel, TemplateModel defaultEmailConfig) {
        if (templateModel == null || templateModel.getSubject() == null || templateModel.getBody() == null) {
            return defaultEmailConfig;
        } else {
            return templateModel;
        }
    }

}
