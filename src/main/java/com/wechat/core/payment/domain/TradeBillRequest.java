package com.wechat.core.payment.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wechat.core.payment.enums.BillType;
import com.wechat.core.payment.enums.TarType;

/**
 * 申请交易账单请求参数。
 */
public class TradeBillRequest {
    /**
     * 账单日期。
     * 格式为 YYYY-MM-DD。
     */
    @SerializedName("bill_date")
    @Expose(serialize = false)
    public String billDate;

    /**
     * 账单类型。
     * 可按全部、支付成功、退款等维度申请。
     */
    @SerializedName("bill_type")
    @Expose(serialize = false)
    public BillType billType;

    /**
     * 压缩格式。
     * 传 GZIP 表示返回压缩账单下载地址。
     */
    @SerializedName("tar_type")
    @Expose(serialize = false)
    public TarType tarType;
}
