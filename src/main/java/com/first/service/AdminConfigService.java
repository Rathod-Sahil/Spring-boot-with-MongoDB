package com.first.service;

import com.first.model.AdminConfig;
import com.first.repository.AdminConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminConfigService {

    private final AdminConfigRepository adminConfigRepository;

    public AdminConfig getAdminConfig(){
        List<AdminConfig> adminConfigList = adminConfigRepository.findAll();
        if(adminConfigList.isEmpty()){
            AdminConfig adminConfig1 = new AdminConfig();
            adminConfig1.setMaxLoginAttempts(3);
            return adminConfig1;
        }
        return adminConfigList.get(0);
    }
}
