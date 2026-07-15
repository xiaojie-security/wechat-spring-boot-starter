package com.aliyun.core.alipay.payment.domain;

/**
 * 支付宝统一支付响应对象。
 */
public class AlipayPaymentResponse {

    /**
     * 本次调用是否成功。
     */
    public boolean success;

    /**
     * 支付宝响应码。
     */
    public String code;

    /**
     * 响应描述。
     */
    public String msg;

    /**
     * 业务错误码。
     */
    public String subCode;

    /**
     * 业务错误描述。
     */
    public String subMsg;

    /**
     * 商户订单号。
     */
    public String outTradeNo;

    /**
     * 支付宝交易号。
     */
    public String tradeNo;

    /**
     * 订单码支付二维码内容。
     */
    public String qrCode;

    /**
     * APP 支付签名串。
     */
    public String orderStr;

    /**
     * 页面支付返回体。
     * H5/PC 支付通常为自动提交的 HTML 表单。
     */
    public String body;
}
