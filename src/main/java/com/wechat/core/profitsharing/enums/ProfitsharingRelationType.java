package com.wechat.core.profitsharing.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 分账接收方关系类型枚举。
 */
public enum ProfitsharingRelationType {
    /**
     * 门店。
     */
    @SerializedName("STORE")
    STORE,

    /**
     * 员工。
     */
    @SerializedName("STAFF")
    STAFF,

    /**
     * 店主。
     */
    @SerializedName("STORE_OWNER")
    STORE_OWNER,

    /**
     * 合作伙伴。
     */
    @SerializedName("PARTNER")
    PARTNER,

    /**
     * 总部。
     */
    @SerializedName("HEADQUARTER")
    HEADQUARTER,

    /**
     * 品牌方。
     */
    @SerializedName("BRAND")
    BRAND,

    /**
     * 分销商。
     */
    @SerializedName("DISTRIBUTOR")
    DISTRIBUTOR,

    /**
     * 用户。
     */
    @SerializedName("USER")
    USER,

    /**
     * 供应商。
     */
    @SerializedName("SUPPLIER")
    SUPPLIER,

    /**
     * 自定义关系。
     */
    @SerializedName("CUSTOM")
    CUSTOM
}
