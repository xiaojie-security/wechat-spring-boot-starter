package com.wechat.core.profitsharing.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.profitsharing.enums.ProfitsharingReceiverType;
import com.wechat.core.profitsharing.enums.ProfitsharingRelationType;

/**
 * 添加分账接收方请求参数。
 */
public class ProfitsharingReceiverRequest {
    /**
     * 商户应用 AppID。
     * 服务层会在发起请求前自动注入。
     */
    @SerializedName("appid")
    public String appid;

    /**
     * 分账接收方类型。
     */
    @SerializedName("type")
    public ProfitsharingReceiverType type;

    /**
     * 分账接收方账号。
     */
    @SerializedName("account")
    public String account;

    /**
     * 分账接收方姓名。
     * 服务层会在请求发送前自动加密。
     */
    @SerializedName("name")
    public String name;

    /**
     * 与分账方的关系类型。
     */
    @SerializedName("relation_type")
    public ProfitsharingRelationType relationType;

    /**
     * 自定义分账关系。
     * 仅当 relationType 为 CUSTOM 时通常需要传入。
     */
    @SerializedName("custom_relation")
    public String customRelation;
}
