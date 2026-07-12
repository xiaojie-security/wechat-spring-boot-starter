package com.wechat.core.transfer.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 商户单号查询转账单请求参数。
 * 用于封装按商户转账单号查询转账单详情时所需的路径参数。
 */
public class GetTransferBillByOutNoRequest {
    /**
     * 商户转账单号。
     * 对应商户系统内部生成的唯一转账业务单号。
     */
    @SerializedName("out_bill_no")
    @Expose(serialize = false)
    public String outBillNo;
}
