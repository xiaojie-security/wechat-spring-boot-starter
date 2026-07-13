package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.payment.enums.RefundFundsAccount;

import java.util.List;

public class RefundRequest {
    @SerializedName("transaction_id")
    public String transactionId;

    @SerializedName("out_trade_no")
    public String outTradeNo;

    @SerializedName("out_refund_no")
    public String outRefundNo;

    @SerializedName("reason")
    public String reason;

    @SerializedName("notify_url")
    public String notifyUrl;

    @SerializedName("funds_account")
    public RefundFundsAccount fundsAccount;

    @SerializedName("amount")
    public Amount amount;

    @SerializedName("goods_detail")
    public List<GoodsDetail> goodsDetail;

    public static class Amount {
        @SerializedName("refund")
        public Long refund;

        @SerializedName("from")
        public List<FundsFromItem> from;

        @SerializedName("total")
        public Long total;

        @SerializedName("currency")
        public String currency;
    }

    public static class FundsFromItem {
        @SerializedName("account")
        public RefundFundsAccount account;

        @SerializedName("amount")
        public Long amount;
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
