package com.first.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Document
public class AdminConfig {

    @Id
    private String id;
    private int maxLoginAttempts;
    private Set<String> adminList;
    private int maxAccountBlockedDays;
    private EmailConfig emailConfig;
    private AggregationConfig aggregationConfig;
}
