package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.transfer.enums.TransferBillStatus;

/**
 * 转账单详情实体。
 * 用于承接按商户单号或微信单号查询转账单接口返回的核心业务字段。
 */
public class TransferBillEntity {
    /**
     * 商户号。
     * 表示这笔转账所属的商户身份。
     */
    @SerializedName("mch_id")
    public String mchId;

    /**
     * 商户转账单号。
     * 用于与商户本地业务单据关联。
     */
    @SerializedName("out_bill_no")
    public String outBillNo;

    /**
     * 微信支付转账单号。
     * 可用于微信侧问题排查和后续查询。
     */
    @SerializedName("transfer_bill_no")
    public String transferBillNo;

    /**
     * 商户应用 AppID。
     */
    @SerializedName("appid")
    public String appid;

    /**
     * 当前转账单状态。
     * 需要结合枚举值判断这笔转账处于处理中、成功、失败还是撤销状态。
     */
    @SerializedName("state")
    public TransferBillStatus state;

    /**
     * 转账金额，单位为分。
     */
    @SerializedName("transfer_amount")
    public Long transferAmount;

    /**
     * 转账备注。
     * 该内容通常会展示给收款用户。
     */
    @SerializedName("transfer_remark")
    public String transferRemark;

    /**
     * 失败原因。
     * 仅当转账失败时通常才有值。
     */
    @SerializedName("fail_reason")
    public String failReason;

    /**
     * 收款用户 OpenID。
     */
    @SerializedName("openid")
    public String openid;

    /**
     * 收款用户名。
     * 如果请求中传入了收款人姓名校验，这里通常会返回对应信息。
     */
    @SerializedName("user_name")
    public String userName;

    /**
     * 转账单创建时间。
     */
    @SerializedName("create_time")
    public String createTime;

    /**
     * 转账单最后更新时间。
     * 当转账状态发生变化时，该时间会随之更新。
     */
    @SerializedName("update_time")
    public String updateTime;
}
