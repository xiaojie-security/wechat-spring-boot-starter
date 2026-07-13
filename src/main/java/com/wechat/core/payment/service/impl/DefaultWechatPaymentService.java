package com.wechat.core.payment.service.impl;

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
import com.wechat.core.payment.service.WechatPaymentService;
import com.wechat.properties.MerchantIdentityProperties;
import com.wechat.utils.WechatPayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultWechatPaymentService implements WechatPaymentService, InitializingBean {
    private final MerchantIdentityProperties merchantIdentityProperties;

    private String mchid;
    private String appid;
    private String certificateSerialNo;
    private PrivateKey privateKey;
    private String wechatPayPublicKeyId;
    private PublicKey wechatPayPublicKey;
    private final OkHttpClient client = new OkHttpClient.Builder().build();

    @Override
    public void afterPropertiesSet() throws Exception {
        wechatPayPublicKey = WechatPayUtils.loadPublicKeyFromString(merchantIdentityProperties.getPublicKey());
        privateKey = WechatPayUtils.loadPrivateKeyFromPath(merchantIdentityProperties.getCertificate());
        wechatPayPublicKeyId = merchantIdentityProperties.getPublicKeyId();
        certificateSerialNo = merchantIdentityProperties.getSerialNo();
        mchid = merchantIdentityProperties.getMerchantId();
        appid = merchantIdentityProperties.getAppid();
    }

    @Override
    public PaymentPrepayResponse jsapiPrepay(PaymentPrepayRequest request) {
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "POST";
        String PATH = "/v3/pay/transactions/jsapi";

        if (isBlank(request.appid)) {
            request.appid = appid;
        }
        if (isBlank(request.mchid)) {
            request.mchid = mchid;
        }

        String reqBody = WechatPayUtils.toJson(request);
        return executeJsonRequest(HOST, METHOD, PATH, reqBody, PaymentPrepayResponse.class);
    }

    @Override
    public PaymentPrepayResponse appPrepay(PaymentPrepayRequest request) {
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "POST";
        String PATH = "/v3/pay/transactions/app";

        if (isBlank(request.appid)) {
            request.appid = appid;
        }
        if (isBlank(request.mchid)) {
            request.mchid = mchid;
        }

        String reqBody = WechatPayUtils.toJson(request);
        return executeJsonRequest(HOST, METHOD, PATH, reqBody, PaymentPrepayResponse.class);
    }

    @Override
    public PaymentOrderEntity queryOrderByTransactionId(QueryOrderByTransactionIdRequest request) {
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "GET";
        String PATH = "/v3/pay/transactions/id/{transaction_id}";

        if (isBlank(request.mchid)) {
            request.mchid = mchid;
        }

        String uri = PATH.replace("{transaction_id}", WechatPayUtils.urlEncode(request.transactionId));
        Map<String, Object> args = new HashMap<>();
        args.put("mchid", request.mchid);
        String queryString = WechatPayUtils.urlEncode(args);
        if (!queryString.isEmpty()) {
            uri = uri + "?" + queryString;
        }
        return executeJsonRequest(HOST, METHOD, uri, null, PaymentOrderEntity.class);
    }

    @Override
    public PaymentOrderEntity queryOrderByOutTradeNo(QueryOrderByOutTradeNoRequest request) {
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "GET";
        String PATH = "/v3/pay/transactions/out-trade-no/{out_trade_no}";

        if (isBlank(request.mchid)) {
            request.mchid = mchid;
        }

        String uri = PATH.replace("{out_trade_no}", WechatPayUtils.urlEncode(request.outTradeNo));
        Map<String, Object> args = new HashMap<>();
        args.put("mchid", request.mchid);
        String queryString = WechatPayUtils.urlEncode(args);
        if (!queryString.isEmpty()) {
            uri = uri + "?" + queryString;
        }
        return executeJsonRequest(HOST, METHOD, uri, null, PaymentOrderEntity.class);
    }

    @Override
    public void closeOrder(PaymentCloseOrderRequest request) {
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "POST";
        String PATH = "/v3/pay/transactions/out-trade-no/{out_trade_no}/close";

        if (isBlank(request.mchid)) {
            request.mchid = mchid;
        }

        String uri = PATH.replace("{out_trade_no}", WechatPayUtils.urlEncode(request.outTradeNo));
        String reqBody = WechatPayUtils.toJson(request);
        executeNoContentRequest(HOST, METHOD, uri, reqBody);
    }

    @Override
    public RefundEntity createRefund(RefundRequest request) {
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "POST";
        String PATH = "/v3/refund/domestic/refunds";

        String reqBody = WechatPayUtils.toJson(request);
        return executeJsonRequest(HOST, METHOD, PATH, reqBody, RefundEntity.class);
    }

    @Override
    public RefundEntity queryRefundByOutRefundNo(QueryRefundByOutRefundNoRequest request) {
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "GET";
        String PATH = "/v3/refund/domestic/refunds/{out_refund_no}";

        String uri = PATH.replace("{out_refund_no}", WechatPayUtils.urlEncode(request.outRefundNo));
        return executeJsonRequest(HOST, METHOD, uri, null, RefundEntity.class);
    }

    @Override
    public RefundEntity createAbnormalRefund(AbnormalRefundRequest request) {
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "POST";
        String PATH = "/v3/refund/domestic/refunds/{refund_id}/apply-abnormal-refund";

        String uri = PATH.replace("{refund_id}", WechatPayUtils.urlEncode(request.refundId));
        if (request.bankAccount != null && !request.bankAccount.isEmpty()) {
            request.bankAccount = WechatPayUtils.encrypt(wechatPayPublicKey, request.bankAccount);
        }
        if (request.realName != null && !request.realName.isEmpty()) {
            request.realName = WechatPayUtils.encrypt(wechatPayPublicKey, request.realName);
        }
        String reqBody = WechatPayUtils.toJson(request);
        return executeJsonRequest(HOST, METHOD, uri, reqBody, RefundEntity.class);
    }

    @Override
    public BillDownloadEntity getTradeBill(TradeBillRequest request) {
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "GET";
        String PATH = "/v3/bill/tradebill";

        String uri = PATH;
        Map<String, Object> args = new HashMap<>();
        args.put("bill_date", request.billDate);
        args.put("bill_type", request.billType);
        args.put("tar_type", request.tarType);
        String queryString = WechatPayUtils.urlEncode(args);
        if (!queryString.isEmpty()) {
            uri = uri + "?" + queryString;
        }
        return executeJsonRequest(HOST, METHOD, uri, null, BillDownloadEntity.class);
    }

    @Override
    public BillDownloadEntity getFundFlowBill(FundFlowBillRequest request) {
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "GET";
        String PATH = "/v3/bill/fundflowbill";

        String uri = PATH;
        Map<String, Object> args = new HashMap<>();
        args.put("bill_date", request.billDate);
        args.put("account_type", request.accountType);
        args.put("tar_type", request.tarType);
        String queryString = WechatPayUtils.urlEncode(args);
        if (!queryString.isEmpty()) {
            uri = uri + "?" + queryString;
        }
        return executeJsonRequest(HOST, METHOD, uri, null, BillDownloadEntity.class);
    }

    private <T> T executeJsonRequest(String host, String method, String uri, String reqBody, Class<T> responseClass) {
        Request.Builder reqBuilder = new Request.Builder().url(host + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", wechatPayPublicKeyId);
        reqBuilder.addHeader("Authorization",
                WechatPayUtils.buildAuthorization(mchid, certificateSerialNo, privateKey, method, uri, reqBody));
        if (reqBody != null) {
            reqBuilder.addHeader("Content-Type", "application/json");
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqBody);
            reqBuilder.method(method, requestBody);
        } else {
            reqBuilder.method(method, null);
        }
        Request httpRequest = reqBuilder.build();

        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = WechatPayUtils.extractBody(httpResponse);
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                WechatPayUtils.validateResponse(this.wechatPayPublicKeyId, this.wechatPayPublicKey,
                        httpResponse.headers(), respBody);
                return WechatPayUtils.fromJson(respBody, responseClass);
            } else {
                throw new WechatPayUtils.ApiException(httpResponse.code(), respBody, httpResponse.headers());
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Sending request to " + uri + " failed.", e);
        }
    }

    private void executeNoContentRequest(String host, String method, String uri, String reqBody) {
        Request.Builder reqBuilder = new Request.Builder().url(host + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", wechatPayPublicKeyId);
        reqBuilder.addHeader("Authorization",
                WechatPayUtils.buildAuthorization(mchid, certificateSerialNo, privateKey, method, uri, reqBody));
        reqBuilder.addHeader("Content-Type", "application/json");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqBody);
        reqBuilder.method(method, requestBody);
        Request httpRequest = reqBuilder.build();

        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                return;
            }
            String respBody = WechatPayUtils.extractBody(httpResponse);
            throw new WechatPayUtils.ApiException(httpResponse.code(), respBody, httpResponse.headers());
        } catch (IOException e) {
            throw new UncheckedIOException("Sending request to " + uri + " failed.", e);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }
}
