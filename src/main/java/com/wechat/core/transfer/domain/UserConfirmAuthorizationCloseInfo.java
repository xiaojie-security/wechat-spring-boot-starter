package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.transfer.enums.CloseReason;

/**
 * 免确认收款授权关闭信息。
 * 用于描述授权被关闭时的时间点和关闭原因。
 */
public class UserConfirmAuthorizationCloseInfo {
    /**
     * 授权关闭时间。
     */
    @SerializedName("close_time")
    public String closeTime;

    /**
     * 授权关闭原因。
     * 可用于区分是商户主动解除、用户关闭还是风控等原因导致的关闭。
     */
    @SerializedName("close_reason")
    public CloseReason closeReason;
}
