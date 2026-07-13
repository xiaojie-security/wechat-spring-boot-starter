package com.wechat.core.payment.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 交易账单类型枚举。
 */
public enum BillType {
    /**
     * 全部交易类型。
     */
    @SerializedName("ALL")
    ALL,
    /**
     * 仅成功支付账单。
     */
    @SerializedName("SUCCESS")
    SUCCESS,
    /**
     * 仅退款账单。
     */
    @SerializedName("REFUND")
    REFUND
}
