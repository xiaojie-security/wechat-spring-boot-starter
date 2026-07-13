package com.wechat.core.profitsharing.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 分账接收方类型枚举。
 */
public enum ProfitsharingReceiverType {
    /**
     * 商户号。
     * 适用于将资金分给另一个微信支付商户。
     */
    @SerializedName("MERCHANT_ID")
    MERCHANT_ID,

    /**
     * 个人 OpenID。
     * 适用于将资金分给个人收款方。
     */
    @SerializedName("PERSONAL_OPENID")
    PERSONAL_OPENID
}
