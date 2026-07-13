package com.wechat.core.payment.service;

import com.wechat.core.payment.domain.AbnormalRefundRequest;
import com.wechat.core.payment.domain.BillDownloadEntity;
import com.wechat.core.payment.domain.FundFlowBillRequest;
import com.wechat.core.payment.domain.PaymentOrderEntity;
import com.wechat.core.payment.domain.PaymentPrepayRequest;
import com.wechat.core.payment.domain.PaymentPrepayResponse;
import com.wechat.core.payment.domain.RefundEntity;
import com.wechat.core.payment.domain.RefundRequest;
import com.wechat.core.payment.domain.TradeBillRequest;

/**
 * 微信支付服务接口。
 * 封装普通商户场景下的下单、查单、关单、退款和账单申请能力。
 */
public interface WechatPaymentService {
    /**
     * JSAPI/小程序统一下单。
     *
     * @param request 下单请求参数，需包含商品描述、商户订单号、金额、支付者等信息
     * @return 统一下单响应，主要包含 prepayId，前端可据此继续拉起支付
     */
    PaymentPrepayResponse jsapiPrepay(PaymentPrepayRequest request);

    /**
     * APP 统一下单。
     *
     * @param request 下单请求参数，需包含商品描述、商户订单号、金额等信息
     * @return 统一下单响应，主要包含 prepayId，客户端可据此继续调起支付
     */
    PaymentPrepayResponse appPrepay(PaymentPrepayRequest request);

    /**
     * 通过微信支付订单号查询订单。
     *
     * @param transactionId 微信支付订单号
     * @return 订单详情，包含交易状态、金额、支付者和优惠信息等
     */
    PaymentOrderEntity queryOrderByTransactionId(String transactionId);

    /**
     * 通过商户订单号查询订单。
     *
     * @param outTradeNo 商户订单号
     * @return 订单详情，包含交易状态、金额、支付者和优惠信息等
     */
    PaymentOrderEntity queryOrderByOutTradeNo(String outTradeNo);

    /**
     * 关闭订单。
     * 适用于未支付完成且无需继续支付的订单关闭处理。
     *
     * @param outTradeNo 商户订单号
     */
    void closeOrder(String outTradeNo);

    /**
     * 发起退款申请。
     *
     * @param request 退款请求参数，需包含订单标识、退款单号和退款金额等信息
     * @return 退款详情，包含退款状态、退款金额和退款单号等
     */
    RefundEntity createRefund(RefundRequest request);

    /**
     * 通过商户退款单号查询单笔退款。
     *
     * @param outRefundNo 商户退款单号
     * @return 退款详情，包含退款状态、退款渠道和到账账户等
     */
    RefundEntity queryRefundByOutRefundNo(String outRefundNo);

    /**
     * 发起异常退款。
     * 适用于原路退款失败后，根据微信支付要求补充银行卡等信息重新发起退款。
     *
     * @param refundId 微信退款单号
     * @param request 异常退款请求参数
     * @return 退款详情，包含异常退款后的当前状态
     */
    RefundEntity createAbnormalRefund(String refundId, AbnormalRefundRequest request);

    /**
     * 申请交易账单。
     *
     * @param request 交易账单请求参数，需包含账单日期、账单类型和压缩格式
     * @return 账单下载信息，包含下载地址和摘要校验信息
     */
    BillDownloadEntity getTradeBill(TradeBillRequest request);

    /**
     * 申请资金账单。
     *
     * @param request 资金账单请求参数，需包含账单日期、账户类型和压缩格式
     * @return 账单下载信息，包含下载地址和摘要校验信息
     */
    BillDownloadEntity getFundFlowBill(FundFlowBillRequest request);
}
