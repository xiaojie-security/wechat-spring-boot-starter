package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.transfer.enums.TransferBillStatus;

/**
 * 商家转账到用户响应参数。
 * 表示微信支付创建转账单后的受理结果以及后续处理所需信息。
 */
public class TransferToUserResponse {
    /**
     * 商户转账单号。
     * 与请求中的 outBillNo 对应，便于商户系统关联自己的业务单。
     */
    @SerializedName("out_bill_no")
    public String outBillNo;

    /**
     * 微信支付转账单号。
     * 由微信支付生成，可用于后续查询和问题排查。
     */
    @SerializedName("transfer_bill_no")
    public String transferBillNo;

    /**
     * 转账单创建时间。
     */
    @SerializedName("create_time")
    public String createTime;

    /**
     * 当前转账状态。
     * 需要结合该状态判断这笔转账是已成功、处理中还是等待用户确认。
     */
    @SerializedName("state")
    public TransferBillStatus state;

    /**
     * 收款确认拉起参数。
     * 当状态为 WAIT_USER_CONFIRM 时，通常需要将该值交给前端拉起确认收款流程。
     */
    @SerializedName("package_info")
    public String packageInfo;

    /**
     * 用户展示名称。
     * 通常用于前端展示授权或确认收款时的用户名称信息。
     */
    @SerializedName("user_display_name")
    public String userDisplayName;

    /**
     * 商户授权单号。
     * 对应请求中的免确认授权业务单号，用于关联后续授权结果。
     */
    @SerializedName("out_authorization_no")
    public String outAuthorizationNo;
}
