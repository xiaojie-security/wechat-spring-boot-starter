package com.wechat.core.oauth2.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小程序 code2session 请求参数。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XcxCode2SessionRequest {

    /**
     * 小程序 appid。
     */
    private String appid;

    /**
     * 小程序 appSecret。
     */
    private String secret;

    /**
     * 小程序登录时获取的 code。
     */
    private String jsCode;

    /**
     * 授权类型，固定为 authorization_code。
     */
    @Builder.Default
    private String grantType = "authorization_code";
}
