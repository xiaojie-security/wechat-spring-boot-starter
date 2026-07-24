package com.wechat.core.xcx.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 微信小程序二维码对应的小程序版本。
 */
public enum WechatXcxEnvVersion {

    /**
     * 线上正式版。
     */
    @SerializedName("release")
    RELEASE,

    /**
     * 体验版。
     */
    @SerializedName("trial")
    TRIAL,

    /**
     * 开发版。
     */
    @SerializedName("develop")
    DEVELOP
}
