package com.wechat.core.profitsharing.domain;

import com.google.gson.annotations.SerializedName;

/**
 * 请求分账回退参数。
 */
public class ProfitsharingReturnOrderRequest {
    /**
     * 商户分账单号。
     */
    @SerializedName("out_order_no")
    public String outOrderNo;

    /**
     * 商户分账回退单号。
     * 需在商户系统内保持唯一。
     */
    @SerializedName("out_return_no")
    public String outReturnNo;

    /**
     * 回退到的商户号。
     * 如果请求对象未传，则自动使用当前服务已初始化的商户号。
     */
    @SerializedName("return_mchid")
    public String returnMchid;

    /**
     * 回退金额，单位为分。
     */
    @SerializedName("amount")
    public Long amount;

    /**
     * 回退描述。
     */
    @SerializedName("description")
    public String description;
}
