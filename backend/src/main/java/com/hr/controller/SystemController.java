package com.hr.controller;

import com.hr.common.R;
import com.hr.dto.SystemSettings;
import com.hr.service.SystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 系统管理控制器
 */
@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
@Tag(name = "系统管理", description = "系统设置等接口")
public class SystemController {

    private final SystemService systemService;

    @GetMapping("/settings")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "获取系统设置")
    public R<SystemSettings> getSystemSettings() {
        SystemSettings settings = systemService.getSystemSettings();
        return R.ok(settings);
    }

    @PutMapping("/settings")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "更新系统设置")
    public R<SystemSettings> updateSystemSettings(@RequestBody SystemSettings settings) {
        SystemSettings updatedSettings = systemService.updateSystemSettings(settings);
        return R.ok(updatedSettings);
    }
}
