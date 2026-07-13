package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.transfer.enums.AuthorizationState;
import com.wechat.core.transfer.enums.CloseReason;

/**
 * 免确认收款授权结果通知解密后的业务内容实体。
 * 用于承接微信支付授权结果通知中 resource 解密后的业务数据。
 */
public class AutoApprovalResultNotifyEntity {
    /**
     * 商户授权单号。
     */
    @SerializedName("out_authorization_no")
    public String outAuthorizationNo;

    /**
     * 微信支付授权单号。
     */
    @SerializedName("authorization_id")
    public String authorizationId;

    /**
     * 商户应用 AppID。
     */
    @SerializedName("appid")
    public String appid;

    /**
     * 收款用户 OpenID。
     */
    @SerializedName("openid")
    public String openid;

    /**
     * 授权页展示名称。
     */
    @SerializedName("user_display_name")
    public String userDisplayName;

    /**
     * 当前授权状态。
     */
    @SerializedName("state")
    public AuthorizationState state;

    /**
     * 授权生效时间。
     */
    @SerializedName("authorize_time")
    public String authorizeTime;

    /**
     * 授权关闭时间。
     */
    @SerializedName("close_time")
    public String closeTime;

    /**
     * 授权关闭原因。
     */
    @SerializedName("close_reason")
    public CloseReason closeReason;
}
