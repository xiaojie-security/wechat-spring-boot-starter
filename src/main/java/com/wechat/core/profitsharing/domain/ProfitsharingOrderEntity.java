package com.wechat.core.profitsharing.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.profitsharing.enums.ProfitsharingOrderState;
import com.wechat.core.profitsharing.enums.ProfitsharingReceiverResult;
import com.wechat.core.profitsharing.enums.ProfitsharingReceiverType;

import java.util.List;

/**
 * 分账单详情实体。
 * 用于承接请求分账、查询分账结果以及解冻剩余资金接口返回的数据。
 */
public class ProfitsharingOrderEntity {
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
     * 微信支付订单号。
     */
    @SerializedName("transaction_id")
    public String transactionId;

    /**
     * 分账单状态。
     */
    @SerializedName("state")
    public ProfitsharingOrderState state;

    /**
     * 分账接收方处理结果列表。
     */
    @SerializedName("receivers")
    public List<ReceiverResult> receivers;

    /**
     * 分账单创建时间。
     */
    @SerializedName("create_time")
    public String createTime;

    /**
     * 分账单完成时间。
     */
    @SerializedName("finish_time")
    public String finishTime;

    /**
     * 单个分账接收方结果。
     */
    public static class ReceiverResult {
        /**
         * 微信分账明细单号。
         */
        @SerializedName("detail_id")
        public String detailId;

        /**
         * 分账接收方类型。
         */
        @SerializedName("type")
        public ProfitsharingReceiverType type;

        /**
         * 分账接收方账号。
         */
        @SerializedName("account")
        public String account;

        /**
         * 分账金额，单位为分。
         */
        @SerializedName("amount")
        public Long amount;

        /**
         * 分账描述。
         */
        @SerializedName("description")
        public String description;

        /**
         * 分账结果。
         */
        @SerializedName("result")
        public ProfitsharingReceiverResult result;

        /**
         * 分账失败原因。
         * 仅在接收方明细处理失败时通常返回。
         */
        @SerializedName("fail_reason")
        public String failReason;
    }
}
