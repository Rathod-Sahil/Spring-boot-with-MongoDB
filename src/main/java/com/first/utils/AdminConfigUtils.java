package com.first.utils;

import com.first.model.AdminConfig;
import com.first.repository.AdminConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AdminConfigUtils {
    private final AdminConfigRepository adminConfigRepository;

    public AdminConfig getAdminConfig(){
        List<AdminConfig> adminConfigList = adminConfigRepository.findAll();
        if(adminConfigList.isEmpty()){
            AdminConfig adminConfig = new AdminConfig();
            adminConfig.setMaxLoginAttempts(3);
            adminConfig.setMaxAccountBlockedDays(1);
            return adminConfig;
        }
        return adminConfigList.get(0);
    }
}
