package com.wechat.core.transfer.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 转账单状态枚举。
 * 用于描述微信支付侧当前对这笔转账的处理进度。
 */
public enum TransferBillStatus {
    /**
     * 已受理。
     * 微信支付已接收请求，但后续结果仍需继续观察。
     */
    @SerializedName("ACCEPTED")
    ACCEPTED,

    /**
     * 处理中。
     * 转账流程已进入处理中间态，尚未给出最终结果。
     */
    @SerializedName("PROCESSING")
    PROCESSING,

    /**
     * 等待用户确认收款。
     * 通常需要借助 package_info 拉起用户确认收款页面。
     */
    @SerializedName("WAIT_USER_CONFIRM")
    WAIT_USER_CONFIRM,

    /**
     * 转账中。
     * 微信支付正在继续执行打款流程，结果尚未最终落定。
     */
    @SerializedName("TRANSFERING")
    TRANSFERING,

    /**
     * 转账成功。
     * 表示这笔商家转账已经完成。
     */
    @SerializedName("SUCCESS")
    SUCCESS,

    /**
     * 撤销中。
     * 表示该转账单正在进行撤销或关闭相关处理。
     */
    @SerializedName("CANCELING")
    CANCELING
}
