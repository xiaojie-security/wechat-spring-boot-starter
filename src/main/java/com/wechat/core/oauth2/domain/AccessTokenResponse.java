package com.wechat.core.oauth2.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取访问令牌响应类
 * 用于封装通过code换取access_token接口的返回数据
 * 
 * @author YourName
 * @version 1.0
 * @since 2026-07-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponse {
    
    /**
     * 接口调用凭证
     */
    @SerializedName("access_token")
    private String accessToken;
    
    /**
     * access_token接口调用凭证超时时间，单位（秒）
     */
    @SerializedName("expires_in")
    private Integer expiresIn;
    
    /**
     * 用户刷新access_token
     * 用于在access_token过期后获取新的access_token
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
    
    /**
     * 用户统一标识
     * 当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段
     */
    private String unionid;
}
