package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;

/**
 * 发起免确认收款授权请求参数。
 */
public class UserConfirmAuthorizationRequest {
    /**
     * 商户侧授权单号。
     */
    @SerializedName("out_authorization_no")
    public String outAuthorizationNo;

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
     * 用户授权后免确认收款的转账场景 ID。
     */
    @SerializedName("transfer_scene_id")
    public String transferSceneId;

    /**
     * 授权详情中展示的用户名称。
     */
    @SerializedName("user_display_name")
    public String userDisplayName;

    /**
     * 用户收款时感知到的收款原因。
     */
    @SerializedName("user_recv_perception")
    public String userRecvPerception;

    /**
     * 授权结果通知地址。未传时使用商户配置中的免确认收款授权结果通知地址。
     */
    @SerializedName("authorization_notify_url")
    public String authorizationNotifyUrl;

    /**
     * 用户端场景信息。
     */
    @SerializedName("scene_info")
    public UserConfirmAuthorizationSceneInfo sceneInfo;
}
