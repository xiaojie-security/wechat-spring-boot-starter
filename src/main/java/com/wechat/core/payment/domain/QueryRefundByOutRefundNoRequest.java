package com.wechat.core.payment.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 通过商户退款单号查询单笔退款请求参数。
 */
public class QueryRefundByOutRefundNoRequest {
    /**
     * 商户退款单号。
     */
    @SerializedName("out_refund_no")
    @Expose(serialize = false)
    public String outRefundNo;
}
