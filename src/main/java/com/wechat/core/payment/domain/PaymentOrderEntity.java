package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentOrderEntity {
    @SerializedName("appid")
    public String appid;

    @SerializedName("mchid")
    public String mchid;

    @SerializedName("out_trade_no")
    public String outTradeNo;

    @SerializedName("transaction_id")
    public String transactionId;

    @SerializedName("trade_type")
    public String tradeType;

    @SerializedName("trade_state")
    public String tradeState;

    @SerializedName("trade_state_desc")
    public String tradeStateDesc;

    @SerializedName("bank_type")
    public String bankType;

    @SerializedName("attach")
    public String attach;

    @SerializedName("success_time")
    public String successTime;

    @SerializedName("payer")
    public Payer payer;

    @SerializedName("amount")
    public Amount amount;

    @SerializedName("scene_info")
    public SceneInfo sceneInfo;

    @SerializedName("promotion_detail")
    public List<PromotionDetail> promotionDetail;

    public static class Payer {
        @SerializedName("openid")
        public String openid;
    }

    public static class Amount {
        @SerializedName("total")
        public Long total;

        @SerializedName("payer_total")
        public Long payerTotal;

        @SerializedName("currency")
        public String currency;

        @SerializedName("payer_currency")
        public String payerCurrency;
    }

    public static class SceneInfo {
        @SerializedName("device_id")
        public String deviceId;
    }

    public static class PromotionDetail {
        @SerializedName("coupon_id")
        public String couponId;

        @SerializedName("name")
        public String name;

        @SerializedName("scope")
        public String scope;

        @SerializedName("type")
        public String type;

        @SerializedName("amount")
        public Long amount;

        @SerializedName("stock_id")
        public String stockId;

        @SerializedName("wechatpay_contribute")
        public Long wechatpayContribute;

        @SerializedName("merchant_contribute")
        public Long merchantContribute;

        @SerializedName("other_contribute")
        public Long otherContribute;

        @SerializedName("currency")
        public String currency;

        @SerializedName("goods_detail")
        public List<GoodsDetailInPromotion> goodsDetail;
    }

    public static class GoodsDetailInPromotion {
        @SerializedName("goods_id")
        public String goodsId;

        @SerializedName("quantity")
        public Long quantity;

        @SerializedName("unit_price")
        public Long unitPrice;

        @SerializedName("discount_amount")
        public Long discountAmount;

        @SerializedName("goods_remark")
        public String goodsRemark;
    }
}
