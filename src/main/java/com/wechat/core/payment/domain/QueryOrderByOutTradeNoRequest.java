package com.wechat.core.payment.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 通过商户订单号查询订单请求参数。
 */
public class QueryOrderByOutTradeNoRequest {
    /**
     * 商户订单号。
     */
    @SerializedName("out_trade_no")
    @Expose(serialize = false)
    public String outTradeNo;

    /**
     * 商户号。
     * 如果未传，则服务层会自动回退使用已初始化的商户号。
     */
    @SerializedName("mchid")
    @Expose(serialize = false)
    public String mchid;
}
