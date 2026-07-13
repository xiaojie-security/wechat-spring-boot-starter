package com.wechat.core.payment.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 资金账单账户类型枚举。
 */
public enum FundFlowBillAccountType {
    /**
     * 基本账户。
     */
    @SerializedName("BASIC")
    BASIC,
    /**
     * 运营账户。
     */
    @SerializedName("OPERATION")
    OPERATION,
    /**
     * 手续费账户。
     */
    @SerializedName("FEES")
    FEES
}
