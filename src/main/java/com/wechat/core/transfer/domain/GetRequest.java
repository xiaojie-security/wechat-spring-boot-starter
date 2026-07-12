package com.wechat.core.transfer.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 商户单号查询授权结果请求参数。
 * 用于封装按商户授权单号查询免确认收款授权结果时需要的路径参数和查询参数。
 */
public class GetRequest {
    /**
     * 商户授权单号。
     * 用于唯一标识商户侧发起的一次免确认收款授权申请。
     */
    @SerializedName("out_authorization_no")
    @Expose(serialize = false)
    public String outAuthorizationNo;

    /**
     * 是否需要返回授权拉起参数。
     * 当授权状态为 WAIT_USER_CONFIRM 时，可通过该字段控制是否返回 package_info。
     */
    @SerializedName("is_display_authorization")
    @Expose(serialize = false)
    public Boolean isDisplayAuthorization;
}
