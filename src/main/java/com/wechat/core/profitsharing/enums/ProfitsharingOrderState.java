package com.wechat.core.profitsharing.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 分账单状态枚举。
 */
public enum ProfitsharingOrderState {
    /**
     * 处理中。
     * 分账请求已受理，但明细结果尚未全部落定。
     */
    @SerializedName("PROCESSING")
    PROCESSING,

    /**
     * 已完成。
     * 分账明细处理完成，需进一步查看明细结果是否全部成功。
     */
    @SerializedName("FINISHED")
    FINISHED,

    /**
     * 已关闭。
     * 分账流程已关闭，通常表示后续不会再继续处理。
     */
    @SerializedName("CLOSED")
    CLOSED
}
