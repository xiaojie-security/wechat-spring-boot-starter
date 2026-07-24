package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;

/**
 * 发起免确认收款授权时的用户端场景信息。
 */
public class UserConfirmAuthorizationSceneInfo {
    /**
     * 用户端实际 IP 地址。
     */
    @SerializedName("client_ip")
    public String clientIp;

    /**
     * 用户设备 ID。
     */
    @SerializedName("device_id")
    public String deviceId;

    /**
     * 用户设备类型。
     */
    @SerializedName("device_type")
    public String deviceType;
}
