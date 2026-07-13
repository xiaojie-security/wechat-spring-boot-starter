package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 支付订单详情实体。
 * 用于按微信支付订单号或商户订单号查询订单时承接响应数据。
 */
public class PaymentOrderEntity {
    /**
     * 商户应用 AppID。
     */
    @SerializedName("appid")
    public String appid;

    /**
     * 商户号。
     */
    @SerializedName("mchid")
    public String mchid;

    /**
     * 商户订单号。
     */
    @SerializedName("out_trade_no")
    public String outTradeNo;

    /**
     * 微信支付订单号。
     */
    @SerializedName("transaction_id")
    public String transactionId;

    /**
     * 交易类型。
     * 例如 JSAPI、APP 等。
     */
    @SerializedName("trade_type")
    public String tradeType;

    /**
     * 交易状态。
     * 可用来判断订单是否支付成功、转入退款或已关闭。
     */
    @SerializedName("trade_state")
    public String tradeState;

    /**
     * 交易状态描述。
     */
    @SerializedName("trade_state_desc")
    public String tradeStateDesc;

    /**
     * 付款银行类型。
     */
    @SerializedName("bank_type")
    public String bankType;

    /**
     * 商户附加数据。
     */
    @SerializedName("attach")
    public String attach;

    /**
     * 支付成功时间。
     */
    @SerializedName("success_time")
    public String successTime;

    /**
     * 支付者信息。
     */
    @SerializedName("payer")
    public Payer payer;

    /**
     * 订单金额信息。
     */
    @SerializedName("amount")
    public Amount amount;

    /**
     * 场景信息。
     */
    @SerializedName("scene_info")
    public SceneInfo sceneInfo;

    /**
     * 优惠功能明细列表。
     */
    @SerializedName("promotion_detail")
    public List<PromotionDetail> promotionDetail;

    /**
     * 支付者信息。
     */
    public static class Payer {
        /**
         * 支付者 OpenID。
         */
        @SerializedName("openid")
        public String openid;
    }

    /**
     * 订单金额信息。
     */
    public static class Amount {
        /**
         * 订单总金额，单位为分。
         */
        @SerializedName("total")
        public Long total;

        /**
         * 用户实际支付金额，单位为分。
         */
        @SerializedName("payer_total")
        public Long payerTotal;

        /**
         * 订单金额币种。
         */
        @SerializedName("currency")
        public String currency;

        /**
         * 用户支付币种。
         */
        @SerializedName("payer_currency")
        public String payerCurrency;
    }

    /**
     * 场景信息。
     */
    public static class SceneInfo {
        /**
         * 设备号。
         */
        @SerializedName("device_id")
        public String deviceId;
    }

    /**
     * 单条优惠明细。
     */
    public static class PromotionDetail {
        /**
         * 券 ID。
         */
        @SerializedName("coupon_id")
        public String couponId;

        /**
         * 优惠名称。
         */
        @SerializedName("name")
        public String name;

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
         * 优惠金额，单位为分。
         */
        @SerializedName("amount")
        public Long amount;

        /**
         * 活动库存 ID。
         */
        @SerializedName("stock_id")
        public String stockId;

        /**
         * 微信出资金额。
         */
        @SerializedName("wechatpay_contribute")
        public Long wechatpayContribute;

        /**
         * 商户出资金额。
         */
        @SerializedName("merchant_contribute")
        public Long merchantContribute;

        /**
         * 其他出资方金额。
         */
        @SerializedName("other_contribute")
        public Long otherContribute;

        /**
         * 币种。
         */
        @SerializedName("currency")
        public String currency;

        /**
         * 优惠关联商品明细。
         */
        @SerializedName("goods_detail")
        public List<GoodsDetailInPromotion> goodsDetail;
    }

    /**
     * 优惠项中的商品明细。
     */
    public static class GoodsDetailInPromotion {
        /**
         * 商品编码。
         */
        @SerializedName("goods_id")
        public String goodsId;

        /**
         * 商品数量。
         */
        @SerializedName("quantity")
        public Long quantity;

        /**
         * 商品单价。
         */
        @SerializedName("unit_price")
        public Long unitPrice;

        /**
         * 商品优惠金额。
         */
        @SerializedName("discount_amount")
        public Long discountAmount;

        /**
         * 商品备注。
         */
        @SerializedName("goods_remark")
        public String goodsRemark;
    }
}
