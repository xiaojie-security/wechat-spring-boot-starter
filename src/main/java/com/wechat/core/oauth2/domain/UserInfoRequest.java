package com.wechat.core.oauth2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取用户信息请求参数类
 * 用于请求获取授权用户的详细信息
 * 
 * @author YourName
 * @version 1.0
 * @since 2026-07-15
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoRequest {
    
    /**
     * 调用凭证（必填）
     * 通过access_token接口获取的接口调用凭证
     */
    private String accessToken;
    
    /**
     * 用户唯一标识（必填）
     * 普通用户的标识，对当前开发者账号唯一
     */
    private String openid;
    
    /**
     * 国家地区语言版本（可选）
     * zh_CN 简体，zh_TW 繁体，en 英语
     * 默认为 en
     */
    private String lang = "en";

    public UserInfoRequest(String accessToken, String openid) {
        this.accessToken = accessToken;
        this.openid = openid;
    }
}
