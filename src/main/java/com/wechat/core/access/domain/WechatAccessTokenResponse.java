package com.wechat.core.access.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信稳定版接口调用凭据响应。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatAccessTokenResponse {

    /**
     * 接口调用凭据。
     */
    @SerializedName("access_token")
    private String accessToken;

    /**
     * 凭据有效时间，单位为秒。
     */
    @SerializedName("expires_in")
    private Integer expiresIn;

    /**
     * 微信错误码。
     */
    private Integer errcode;

    /**
     * 微信错误信息。
     */
    private String errmsg;
}
