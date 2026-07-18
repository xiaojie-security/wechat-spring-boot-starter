package com.wechat.core.oauth2.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小程序 code2session 响应结果。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XcxCode2SessionResponse {

    /**
     * 用户唯一标识。
     */
    private String openid;

    /**
     * 会话密钥。
     */
    @SerializedName("session_key")
    private String sessionKey;

    /**
     * 用户在开放平台的唯一标识。
     */
    private String unionid;

    /**
     * 错误码。
     */
    private Integer errcode;

    /**
     * 错误信息。
     */
    private String errmsg;
}
