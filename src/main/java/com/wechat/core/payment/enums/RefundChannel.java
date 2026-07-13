package com.wechat.core.payment.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 退款渠道枚举。
 */
public enum RefundChannel {
    /**
     * 原路退回。
     */
    @SerializedName("ORIGINAL")
    ORIGINAL,
    /**
     * 退回到可用余额。
     */
    @SerializedName("BALANCE")
    BALANCE,
    /**
     * 退回到其他余额账户。
     */
    @SerializedName("OTHER_BALANCE")
    OTHER_BALANCE,
    /**
     * 退回到其他银行卡。
     */
    @SerializedName("OTHER_BANKCARD")
    OTHER_BANKCARD
}
