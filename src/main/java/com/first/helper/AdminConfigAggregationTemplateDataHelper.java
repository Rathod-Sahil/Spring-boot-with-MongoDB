package com.first.helper;

import com.first.model.AdminConfig;
import com.first.model.AggregationConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

@Slf4j
public class AdminConfigAggregationTemplateDataHelper {

    private static final AggregationConfig DEFAULT_AGGREGATION_CONFIG = new AggregationConfig();
    private static AggregationConfig getAggregationConfig(AdminConfig adminConfig) {
        AggregationConfig aggregationConfig = adminConfig.getAggregationConfig();
        return ObjectUtils.isNotEmpty(aggregationConfig) ? aggregationConfig : DEFAULT_AGGREGATION_CONFIG;
    }

    public static String getFilterRegistrationDate(AdminConfig adminConfig) {
        return getAggregationConfig(adminConfig).getFilterRegistrationDate();
//        return ObjectUtils.isNotEmpty(filterRegistrationDate) ? filterRegistrationDate : DEFAULT_AGGREGATION_CONFIG.getFilterRegistrationDate();
    }
}
