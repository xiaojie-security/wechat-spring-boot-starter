package com.wechat.core.profitsharing.domain;

/**
 * 查询分账结果请求参数。
 */
public class QueryProfitsharingOrderRequest {
    /**
     * 商户分账单号。
     */
    public String outOrderNo;

    /**
     * 微信支付订单号。
     */
    public String transactionId;
}
