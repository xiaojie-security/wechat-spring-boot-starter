package com.aliyun.core.alipay.payment.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * 支付宝统一支付请求对象。
 * 适用于订单码支付、APP 支付、手机网站支付和 PC 页面支付。
 */
public class AlipayPaymentRequest {

    /**
     * 商户订单号。
     * 同一商户下需保持唯一。
     */
    @SerializedName("out_trade_no")
    public String outTradeNo;

    /**
     * 订单总金额，单位为元，精确到小数点后两位。
     */
    @SerializedName("total_amount")
    public String totalAmount;

    /**
     * 订单标题。
     */
    @SerializedName("subject")
    public String subject;

    /**
     * 商品描述。
     */
    @SerializedName("body")
    public String body;

    /**
     * 销售产品码。
     * 未传时服务会按不同支付场景补默认值。
     */
    @SerializedName("product_code")
    public String productCode;

    /**
     * 订单允许支付的最晚时间，例如 30m。
     */
    @SerializedName("timeout_express")
    public String timeoutExpress;

    /**
     * 卖家支付宝用户 ID。
     */
    @SerializedName("seller_id")
    public String sellerId;

    /**
     * 商户门店编号。
     */
    @SerializedName("store_id")
    public String storeId;

    /**
     * 商户操作员编号。
     */
    @SerializedName("operator_id")
    public String operatorId;

    /**
     * 商户机具终端编号。
     */
    @SerializedName("terminal_id")
    public String terminalId;

    /**
     * 商品主类型。
     */
    @SerializedName("goods_type")
    public String goodsType;

    /**
     * 公用回传参数。
     */
    @SerializedName("passback_params")
    public String passbackParams;

    /**
     * 商户原始订单号。
     */
    @SerializedName("merchant_order_no")
    public String merchantOrderNo;

    /**
     * 订单码绝对过期时间，仅订单码支付场景常用。
     */
    @SerializedName("qr_code_timeout_express")
    public String qrCodeTimeoutExpress;

    /**
     * 异步通知地址。
     * 未传时服务会尝试使用全局配置。
     */
    public String notifyUrl;

    /**
     * 同步跳转地址。
     * H5/PC 场景常用，未传时服务会尝试使用全局配置。
     */
    public String returnUrl;

    /**
     * 用户中途退出返回地址。
     * 仅手机网站支付场景使用。
     */
    public String quitUrl;

    /**
     * 是否启用请求内容加密。
     * 未传时取全局配置。
     */
    public Boolean needEncrypt;

    /**
     * 业务扩展参数。
     */
    @SerializedName("extend_params")
    public Map<String, Object> extendParams;

    /**
     * 业务透传特殊参数。
     */
    @SerializedName("business_params")
    public Map<String, Object> businessParams;

    /**
     * 结算信息。
     */
    @SerializedName("settle_info")
    public Map<String, Object> settleInfo;

    /**
     * 优惠参数。
     */
    @SerializedName("promo_params")
    public String promoParams;

    /**
     * 商品明细。
     */
    @SerializedName("goods_detail")
    public List<GoodsDetail> goodsDetail;

    /**
     * 额外业务参数。
     * 当统一对象未覆盖某个支付宝字段时，可直接透传。
     */
    public Map<String, Object> extraBizContent;

    public static class GoodsDetail {
        @SerializedName("goods_id")
        public String goodsId;

        @SerializedName("goods_name")
        public String goodsName;

        @SerializedName("quantity")
        public Long quantity;

        @SerializedName("price")
        public String price;

        @SerializedName("goods_category")
        public String goodsCategory;

        @SerializedName("body")
        public String body;

        @SerializedName("show_url")
        public String showUrl;
    }
}
