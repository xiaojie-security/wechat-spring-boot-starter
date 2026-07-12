package com.wechat.core.transfer.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 免确认收款授权状态枚举。
 * 用于描述微信支付侧当前对该授权申请的处理结果。
 */
public enum AuthorizationState {
    /**
     * 授权已生效。
     * 表示后续符合条件的转账可以按免确认收款方式发起。
     */
    @SerializedName("TAKING_EFFECT")
    TAKING_EFFECT,

    /**
     * 授权已关闭。
     * 表示该授权记录已失效，不能再用于免确认收款。
     */
    @SerializedName("CLOSED")
    CLOSED,

    /**
     * 等待用户确认授权。
     * 通常需要前端拉起微信授权页面，由用户在微信侧完成确认。
     */
    @SerializedName("WAIT_USER_CONFIRM")
    WAIT_USER_CONFIRM
}
