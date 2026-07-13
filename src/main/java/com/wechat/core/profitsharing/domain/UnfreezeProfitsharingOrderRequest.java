package com.wechat.core.profitsharing.domain;

import com.google.gson.annotations.SerializedName;

/**
 * 解冻剩余资金请求参数。
 */
public class UnfreezeProfitsharingOrderRequest {
    /**
     * 微信支付订单号。
     */
    @SerializedName("transaction_id")
    public String transactionId;

    /**
     * 商户分账单号。
     */
    @SerializedName("out_order_no")
    public String outOrderNo;

    /**
     * 解冻原因描述。
     */
    @SerializedName("description")
    public String description;
}
