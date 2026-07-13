package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentPrepayRequest {
    @SerializedName("appid")
    public String appid;

    @SerializedName("mchid")
    public String mchid;

    @SerializedName("description")
    public String description;

    @SerializedName("out_trade_no")
    public String outTradeNo;

    @SerializedName("time_expire")
    public String timeExpire;

    @SerializedName("attach")
    public String attach;

    @SerializedName("notify_url")
    public String notifyUrl;

    @SerializedName("goods_tag")
    public String goodsTag;

    @SerializedName("support_fapiao")
    public Boolean supportFapiao;

    @SerializedName("amount")
    public Amount amount;

    @SerializedName("payer")
    public Payer payer;

    @SerializedName("detail")
    public Detail detail;

    @SerializedName("scene_info")
    public SceneInfo sceneInfo;

    @SerializedName("settle_info")
    public SettleInfo settleInfo;

    public static class Amount {
        @SerializedName("total")
        public Long total;

        @SerializedName("currency")
        public String currency;
    }

    public static class Payer {
        @SerializedName("openid")
        public String openid;
    }

    public static class Detail {
        @SerializedName("cost_price")
        public Long costPrice;

        @SerializedName("invoice_id")
        public String invoiceId;

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

        @SerializedName("quantity")
        public Long quantity;

        @SerializedName("unit_price")
        public Long unitPrice;
    }

    public static class SceneInfo {
        @SerializedName("payer_client_ip")
        public String payerClientIp;

        @SerializedName("device_id")
        public String deviceId;

        @SerializedName("store_info")
        public StoreInfo storeInfo;
    }

    public static class StoreInfo {
        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;

        @SerializedName("area_code")
        public String areaCode;

        @SerializedName("address")
        public String address;
    }

    public static class SettleInfo {
        @SerializedName("profit_sharing")
        public Boolean profitSharing;
    }
}
