package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;

public class PaymentCloseOrderRequest {
    @SerializedName("mchid")
    public String mchid;
}
