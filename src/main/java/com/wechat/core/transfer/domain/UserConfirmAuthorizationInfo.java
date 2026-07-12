package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;

/**
 * 免确认收款授权信息。
 * 用于在首次转账时附带授权申请，让用户在收款确认过程中同步完成免确认授权。
 */
public class UserConfirmAuthorizationInfo {
    /**
     * 授权页面展示给用户的名称。
     * 通常用于告诉收款用户是谁在向其申请免确认收款授权。
     */
    @SerializedName("user_display_name")
    public String userDisplayName;

    /**
     * 商户授权单号。
     * 商户侧生成的唯一授权业务单号，用于后续幂等、通知和授权结果关联。
     */
    @SerializedName("out_authorization_no")
    public String outAuthorizationNo;

    /**
     * 授权结果通知地址。
     * 微信支付在用户完成或拒绝授权后，会将结果通知到该地址。
     */
    @SerializedName("authorization_notify_url")
    public String authorizationNotifyUrl;
}
