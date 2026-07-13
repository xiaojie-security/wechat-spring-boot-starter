package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.payment.enums.RefundChannel;
import com.wechat.core.payment.enums.RefundFundsAccount;
import com.wechat.core.payment.enums.RefundStatus;

import java.util.List;

public class RefundEntity {
    @SerializedName("refund_id")
    public String refundId;

    @SerializedName("out_refund_no")
    public String outRefundNo;

    @SerializedName("transaction_id")
    public String transactionId;

    @SerializedName("out_trade_no")
    public String outTradeNo;

    @SerializedName("channel")
    public RefundChannel channel;

    @SerializedName("user_received_account")
    public String userReceivedAccount;

    @SerializedName("success_time")
    public String successTime;

    @SerializedName("create_time")
    public String createTime;

    @SerializedName("status")
    public RefundStatus status;

    @SerializedName("funds_account")
    public RefundFundsAccount fundsAccount;

    @SerializedName("amount")
    public Amount amount;

    @SerializedName("promotion_detail")
    public List<PromotionDetail> promotionDetail;

    public static class Amount {
        @SerializedName("total")
        public Long total;

        @SerializedName("refund")
        public Long refund;

        @SerializedName("from")
        public List<FundsFromItem> from;

        @SerializedName("payer_total")
        public Long payerTotal;

        @SerializedName("payer_refund")
        public Long payerRefund;

        @SerializedName("settlement_refund")
        public Long settlementRefund;

        @SerializedName("settlement_total")
        public Long settlementTotal;

        @SerializedName("discount_refund")
        public Long discountRefund;

        @SerializedName("currency")
        public String currency;

        @SerializedName("refund_fee")
        public Long refundFee;
    }

    public static class FundsFromItem {
        @SerializedName("account")
        public RefundFundsAccount account;

        @SerializedName("amount")
        public Long amount;
    }

    public static class PromotionDetail {
        @SerializedName("promotion_id")
        public String promotionId;

        @SerializedName("scope")
        public String scope;

        @SerializedName("type")
        public String type;

        @SerializedName("amount")
        public Long amount;

        @SerializedName("refund_amount")
        public Long refundAmount;

        @SerializedName("goods_detail")
        public List<GoodsDetail> goodsDetail;
    }

    public static class GoodsDetail {
        @SerializedName("merchant_goods_id")
        public String merchantGoodsId;

        @SerializedName("wechatpay_goods_id")
        public String wechatpayGoodsId;

        @SerializedName("goods_name")
        public String goodsName;

        @SerializedName("unit_price")
        public Long unitPrice;

        @SerializedName("refund_amount")
        public Long refundAmount;

        @SerializedName("refund_quantity")
        public Long refundQuantity;
    }
}
