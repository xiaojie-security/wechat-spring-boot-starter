package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;

/**
 * 统一下单响应参数。
 * 主要返回预支付交易会话标识。
 */
public class PaymentPrepayResponse {
    /**
     * 预支付交易会话标识。
     * 前端或客户端需基于该值继续发起支付。
     */
    @SerializedName("prepay_id")
    public String prepayId;
}
