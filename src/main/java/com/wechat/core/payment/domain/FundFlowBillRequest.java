package com.wechat.core.payment.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wechat.core.payment.enums.FundFlowBillAccountType;
import com.wechat.core.payment.enums.TarType;

/**
 * 申请资金账单请求参数。
 */
public class FundFlowBillRequest {
    /**
     * 账单日期。
     * 格式为 YYYY-MM-DD。
     */
    @SerializedName("bill_date")
    @Expose(serialize = false)
    public String billDate;

    /**
     * 资金账户类型。
     */
    @SerializedName("account_type")
    @Expose(serialize = false)
    public FundFlowBillAccountType accountType;

    /**
     * 压缩格式。
     */
    @SerializedName("tar_type")
    @Expose(serialize = false)
    public TarType tarType;
}
