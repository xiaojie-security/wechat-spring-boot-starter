package com.wechat.core.payment.service;

import com.wechat.core.payment.domain.AbnormalRefundRequest;
import com.wechat.core.payment.domain.BillDownloadEntity;
import com.wechat.core.payment.domain.FundFlowBillRequest;
import com.wechat.core.payment.domain.PaymentCloseOrderRequest;
import com.wechat.core.payment.domain.PaymentOrderEntity;
import com.wechat.core.payment.domain.PaymentPrepayRequest;
import com.wechat.core.payment.domain.PaymentPrepayResponse;
import com.wechat.core.payment.domain.QueryOrderByOutTradeNoRequest;
import com.wechat.core.payment.domain.QueryOrderByTransactionIdRequest;
import com.wechat.core.payment.domain.QueryRefundByOutRefundNoRequest;
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
     * @param request 下单请求参数，需包含商品描述、商户订单号、金额、支付者等信息。
     *                其中 {@code appid}、{@code mchid} 支持自动注入：
     *                如果请求对象自身已传值，则优先使用请求值；
     *                如果未传，则自动使用服务中已初始化的商户应用 AppID 和商户号。
     * @return 统一下单响应，主要包含 prepayId，前端可据此继续拉起支付
     */
    PaymentPrepayResponse jsapiPrepay(PaymentPrepayRequest request);

    /**
     * APP 统一下单。
     *
     * @param request 下单请求参数，需包含商品描述、商户订单号、金额等信息。
     *                其中 {@code appid}、{@code mchid} 支持自动注入：
     *                如果请求对象自身已传值，则优先使用请求值；
     *                如果未传，则自动使用服务中已初始化的商户应用 AppID 和商户号。
     * @return 统一下单响应，主要包含 prepayId，客户端可据此继续调起支付
     */
    PaymentPrepayResponse appPrepay(PaymentPrepayRequest request);

    /**
     * H5 支付下单。
     *
     * @param request 下单请求参数，需包含商品描述、商户订单号、金额以及 H5 场景信息。
     *                其中 {@code appid}、{@code mchid} 支持自动注入：
     *                如果请求对象自身已传值，则优先使用请求值；
     *                如果未传，则自动使用服务中已初始化的商户应用 AppID 和商户号。
     * @return 下单响应，主要包含 h5Url，前端浏览器可据此跳转至微信支付收银台
     */
    PaymentPrepayResponse h5Prepay(PaymentPrepayRequest request);

    /**
     * Native 支付下单。
     *
     * @param request 下单请求参数，需包含商品描述、商户订单号、金额等信息。
     *                其中 {@code appid}、{@code mchid} 支持自动注入：
     *                如果请求对象自身已传值，则优先使用请求值；
     *                如果未传，则自动使用服务中已初始化的商户应用 AppID 和商户号。
     * @return 下单响应，主要包含 codeUrl，商户可据此生成支付二维码
     */
    PaymentPrepayResponse nativePrepay(PaymentPrepayRequest request);

    /**
     * 通过微信支付订单号查询订单。
     *
     * @param request 查询请求参数。
     *                其中 {@code mchid} 支持自动注入：
     *                如果请求对象自身已传值，则优先使用请求值；
     *                如果未传，则自动使用服务中已初始化的商户号。
     * @return 订单详情，包含交易状态、金额、支付者和优惠信息等
     */
    PaymentOrderEntity queryOrderByTransactionId(QueryOrderByTransactionIdRequest request);

    /**
     * 通过商户订单号查询订单。
     *
     * @param request 查询请求参数。
     *                其中 {@code mchid} 支持自动注入：
     *                如果请求对象自身已传值，则优先使用请求值；
     *                如果未传，则自动使用服务中已初始化的商户号。
     * @return 订单详情，包含交易状态、金额、支付者和优惠信息等
     */
    PaymentOrderEntity queryOrderByOutTradeNo(QueryOrderByOutTradeNoRequest request);

    /**
     * 关闭订单。
     * 适用于未支付完成且无需继续支付的订单关闭处理。
     *
     * @param request 关闭订单请求参数。
     *                其中 {@code mchid} 支持自动注入：
     *                如果请求对象自身已传值，则优先使用请求值；
     *                如果未传，则自动使用服务中已初始化的商户号。
     */
    void closeOrder(PaymentCloseOrderRequest request);

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
     * @param request 退款查询请求参数，需包含商户退款单号
     * @return 退款详情，包含退款状态、退款渠道和到账账户等
     */
    RefundEntity queryRefundByOutRefundNo(QueryRefundByOutRefundNoRequest request);

    /**
     * 发起异常退款。
     * 适用于原路退款失败后，根据微信支付要求补充银行卡等信息重新发起退款。
     *
     * @param request 异常退款请求参数，需包含微信退款单号以及补充退款信息
     * @return 退款详情，包含异常退款后的当前状态
     */
    RefundEntity createAbnormalRefund(AbnormalRefundRequest request);

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
