package com.wechat.core.payment.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wechat.core.payment.enums.BillType;
import com.wechat.core.payment.enums.TarType;

public class TradeBillRequest {
    @SerializedName("bill_date")
    @Expose(serialize = false)
    public String billDate;

    @SerializedName("bill_type")
    @Expose(serialize = false)
    public BillType billType;

    @SerializedName("tar_type")
    @Expose(serialize = false)
    public TarType tarType;
}
