package com.wechat.core.oauth2.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 刷新访问令牌请求参数类
 * 用于通过refresh_token换取新的access_token
 * 
 * @author YourName
 * @version 1.0
 * @since 2026-07-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {
    
    /**
     * 应用唯一标识（必填）
     */
    private String appid;
    
    /**
     * 授权类型（必填）
     * 固定值：refresh_token
     */
    @Builder.Default
    private String grantType = "refresh_token";
    
    /**
     * 刷新令牌（必填）
     * 通过access_token获取到的refresh_token参数
     */
    private String refreshToken;

    public RefreshTokenRequest(String appid, String refreshToken) {
        this.appid = appid;
        this.refreshToken = refreshToken;
    }
}
