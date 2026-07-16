package com.wechat.core.oauth2.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 校验网站授权 access_token 响应类
 *
 * @author YourName
 * @version 1.0
 * @since 2026-07-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenValidateResponse {

    /**
     * 错误码，成功时为 0
     */
    @SerializedName("errcode")
    private Integer errcode;

    /**
     * 错误信息，成功时为 ok
     */
    @SerializedName("errmsg")
    private String errmsg;
}
