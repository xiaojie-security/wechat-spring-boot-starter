package com.wechat.core.oauth2.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取访问令牌请求参数类
 * 通过授权码code换取access_token
 * 
 * @author YourName
 * @version 1.0
 * @since 2026-07-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenRequest {
    
    /**
     * 应用唯一标识（必填）
     * 在微信开放平台提交应用审核通过后获得
     */
    private String appid;
    
    /**
     * 应用密钥（必填）
     * AppSecret，在微信开放平台提交应用审核通过后获得
     */
    private String secret;
    
    /**
     * 授权临时票据（必填）
     * 填写第一步获取的code参数
     */
    private String code;
    
    /**
     * 授权类型（必填）
     * 固定值：authorization_code
     */
    @Builder.Default
    private String grantType = "authorization_code";
}
