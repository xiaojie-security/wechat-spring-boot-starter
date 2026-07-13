package com.wechat.core.transfer.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 解除免确认收款授权请求参数。
 */
public class CloseAuthorizationRequest {
    /**
     * 商户授权单号。
     * 用于唯一标识待解除的授权记录。
     */
    @SerializedName("out_authorization_no")
    @Expose(serialize = false)
    public String outAuthorizationNo;
}
