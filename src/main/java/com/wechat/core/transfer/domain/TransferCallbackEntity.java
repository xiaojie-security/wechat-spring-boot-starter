package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.transfer.enums.TransferBillStatus;

/**
 * 商家转账结果回调解密后的业务内容实体。
 * 用于承接微信支付转账结果通知中 resource 解密后的业务数据。
 */
public class TransferCallbackEntity {
    /**
     * 商户号。
     */
    @SerializedName("mch_id")
    public String mchId;

    /**
     * 商户转账单号。
     */
    @SerializedName("out_bill_no")
    public String outBillNo;

    /**
     * 微信支付转账单号。
     */
    @SerializedName("transfer_bill_no")
    public String transferBillNo;

    /**
     * 转账状态。
     */
    @SerializedName("state")
    public TransferBillStatus state;

    /**
     * 转账金额，单位为分。
     */
    @SerializedName("transfer_amount")
    public Long transferAmount;

    /**
     * 收款用户 OpenID。
     */
    @SerializedName("openid")
    public String openid;

    /**
     * 转账失败原因。
     */
    @SerializedName("fail_reason")
    public String failReason;

    /**
     * 转账单创建时间。
     */
    @SerializedName("create_time")
    public String createTime;

    /**
     * 转账单最后更新时间。
     */
    @SerializedName("update_time")
    public String updateTime;

    /**
     * 收款方式类型。
     */
    @SerializedName("payment_method_type")
    public String paymentMethodType;
}
