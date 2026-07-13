package com.wechat.core.payment.enums;

import com.google.gson.annotations.SerializedName;

public enum AbnormalRefundType {
    @SerializedName("USER_BANK_CARD")
    USER_BANK_CARD,
    @SerializedName("MERCHANT_BANK_CARD")
    MERCHANT_BANK_CARD
}
