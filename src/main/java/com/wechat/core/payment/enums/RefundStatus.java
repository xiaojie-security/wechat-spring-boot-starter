package com.wechat.core.payment.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 退款状态枚举。
 */
public enum RefundStatus {
    /**
     * 退款成功。
     */
    @SerializedName("SUCCESS")
    SUCCESS,
    /**
     * 退款关闭。
     */
    @SerializedName("CLOSED")
    CLOSED,
    /**
     * 退款处理中。
     */
    @SerializedName("PROCESSING")
    PROCESSING,
    /**
     * 退款异常。
     * 通常需要商户进一步处理异常退款。
     */
    @SerializedName("ABNORMAL")
    ABNORMAL
}
