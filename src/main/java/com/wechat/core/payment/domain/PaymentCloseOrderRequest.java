package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;

/**
 * 关闭订单请求参数。
 */
public class PaymentCloseOrderRequest {
    /**
     * 商户号。
     * 关闭订单接口要求放在请求体中。
     */
    @SerializedName("mchid")
    public String mchid;
}
