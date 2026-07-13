package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.payment.enums.AbnormalRefundType;

/**
 * 异常退款请求参数。
 * 当原路退款失败时，可通过该对象补充退回银行卡所需信息。
 */
public class AbnormalRefundRequest {
    /**
     * 商户退款单号。
     * 用于标识本次异常退款业务单。
     */
    @SerializedName("out_refund_no")
    public String outRefundNo;

    /**
     * 异常退款类型。
     */
    @SerializedName("type")
    public AbnormalRefundType type;

    /**
     * 开户银行类型。
     */
    @SerializedName("bank_type")
    public String bankType;

    /**
     * 银行卡号。
     * 发起请求前会由服务层自动加密。
     */
    @SerializedName("bank_account")
    public String bankAccount;

    /**
     * 开户姓名。
     * 发起请求前会由服务层自动加密。
     */
    @SerializedName("real_name")
    public String realName;
}
