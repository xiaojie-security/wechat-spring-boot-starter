package com.wechat.core.profitsharing.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.profitsharing.enums.ProfitsharingReturnResult;

/**
 * 分账回退单详情实体。
 * 用于承接请求分账回退与查询分账回退结果接口返回的数据。
 */
public class ProfitsharingReturnOrderEntity {
    /**
     * 微信分账回退单号。
     */
    @SerializedName("return_id")
    public String returnId;

    /**
     * 商户分账回退单号。
     */
    @SerializedName("out_return_no")
    public String outReturnNo;

    /**
     * 微信分账单号。
     */
    @SerializedName("order_id")
    public String orderId;

    /**
     * 商户分账单号。
     */
    @SerializedName("out_order_no")
    public String outOrderNo;

    /**
     * 回退到的商户号。
     */
    @SerializedName("return_mchid")
    public String returnMchid;

    /**
     * 回退金额，单位为分。
     */
    @SerializedName("amount")
    public Long amount;

    /**
     * 回退描述。
     */
    @SerializedName("description")
    public String description;

    /**
     * 分账回退结果。
     */
    @SerializedName("result")
    public ProfitsharingReturnResult result;

    /**
     * 回退失败原因。
     */
    @SerializedName("fail_reason")
    public String failReason;

    /**
     * 回退申请创建时间。
     */
    @SerializedName("create_time")
    public String createTime;

    /**
     * 回退完成时间。
     */
    @SerializedName("finish_time")
    public String finishTime;
}
