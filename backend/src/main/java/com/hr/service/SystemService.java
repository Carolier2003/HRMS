package com.hr.service;

import com.hr.dto.SystemSettings;
import org.springframework.stereotype.Service;

/**
 * 系统服务
 */
@Service
public class SystemService {

    /**
     * 获取系统设置
     */
    public SystemSettings getSystemSettings() {
        // 这里可以从数据库或配置文件读取，暂时返回默认值
        SystemSettings settings = new SystemSettings();
        settings.setSystemName("HR管理系统");
        settings.setSystemVersion("1.0.0");
        settings.setSystemDescription("企业人力资源管理系统");
        settings.setPasswordMinLength(6);
        settings.setSessionTimeout(120);
        settings.setEnableTwoFactor(false);
        settings.setEnableLoginLog(true);
        settings.setSmtpHost("smtp.example.com");
        settings.setSmtpPort(587);
        settings.setSmtpUsername("admin@example.com");
        settings.setSmtpPassword("");
        settings.setEnableEmailNotification(false);
        settings.setBackupInterval(7);
        settings.setMaxBackupFiles(10);
        settings.setEnableDataExport(true);
        return settings;
    }

    /**
     * 更新系统设置
     */
    public SystemSettings updateSystemSettings(SystemSettings settings) {
        // 这里可以保存到数据库或配置文件，暂时直接返回
        return settings;
    }
}
