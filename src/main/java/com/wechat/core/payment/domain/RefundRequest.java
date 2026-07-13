package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.payment.enums.RefundFundsAccount;

import java.util.List;

/**
 * 退款申请请求参数。
 */
public class RefundRequest {
    /**
     * 微信支付订单号。
     * 与商户订单号二选一。
     */
    @SerializedName("transaction_id")
    public String transactionId;

    /**
     * 商户订单号。
     * 与微信支付订单号二选一。
     */
    @SerializedName("out_trade_no")
    public String outTradeNo;

    /**
     * 商户退款单号。
     * 需在商户系统内唯一。
     */
    @SerializedName("out_refund_no")
    public String outRefundNo;

    /**
     * 退款原因。
     */
    @SerializedName("reason")
    public String reason;

    /**
     * 退款结果通知地址。
     */
    @SerializedName("notify_url")
    public String notifyUrl;

    /**
     * 退款出资账户。
     */
    @SerializedName("funds_account")
    public RefundFundsAccount fundsAccount;

    /**
     * 退款金额信息。
     */
    @SerializedName("amount")
    public Amount amount;

    /**
     * 退款商品明细。
     * 部分退款场景下可传。
     */
    @SerializedName("goods_detail")
    public List<GoodsDetail> goodsDetail;

    /**
     * 退款金额对象。
     */
    public static class Amount {
        /**
         * 退款金额，单位为分。
         */
        @SerializedName("refund")
        public Long refund;

        /**
         * 指定退款出资渠道列表。
         */
        @SerializedName("from")
        public List<FundsFromItem> from;

        /**
         * 原订单金额，单位为分。
         */
        @SerializedName("total")
        public Long total;

        /**
         * 币种。
         */
        @SerializedName("currency")
        public String currency;
    }

    /**
     * 指定退款资金来源项。
     */
    public static class FundsFromItem {
        /**
         * 出资账户类型。
         */
        @SerializedName("account")
        public RefundFundsAccount account;

        /**
         * 对应账户退款金额。
         */
        @SerializedName("amount")
        public Long amount;
    }

    /**
     * 退款商品明细。
     */
    public static class GoodsDetail {
        /**
         * 商户侧商品编码。
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
