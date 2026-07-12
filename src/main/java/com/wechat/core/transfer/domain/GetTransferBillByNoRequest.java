package com.wechat.core.transfer.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 微信单号查询转账单请求参数。
 * 用于封装按微信支付转账单号查询转账单详情时所需的路径参数。
 */
public class GetTransferBillByNoRequest {
    /**
     * 微信支付转账单号。
     * 该值由微信支付在受理转账后生成。
     */
    @SerializedName("transfer_bill_no")
    @Expose(serialize = false)
    public String transferBillNo;
}
