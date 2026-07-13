package com.wechat.core.payment.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 通过微信支付订单号查询订单请求参数。
 */
public class QueryOrderByTransactionIdRequest {
    /**
     * 微信支付订单号。
     */
    @SerializedName("transaction_id")
    @Expose(serialize = false)
    public String transactionId;

    /**
     * 商户号。
     * 如果未传，则服务层会自动回退使用已初始化的商户号。
     */
    @SerializedName("mchid")
    @Expose(serialize = false)
    public String mchid;
}
