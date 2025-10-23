package com.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统设置DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemSettings {
    private String systemName;
    private String systemVersion;
    private String systemDescription;
    private Integer passwordMinLength;
    private Integer sessionTimeout;
    private Boolean enableTwoFactor;
    private Boolean enableLoginLog;
    private String smtpHost;
    private Integer smtpPort;
    private String smtpUsername;
    private String smtpPassword;
    private Boolean enableEmailNotification;
    private Integer backupInterval;
    private Integer maxBackupFiles;
    private Boolean enableDataExport;
}
