package com.wechat.core.payment.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 退款出资账户枚举。
 */
public enum RefundFundsAccount {
    /**
     * 未结算资金。
     */
    @SerializedName("UNSETTLED")
    UNSETTLED,
    /**
     * 可用余额账户。
     */
    @SerializedName("AVAILABLE")
    AVAILABLE,
    /**
     * 不可用余额账户。
     */
    @SerializedName("UNAVAILABLE")
    UNAVAILABLE,
    /**
     * 运营账户。
     */
    @SerializedName("OPERATION")
    OPERATION,
    /**
     * 基本账户。
     */
    @SerializedName("BASIC")
    BASIC,
    /**
     * 数字人民币基本账户。
     */
    @SerializedName("ECNY_BASIC")
    ECNY_BASIC
}
