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

public interface WechatPaymentService {
    PaymentPrepayResponse jsapiPrepay(PaymentPrepayRequest request);

    PaymentPrepayResponse appPrepay(PaymentPrepayRequest request);

    PaymentOrderEntity queryOrderByTransactionId(String transactionId);

    PaymentOrderEntity queryOrderByOutTradeNo(String outTradeNo);

    void closeOrder(String outTradeNo);

    RefundEntity createRefund(RefundRequest request);

    RefundEntity queryRefundByOutRefundNo(String outRefundNo);

    RefundEntity createAbnormalRefund(String refundId, AbnormalRefundRequest request);

    BillDownloadEntity getTradeBill(TradeBillRequest request);

    BillDownloadEntity getFundFlowBill(FundFlowBillRequest request);
}
