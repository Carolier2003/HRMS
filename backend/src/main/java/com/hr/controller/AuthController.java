package com.hr.controller;

import com.hr.common.R;
import com.hr.dto.LoginRequest;
import com.hr.dto.LoginResponse;
import com.hr.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证管理", description = "登录、刷新令牌等接口")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public R<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return R.ok(response);
    }
    
    @PostMapping("/refresh")
    @Operation(summary = "刷新令牌")
    public R<LoginResponse> refreshToken(@RequestBody String refreshToken) {
        try {
            // 移除JSON字符串的引号
            if (refreshToken.startsWith("\"") && refreshToken.endsWith("\"")) {
                refreshToken = refreshToken.substring(1, refreshToken.length() - 1);
            }
            LoginResponse response = authService.refreshToken(refreshToken);
            return R.ok(response);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
}

