package com.wechat.core.profitsharing.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.profitsharing.enums.ProfitsharingReceiverType;

/**
 * 删除分账接收方请求参数。
 */
public class DeleteProfitsharingReceiverRequest {
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
}
