package com.wechat.core.payment.enums;

import com.google.gson.annotations.SerializedName;

public enum FundFlowBillAccountType {
    @SerializedName("BASIC")
    BASIC,
    @SerializedName("OPERATION")
    OPERATION,
    @SerializedName("FEES")
    FEES
}
