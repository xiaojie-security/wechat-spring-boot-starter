package com.wechat.core.transfer.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 免确认收款授权关闭原因枚举。
 * 用于描述授权被关闭时，微信支付返回的关闭原因类型。
 */
public enum CloseReason {
    /**
     * 商户通过接口主动关闭授权。
     */
    @SerializedName("CLOSE_VIA_MCH_API")
    CLOSE_VIA_MCH_API,

    /**
     * 用户主动关闭授权。
     */
    @SerializedName("USER_CLOSE")
    USER_CLOSE,

    /**
     * 用户长时间未确认，授权超时关闭。
     */
    @SerializedName("USER_OVERDUE_UNCONFIRMED")
    USER_OVERDUE_UNCONFIRMED,

    /**
     * 因风控原因关闭授权。
     */
    @SerializedName("TRANSFER_RISK")
    TRANSFER_RISK,

    /**
     * 用户账户状态异常，导致授权被关闭。
     */
    @SerializedName("USER_ACCOUNT_ABNORMAL")
    USER_ACCOUNT_ABNORMAL
}
