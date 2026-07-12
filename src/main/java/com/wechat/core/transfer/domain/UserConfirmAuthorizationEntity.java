package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.transfer.enums.AuthorizationState;

/**
 * 免确认收款授权详情实体。
 * 用于承接按商户授权单号查询授权结果以及解除授权接口返回的数据。
 */
public class UserConfirmAuthorizationEntity {
    /**
     * 商户授权单号。
     * 对应商户侧生成的唯一授权业务单号。
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
     * 授权页面展示名称。
     * 通常用于前端向用户展示本次授权主体。
     */
    @SerializedName("user_display_name")
    public String userDisplayName;

    /**
     * 微信支付授权单号。
     * 由微信支付生成，用于标识一次正式授权记录。
     */
    @SerializedName("authorization_id")
    public String authorizationId;

    /**
     * 当前授权状态。
     * 用于判断授权是否生效、待用户确认或已经关闭。
     */
    @SerializedName("state")
    public AuthorizationState state;

    /**
     * 授权生效时间。
     * 当状态为 TAKING_EFFECT 时通常可以关注该字段。
     */
    @SerializedName("authorize_time")
    public String authorizeTime;

    /**
     * 授权关闭信息。
     * 当授权已关闭时，可从中看到关闭时间和关闭原因。
     */
    @SerializedName("close_info")
    public UserConfirmAuthorizationCloseInfo closeInfo;

    /**
     * 转账场景 ID。
     * 表示该授权与哪一种商家转账业务场景绑定。
     */
    @SerializedName("transfer_scene_id")
    public String transferSceneId;

    /**
     * 用户收款感知信息。
     * 通常用于展示给用户的业务说明文案。
     */
    @SerializedName("user_recv_perception")
    public String userRecvPerception;

    /**
     * 授权记录创建时间。
     */
    @SerializedName("create_time")
    public String createTime;

    /**
     * 微信授权拉起参数。
     * 当授权状态为 WAIT_USER_CONFIRM 且请求要求返回展示信息时，通常会返回该字段。
     */
    @SerializedName("package_info")
    public String packageInfo;
}
