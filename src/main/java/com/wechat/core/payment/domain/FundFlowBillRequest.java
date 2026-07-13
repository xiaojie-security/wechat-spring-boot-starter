package com.wechat.core.payment.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wechat.core.payment.enums.FundFlowBillAccountType;
import com.wechat.core.payment.enums.TarType;

public class FundFlowBillRequest {
    @SerializedName("bill_date")
    @Expose(serialize = false)
    public String billDate;

    @SerializedName("account_type")
    @Expose(serialize = false)
    public FundFlowBillAccountType accountType;

    @SerializedName("tar_type")
    @Expose(serialize = false)
    public TarType tarType;
}
