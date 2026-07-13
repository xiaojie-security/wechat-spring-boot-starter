package com.wechat.core.payment.enums;

import com.google.gson.annotations.SerializedName;

public enum RefundFundsAccount {
    @SerializedName("UNSETTLED")
    UNSETTLED,
    @SerializedName("AVAILABLE")
    AVAILABLE,
    @SerializedName("UNAVAILABLE")
    UNAVAILABLE,
    @SerializedName("OPERATION")
    OPERATION,
    @SerializedName("BASIC")
    BASIC,
    @SerializedName("ECNY_BASIC")
    ECNY_BASIC
}
