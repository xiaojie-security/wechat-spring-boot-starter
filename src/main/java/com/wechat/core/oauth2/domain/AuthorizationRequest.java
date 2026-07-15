package com.wechat.core.oauth2.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 网站应用授权链接请求参数实体类
 * 用于构建OAuth2.0授权请求链接，支持网页应用获取用户授权
 * 
 * @author YourName
 * @version 1.0
 * @since 2026-07-15
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorizationRequest {

    /**
     * 应用唯一标识（必填）
     * 在微信开放平台提交应用审核通过后获得
     */
    private String appid;
    
    /**
     * 回调地址（必填）
     * 用户授权后重定向的URL，需要使用URLEncode进行编码
     */
    private String redirectUri;
    
    /**
     * 响应类型（必填）
     * 固定值：code
     * 表示授权流程为授权码模式（Authorization Code Grant）
     */
    private String responseType = "code";
    
    /**
     * 应用授权作用域（必填）
     * 网页应用目前仅填写 snsapi_login
     * 多个作用域用逗号（,）分隔
     */
    private String scope = "snsapi_login";
    
    /**
     * 状态参数（可选）
     * 用于保持请求和回调的状态，授权请求后原样带回给第三方
     * 可用于防止CSRF攻击（跨站请求伪造攻击）
     * 建议设置为随机数加session进行校验
     */
    private String state;
    
    /**
     * 界面语言（可选）
     * 支持 cn（中文简体）与 en（英文）
     * 默认为 cn
     */
    private String lang = "cn";

    public AuthorizationRequest(String redirectUri, String state) {
        this.redirectUri = redirectUri;
        this.state = state;
    }
}
