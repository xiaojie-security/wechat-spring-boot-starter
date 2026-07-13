package com.wechat.core.payment.enums;

import com.google.gson.annotations.SerializedName;

public enum BillType {
    @SerializedName("ALL")
    ALL,
    @SerializedName("SUCCESS")
    SUCCESS,
    @SerializedName("REFUND")
    REFUND
}
