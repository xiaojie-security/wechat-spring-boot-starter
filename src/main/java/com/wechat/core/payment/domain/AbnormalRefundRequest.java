package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.payment.enums.AbnormalRefundType;

public class AbnormalRefundRequest {
    @SerializedName("out_refund_no")
    public String outRefundNo;

    @SerializedName("type")
    public AbnormalRefundType type;

    @SerializedName("bank_type")
    public String bankType;

    @SerializedName("bank_account")
    public String bankAccount;

    @SerializedName("real_name")
    public String realName;
}
