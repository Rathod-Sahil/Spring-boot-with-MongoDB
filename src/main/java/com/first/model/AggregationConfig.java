package com.first.model;

import com.first.constant.TemplateConstants;
import com.first.utils.FileLoader;
import lombok.Data;

@Data
public class AggregationConfig {

    String filterRegistrationDate;
    public AggregationConfig(){
        filterRegistrationDate = FileLoader.loadFile(TemplateConstants.REGISTRATION_DATE);
    }
}
