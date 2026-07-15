package com.wechat.core.profitsharing.service.impl;

import com.wechat.core.profitsharing.domain.DeleteProfitsharingReceiverRequest;
import com.wechat.core.profitsharing.domain.ProfitsharingAmountEntity;
import com.wechat.core.profitsharing.domain.ProfitsharingBillDownloadEntity;
import com.wechat.core.profitsharing.domain.ProfitsharingBillRequest;
import com.wechat.core.profitsharing.domain.ProfitsharingOrderEntity;
import com.wechat.core.profitsharing.domain.ProfitsharingOrderRequest;
import com.wechat.core.profitsharing.domain.ProfitsharingReceiverRequest;
import com.wechat.core.profitsharing.domain.ProfitsharingReturnOrderEntity;
import com.wechat.core.profitsharing.domain.ProfitsharingReturnOrderRequest;
import com.wechat.core.profitsharing.domain.QueryProfitsharingAmountRequest;
import com.wechat.core.profitsharing.domain.QueryProfitsharingOrderRequest;
import com.wechat.core.profitsharing.domain.QueryProfitsharingReturnOrderRequest;
import com.wechat.core.profitsharing.domain.UnfreezeProfitsharingOrderRequest;
import com.wechat.core.profitsharing.service.WechatProfitsharingService;
import com.wechat.provider.WechatMerchantConfigProvider;
import com.wechat.provider.domain.WechatMerchantConfig;
import com.wechat.utils.WechatPayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class DefaultWechatProfitsharingService implements WechatProfitsharingService {
    private final WechatMerchantConfigProvider provider;
    private final OkHttpClient client = new OkHttpClient.Builder().build();

    @Override
    public ProfitsharingOrderEntity createOrder(ProfitsharingOrderRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "POST";
        String path = "/v3/profitsharing/orders";
        MerchantRuntime runtime = getMerchantRuntime();

        if (isBlank(request.appid)) {
            request.appid = runtime.appid;
        }
        encryptReceiverNames(runtime, request);

        String reqBody = WechatPayUtils.toJson(request);
        return executeJsonRequest(runtime, host, method, path, reqBody, ProfitsharingOrderEntity.class);
    }

    @Override
    public ProfitsharingOrderEntity queryOrderByOutOrderNo(QueryProfitsharingOrderRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "GET";
        String path = "/v3/profitsharing/orders/{out_order_no}";
        MerchantRuntime runtime = getMerchantRuntime();

        String uri = path.replace("{out_order_no}", WechatPayUtils.urlEncode(request.outOrderNo));
        Map<String, Object> args = new HashMap<>();
        args.put("transaction_id", request.transactionId);
        String queryString = WechatPayUtils.urlEncode(args);
        if (!queryString.isEmpty()) {
            uri = uri + "?" + queryString;
        }
        return executeJsonRequest(runtime, host, method, uri, null, ProfitsharingOrderEntity.class);
    }

    @Override
    public ProfitsharingReturnOrderEntity createReturnOrder(ProfitsharingReturnOrderRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "POST";
        String path = "/v3/profitsharing/return-orders";
        MerchantRuntime runtime = getMerchantRuntime();

        if (isBlank(request.returnMchid)) {
            request.returnMchid = runtime.mchid;
        }

        String reqBody = WechatPayUtils.toJson(request);
        return executeJsonRequest(runtime, host, method, path, reqBody, ProfitsharingReturnOrderEntity.class);
    }

    @Override
    public ProfitsharingReturnOrderEntity queryReturnOrderByOutReturnNo(QueryProfitsharingReturnOrderRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "GET";
        String path = "/v3/profitsharing/return-orders/{out_return_no}";
        MerchantRuntime runtime = getMerchantRuntime();

        String uri = path.replace("{out_return_no}", WechatPayUtils.urlEncode(request.outReturnNo));
        return executeJsonRequest(runtime, host, method, uri, null, ProfitsharingReturnOrderEntity.class);
    }

    @Override
    public ProfitsharingOrderEntity unfreezeRemainingFunds(UnfreezeProfitsharingOrderRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "POST";
        String path = "/v3/profitsharing/orders/unfreeze";
        MerchantRuntime runtime = getMerchantRuntime();

        String reqBody = WechatPayUtils.toJson(request);
        return executeJsonRequest(runtime, host, method, path, reqBody, ProfitsharingOrderEntity.class);
    }

    @Override
    public ProfitsharingAmountEntity queryRemainingAmount(QueryProfitsharingAmountRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "GET";
        String path = "/v3/profitsharing/transactions/{transaction_id}/amounts";
        MerchantRuntime runtime = getMerchantRuntime();

        String uri = path.replace("{transaction_id}", WechatPayUtils.urlEncode(request.transactionId));
        return executeJsonRequest(runtime, host, method, uri, null, ProfitsharingAmountEntity.class);
    }

    @Override
    public void addReceiver(ProfitsharingReceiverRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "POST";
        String path = "/v3/profitsharing/receivers/add";
        MerchantRuntime runtime = getMerchantRuntime();

        if (isBlank(request.appid)) {
            request.appid = runtime.appid;
        }
        if (!isBlank(request.name)) {
            request.name = WechatPayUtils.encrypt(runtime.wechatPayPublicKey, request.name);
        }

        String reqBody = WechatPayUtils.toJson(request);
        executeNoContentRequest(runtime, host, method, path, reqBody);
    }

    @Override
    public void deleteReceiver(DeleteProfitsharingReceiverRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "POST";
        String path = "/v3/profitsharing/receivers/delete";
        MerchantRuntime runtime = getMerchantRuntime();

        if (isBlank(request.appid)) {
            request.appid = runtime.appid;
        }

        String reqBody = WechatPayUtils.toJson(request);
        executeNoContentRequest(runtime, host, method, path, reqBody);
    }

    @Override
    public ProfitsharingBillDownloadEntity getBill(ProfitsharingBillRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "GET";
        String path = "/v3/profitsharing/bills";
        MerchantRuntime runtime = getMerchantRuntime();

        String uri = path;
        Map<String, Object> args = new HashMap<>();
        args.put("bill_date", request.billDate);
        args.put("tar_type", request.tarType);
        String queryString = WechatPayUtils.urlEncode(args);
        if (!queryString.isEmpty()) {
            uri = uri + "?" + queryString;
        }
        return executeJsonRequest(runtime, host, method, uri, null, ProfitsharingBillDownloadEntity.class);
    }

    private <T> T executeJsonRequest(MerchantRuntime runtime, String host, String method, String uri,
                                     String reqBody, Class<T> responseClass) {
        Request.Builder reqBuilder = new Request.Builder().url(host + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", runtime.wechatPayPublicKeyId);
        reqBuilder.addHeader("Authorization",
                WechatPayUtils.buildAuthorization(runtime.mchid, runtime.certificateSerialNo,
                        runtime.privateKey, method, uri, reqBody));
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
                WechatPayUtils.validateResponse(runtime.wechatPayPublicKeyId, runtime.wechatPayPublicKey,
                        httpResponse.headers(), respBody);
                return WechatPayUtils.fromJson(respBody, responseClass);
            }
            log.error("DefaultWechatProfitsharingService.executeJsonRequest 请求微信分账接口失败，uri={}, code={}, respBody={}",
                    uri, httpResponse.code(), respBody);
            throw new WechatPayUtils.ApiException(httpResponse.code(), respBody, httpResponse.headers());
        } catch (IOException e) {
            log.error("DefaultWechatProfitsharingService.executeJsonRequest 调用微信分账接口异常，uri={}", uri, e);
            throw new UncheckedIOException("Sending request to " + uri + " failed.", e);
        }
    }

    private void executeNoContentRequest(MerchantRuntime runtime, String host, String method, String uri,
                                         String reqBody) {
        Request.Builder reqBuilder = new Request.Builder().url(host + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", runtime.wechatPayPublicKeyId);
        reqBuilder.addHeader("Authorization",
                WechatPayUtils.buildAuthorization(runtime.mchid, runtime.certificateSerialNo,
                        runtime.privateKey, method, uri, reqBody));
        reqBuilder.addHeader("Content-Type", "application/json");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqBody);
        reqBuilder.method(method, requestBody);
        Request httpRequest = reqBuilder.build();

        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                return;
            }
            String respBody = WechatPayUtils.extractBody(httpResponse);
            log.error("DefaultWechatProfitsharingService.executeNoContentRequest 请求微信分账接口失败，uri={}, code={}, respBody={}",
                    uri, httpResponse.code(), respBody);
            throw new WechatPayUtils.ApiException(httpResponse.code(), respBody, httpResponse.headers());
        } catch (IOException e) {
            log.error("DefaultWechatProfitsharingService.executeNoContentRequest 调用微信分账接口异常，uri={}", uri, e);
            throw new UncheckedIOException("Sending request to " + uri + " failed.", e);
        }
    }

    private void encryptReceiverNames(MerchantRuntime runtime, ProfitsharingOrderRequest request) {
        if (request.receivers == null || request.receivers.isEmpty()) {
            return;
        }
        for (ProfitsharingOrderRequest.Receiver receiver : request.receivers) {
            if (!isBlank(receiver.name)) {
                receiver.name = WechatPayUtils.encrypt(runtime.wechatPayPublicKey, receiver.name);
            }
        }
    }

    private MerchantRuntime getMerchantRuntime() {
        WechatMerchantConfig config = provider.getConfig();
        if (config == null) {
            throw new IllegalStateException("未获取到微信商户配置");
        }
        return new MerchantRuntime(
                config.getMerchantId(),
                config.getAppid(),
                config.getSerialNo(),
                config.getPublicKeyId(),
                WechatPayUtils.loadPrivateKeyFromString(config.getCertificate()),
                WechatPayUtils.loadPublicKeyFromString(config.getPublicKey())
        );
    }

    private boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }

    private static final class MerchantRuntime {
        private final String mchid;
        private final String appid;
        private final String certificateSerialNo;
        private final String wechatPayPublicKeyId;
        private final PrivateKey privateKey;
        private final PublicKey wechatPayPublicKey;

        private MerchantRuntime(String mchid, String appid, String certificateSerialNo, String wechatPayPublicKeyId,
                                PrivateKey privateKey, PublicKey wechatPayPublicKey) {
            this.mchid = mchid;
            this.appid = appid;
            this.certificateSerialNo = certificateSerialNo;
            this.wechatPayPublicKeyId = wechatPayPublicKeyId;
            this.privateKey = privateKey;
            this.wechatPayPublicKey = wechatPayPublicKey;
        }
    }
}
