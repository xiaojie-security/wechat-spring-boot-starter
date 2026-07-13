package com.wechat.core.profitsharing.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 分账接收方处理结果枚举。
 */
public enum ProfitsharingReceiverResult {
    /**
     * 待处理。
     */
    @SerializedName("PENDING")
    PENDING,

    /**
     * 处理成功。
     */
    @SerializedName("SUCCESS")
    SUCCESS,

    /**
     * 已关闭。
     * 常见于该明细最终未成功执行。
     */
    @SerializedName("CLOSED")
    CLOSED
}
