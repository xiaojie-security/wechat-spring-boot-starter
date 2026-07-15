package com.wechat.core.oauth2.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 刷新访问令牌响应类
 * 用于封装刷新access_token接口的返回数据
 * 
 * @author YourName
 * @version 1.0
 * @since 2026-07-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResponse {
    
    /**
     * 新的接口调用凭证
     */
    @SerializedName("access_token")
    private String accessToken;
    
    /**
     * access_token接口调用凭证超时时间，单位（秒）
     */
    @SerializedName("expires_in")
    private Integer expiresIn;
    
    /**
     * 新的刷新令牌
     * 用于下次刷新access_token
     */
    @SerializedName("refresh_token")
    private String refreshToken;
    
    /**
     * 授权用户唯一标识
     */
    private String openid;
    
    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    private String scope;
}
