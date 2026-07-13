package com.wechat.core.payment.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 异常退款类型枚举。
 */
public enum AbnormalRefundType {
    /**
     * 退回至用户银行卡。
     */
    @SerializedName("USER_BANK_CARD")
    USER_BANK_CARD,
    /**
     * 退回至商户银行卡。
     */
    @SerializedName("MERCHANT_BANK_CARD")
    MERCHANT_BANK_CARD
}
