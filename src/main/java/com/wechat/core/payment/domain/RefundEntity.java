package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.payment.enums.RefundChannel;
import com.wechat.core.payment.enums.RefundFundsAccount;
import com.wechat.core.payment.enums.RefundStatus;

import java.util.List;

/**
 * 单笔退款详情实体。
 * 用于承接退款申请、退款查询、异常退款接口返回的数据。
 */
public class RefundEntity {
    /**
     * 微信退款单号。
     */
    @SerializedName("refund_id")
    public String refundId;

    /**
     * 商户退款单号。
     */
    @SerializedName("out_refund_no")
    public String outRefundNo;

    /**
     * 微信支付订单号。
     */
    @SerializedName("transaction_id")
    public String transactionId;

    /**
     * 商户订单号。
     */
    @SerializedName("out_trade_no")
    public String outTradeNo;

    /**
     * 退款渠道。
     */
    @SerializedName("channel")
    public RefundChannel channel;

    /**
     * 退款入账账户。
     */
    @SerializedName("user_received_account")
    public String userReceivedAccount;

    /**
     * 退款成功时间。
     */
    @SerializedName("success_time")
    public String successTime;

    /**
     * 退款申请创建时间。
     */
    @SerializedName("create_time")
    public String createTime;

    /**
     * 退款状态。
     */
    @SerializedName("status")
    public RefundStatus status;

    /**
     * 退款出资账户。
     */
    @SerializedName("funds_account")
    public RefundFundsAccount fundsAccount;

    /**
     * 金额信息。
     */
    @SerializedName("amount")
    public Amount amount;

    /**
     * 优惠退款明细。
     */
    @SerializedName("promotion_detail")
    public List<PromotionDetail> promotionDetail;

    /**
     * 退款金额信息。
     */
    public static class Amount {
        /**
         * 原订单金额。
         */
        @SerializedName("total")
        public Long total;

        /**
         * 本次退款金额。
         */
        @SerializedName("refund")
        public Long refund;

        /**
         * 各出资渠道退款信息。
         */
        @SerializedName("from")
        public List<FundsFromItem> from;

        /**
         * 用户实际支付金额。
         */
        @SerializedName("payer_total")
        public Long payerTotal;

        /**
         * 用户实际退款金额。
         */
        @SerializedName("payer_refund")
        public Long payerRefund;

        /**
         * 应结退款金额。
         */
        @SerializedName("settlement_refund")
        public Long settlementRefund;

        /**
         * 应结订单金额。
         */
        @SerializedName("settlement_total")
        public Long settlementTotal;

        /**
         * 优惠退款金额。
         */
        @SerializedName("discount_refund")
        public Long discountRefund;

        /**
         * 币种。
         */
        @SerializedName("currency")
        public String currency;

        /**
         * 退款手续费。
         */
        @SerializedName("refund_fee")
        public Long refundFee;
    }

    /**
     * 单个退款出资项。
     */
    public static class FundsFromItem {
        /**
         * 退款出资账户。
         */
        @SerializedName("account")
        public RefundFundsAccount account;

        /**
         * 对应退款金额。
         */
        @SerializedName("amount")
        public Long amount;
    }

    /**
     * 优惠退款明细。
     */
    public static class PromotionDetail {
        /**
         * 优惠活动 ID。
         */
        @SerializedName("promotion_id")
        public String promotionId;

        /**
         * 优惠范围。
         */
        @SerializedName("scope")
        public String scope;

        /**
         * 优惠类型。
         */
        @SerializedName("type")
        public String type;

        /**
         * 优惠金额。
         */
        @SerializedName("amount")
        public Long amount;

        /**
         * 对应退回的优惠金额。
         */
        @SerializedName("refund_amount")
        public Long refundAmount;

        /**
         * 优惠关联商品退款明细。
         */
        @SerializedName("goods_detail")
        public List<GoodsDetail> goodsDetail;
    }

    /**
     * 退款商品明细。
     */
    public static class GoodsDetail {
        /**
         * 商户商品编码。
         */
        @SerializedName("merchant_goods_id")
        public String merchantGoodsId;

        /**
         * 微信支付商品编码。
         */
        @SerializedName("wechatpay_goods_id")
        public String wechatpayGoodsId;

        /**
         * 商品名称。
         */
        @SerializedName("goods_name")
        public String goodsName;

        /**
         * 商品单价。
         */
        @SerializedName("unit_price")
        public Long unitPrice;

        /**
         * 商品退款金额。
         */
        @SerializedName("refund_amount")
        public Long refundAmount;

        /**
         * 商品退款数量。
         */
        @SerializedName("refund_quantity")
        public Long refundQuantity;
    }
}
