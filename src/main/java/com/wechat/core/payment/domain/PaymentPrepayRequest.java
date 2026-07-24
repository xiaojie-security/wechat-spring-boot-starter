package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 统一下单请求参数。
 * 适用于 JSAPI/小程序下单与 APP 下单接口的大部分公共字段。
 */
public class PaymentPrepayRequest {
    /**
     * 商户应用 AppID。
     * 服务层会在发起请求前自动注入。
     */
    @SerializedName("appid")
    public String appid;

    /**
     * 商户号。
     * 服务层会在发起请求前自动注入。
     */
    @SerializedName("mchid")
    public String mchid;

    /**
     * 商品描述。
     * 用户在支付时可见，用于说明本次订单内容。
     */
    @SerializedName("description")
    public String description;

    /**
     * 商户订单号。
     * 需保证在商户系统内唯一，用于幂等和后续查单。
     */
    @SerializedName("out_trade_no")
    public String outTradeNo;

    /**
     * 订单失效时间。
     * 采用 RFC3339 时间格式。
     */
    @SerializedName("time_expire")
    public String timeExpire;

    /**
     * 附加数据。
     * 微信支付回调时会原样返回，便于透传业务上下文。
     */
    @SerializedName("attach")
    public String attach;

    /**
     * 支付结果通知地址。
     * 外部未显式传递时，服务层会从商户配置快照注入 paymentNotifyUrl。
     */
    @SerializedName("notify_url")
    public String notifyUrl;

    /**
     * 订单优惠标记。
     * 由商户定义，用于活动标识或优惠券匹配。
     */
    @SerializedName("goods_tag")
    public String goodsTag;

    /**
     * 是否支持电子发票。
     */
    @SerializedName("support_fapiao")
    public Boolean supportFapiao;

    /**
     * 订单金额信息。
     */
    @SerializedName("amount")
    public Amount amount;

    /**
     * 支付者信息。
     * JSAPI/小程序下单通常至少需要提供 openid。
     */
    @SerializedName("payer")
    public Payer payer;

    /**
     * 订单明细。
     */
    @SerializedName("detail")
    public Detail detail;

    /**
     * 场景信息。
     * 包含客户端 IP、门店信息等。
     */
    @SerializedName("scene_info")
    public SceneInfo sceneInfo;

    /**
     * 结算信息。
     * 用于控制是否分账等结算行为。
     */
    @SerializedName("settle_info")
    public SettleInfo settleInfo;

    /**
     * 订单金额对象。
     */
    public static class Amount {
        /**
         * 订单总金额，单位为分。
         */
        @SerializedName("total")
        public Long total;

        /**
         * 货币类型。
         * 通常传 CNY。
         */
        @SerializedName("currency")
        public String currency;
    }

    /**
     * 支付者信息。
     */
    public static class Payer {
        /**
         * 用户 OpenID。
         * JSAPI/小程序支付时通常必填。
         */
        @SerializedName("openid")
        public String openid;
    }

    /**
     * 订单明细对象。
     */
    public static class Detail {
        /**
         * 订单原价，单位为分。
         */
        @SerializedName("cost_price")
        public Long costPrice;

        /**
         * 商家小票 ID。
         */
        @SerializedName("invoice_id")
        public String invoiceId;

        /**
         * 商品明细列表。
         */
        @SerializedName("goods_detail")
        public List<GoodsDetail> goodsDetail;
    }

    /**
     * 单个商品明细。
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
         * 购买数量。
         */
        @SerializedName("quantity")
        public Long quantity;

        /**
         * 商品单价，单位为分。
         */
        @SerializedName("unit_price")
        public Long unitPrice;
    }

    /**
     * 支付场景信息。
     */
    public static class SceneInfo {
        /**
         * 用户终端 IP。
         */
        @SerializedName("payer_client_ip")
        public String payerClientIp;

        /**
         * 商户端设备号。
         */
        @SerializedName("device_id")
        public String deviceId;

        /**
         * 门店信息。
         */
        @SerializedName("store_info")
        public StoreInfo storeInfo;

        /**
         * H5 支付场景信息。
         * H5 下单时可用于标识唤起支付的网页环境。
         */
        @SerializedName("h5_info")
        public H5Info h5Info;
    }

    /**
     * 门店信息。
     */
    public static class StoreInfo {
        /**
         * 门店编号。
         */
        @SerializedName("id")
        public String id;

        /**
         * 门店名称。
         */
        @SerializedName("name")
        public String name;

        /**
         * 门店地区编码。
         */
        @SerializedName("area_code")
        public String areaCode;

        /**
         * 门店详细地址。
         */
        @SerializedName("address")
        public String address;
    }

    /**
     * 结算信息。
     */
    public static class SettleInfo {
        /**
         * 是否指定分账。
         */
        @SerializedName("profit_sharing")
        public Boolean profitSharing;
    }

    /**
     * H5 支付场景信息。
     */
    public static class H5Info {
        /**
         * 场景类型。
         * 常见值如 Wap、iOS、Android。
         */
        @SerializedName("type")
        public String type;

        /**
         * 应用名称。
         */
        @SerializedName("app_name")
        public String appName;

        /**
         * 网站 URL。
         */
        @SerializedName("app_url")
        public String appUrl;

        /**
         * iOS 应用包名标识。
         */
        @SerializedName("bundle_id")
        public String bundleId;

        /**
         * Android 应用包名标识。
         */
        @SerializedName("package_name")
        public String packageName;
    }
}
