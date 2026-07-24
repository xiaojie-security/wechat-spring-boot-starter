package com.wechat.core.transfer.service.impl;

import com.wechat.core.transfer.domain.CloseAuthorizationRequest;
import com.wechat.core.transfer.domain.GetRequest;
import com.wechat.core.transfer.domain.GetTransferBillByNoRequest;
import com.wechat.core.transfer.domain.GetTransferBillByOutNoRequest;
import com.wechat.core.transfer.domain.TransferBillEntity;
import com.wechat.core.transfer.domain.TransferToUserRequest;
import com.wechat.core.transfer.domain.TransferToUserResponse;
import com.wechat.core.transfer.domain.UserConfirmAuthorizationEntity;
import com.wechat.core.transfer.service.WechatTransferService;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class DefaultWechatTransferService implements WechatTransferService {

    private final WechatMerchantConfigProvider provider;
    private final OkHttpClient client = new OkHttpClient.Builder().build();

    @Override
    public TransferToUserResponse transferWithAutoApproval(TransferToUserRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "POST";
        String path = "/v3/fund-app/mch-transfer/transfer-bills/pre-transfer-with-authorization";
        WechatMerchantConfig config = getMerchantConfig();

        if (isBlank(request.appid)) {
            request.appid = config.getAppid();
        }
        if (isBlank(request.notifyUrl)) {
            request.notifyUrl = config.getTransferNotifyUrl();
        }
        if (!isBlank(request.userName)) {
            request.userName = WechatPayUtils.encrypt(config.getWechatPayPublicKey(), request.userName);
        }

        String reqBody = WechatPayUtils.toJson(request);
        Request.Builder reqBuilder = new Request.Builder().url(host + path);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", config.getWechatPayPublicKeyId());
        reqBuilder.addHeader("Authorization",
                WechatPayUtils.buildAuthorization(
                        config.getMchid(),
                        config.getCertificateSerialNo(),
                        config.getPrivateKey(),
                        method,
                        path,
                        reqBody));
        reqBuilder.addHeader("Content-Type", "application/json");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqBody);
        reqBuilder.method(method, requestBody);
        Request httpRequest = reqBuilder.build();

        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = WechatPayUtils.extractBody(httpResponse);
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                WechatPayUtils.validateResponse(config.getWechatPayPublicKeyId(), config.getWechatPayPublicKey(),
                        httpResponse.headers(), respBody);
                return WechatPayUtils.fromJson(respBody, TransferToUserResponse.class);
            } else {
                log.error("DefaultWechatTransferService.transferWithAutoApproval 请求微信商家转账接口失败，path={}, code={}, respBody={}",
                        path, httpResponse.code(), respBody);
                throw new WechatPayUtils.ApiException(httpResponse.code(), respBody, httpResponse.headers());
            }
        } catch (IOException e) {
            log.error("DefaultWechatTransferService.transferWithAutoApproval 调用微信商家转账接口异常，path={}", path, e);
            throw new UncheckedIOException("Sending request to " + path + " failed.", e);
        }
    }

    @Override
    public UserConfirmAuthorizationEntity queryAuthorization(GetRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "GET";
        String path = "/v3/fund-app/mch-transfer/user-confirm-authorization/out-authorization-no/{out_authorization_no}";
        WechatMerchantConfig config = getMerchantConfig();

        String uri = path.replace("{out_authorization_no}", WechatPayUtils.urlEncode(request.outAuthorizationNo));
        Map<String, Object> args = new HashMap<>();
        args.put("is_display_authorization", request.isDisplayAuthorization);
        String queryString = WechatPayUtils.urlEncode(args);
        if (!queryString.isEmpty()) {
            uri = uri + "?" + queryString;
        }

        Request.Builder reqBuilder = new Request.Builder().url(host + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", config.getWechatPayPublicKeyId());
        reqBuilder.addHeader("Authorization", WechatPayUtils.buildAuthorization(config.getMchid(),
                config.getCertificateSerialNo(), config.getPrivateKey(), method, uri, null));
        reqBuilder.method(method, null);
        Request httpRequest = reqBuilder.build();

        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = WechatPayUtils.extractBody(httpResponse);
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                WechatPayUtils.validateResponse(config.getWechatPayPublicKeyId(), config.getWechatPayPublicKey(),
                        httpResponse.headers(), respBody);
                return WechatPayUtils.fromJson(respBody, UserConfirmAuthorizationEntity.class);
            } else {
                log.error("DefaultWechatTransferService.queryAuthorization 请求微信授权查询接口失败，uri={}, code={}, respBody={}",
                        uri, httpResponse.code(), respBody);
                throw new WechatPayUtils.ApiException(httpResponse.code(), respBody, httpResponse.headers());
            }
        } catch (IOException e) {
            log.error("DefaultWechatTransferService.queryAuthorization 调用微信授权查询接口异常，uri={}", uri, e);
            throw new UncheckedIOException("Sending request to " + uri + " failed.", e);
        }
    }

    @Override
    public TransferBillEntity queryTransferBillByOutBillNo(GetTransferBillByOutNoRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "GET";
        String path = "/v3/fund-app/mch-transfer/transfer-bills/out-bill-no/{out_bill_no}";
        WechatMerchantConfig config = getMerchantConfig();

        String uri = path.replace("{out_bill_no}", WechatPayUtils.urlEncode(request.outBillNo));

        Request.Builder reqBuilder = new Request.Builder().url(host + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", config.getWechatPayPublicKeyId());
        reqBuilder.addHeader("Authorization",
                WechatPayUtils.buildAuthorization(config.getMchid(), config.getCertificateSerialNo(),
                        config.getPrivateKey(), method, uri, null));
        reqBuilder.method(method, null);
        Request httpRequest = reqBuilder.build();

        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = WechatPayUtils.extractBody(httpResponse);
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                WechatPayUtils.validateResponse(config.getWechatPayPublicKeyId(), config.getWechatPayPublicKey(),
                        httpResponse.headers(), respBody);
                return WechatPayUtils.fromJson(respBody, TransferBillEntity.class);
            } else {
                log.error("DefaultWechatTransferService.queryTransferBillByOutBillNo 请求微信转账单查询接口失败，uri={}, code={}, respBody={}",
                        uri, httpResponse.code(), respBody);
                throw new WechatPayUtils.ApiException(httpResponse.code(), respBody, httpResponse.headers());
            }
        } catch (IOException e) {
            log.error("DefaultWechatTransferService.queryTransferBillByOutBillNo 调用微信转账单查询接口异常，uri={}", uri, e);
            throw new UncheckedIOException("Sending request to " + uri + " failed.", e);
        }
    }

    @Override
    public TransferBillEntity queryTransferBillByTransferBillNo(GetTransferBillByNoRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "GET";
        String path = "/v3/fund-app/mch-transfer/transfer-bills/transfer-bill-no/{transfer_bill_no}";
        WechatMerchantConfig config = getMerchantConfig();

        String uri = path.replace("{transfer_bill_no}", WechatPayUtils.urlEncode(request.transferBillNo));

        Request.Builder reqBuilder = new Request.Builder().url(host + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", config.getWechatPayPublicKeyId());
        reqBuilder.addHeader("Authorization",
                WechatPayUtils.buildAuthorization(config.getMchid(), config.getCertificateSerialNo(),
                        config.getPrivateKey(), method, uri, null));
        reqBuilder.method(method, null);
        Request httpRequest = reqBuilder.build();

        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = WechatPayUtils.extractBody(httpResponse);
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                WechatPayUtils.validateResponse(config.getWechatPayPublicKeyId(), config.getWechatPayPublicKey(),
                        httpResponse.headers(), respBody);
                return WechatPayUtils.fromJson(respBody, TransferBillEntity.class);
            } else {
                log.error("DefaultWechatTransferService.queryTransferBillByTransferBillNo 请求微信转账单查询接口失败，uri={}, code={}, respBody={}",
                        uri, httpResponse.code(), respBody);
                throw new WechatPayUtils.ApiException(httpResponse.code(), respBody, httpResponse.headers());
            }
        } catch (IOException e) {
            log.error("DefaultWechatTransferService.queryTransferBillByTransferBillNo 调用微信转账单查询接口异常，uri={}", uri, e);
            throw new UncheckedIOException("Sending request to " + uri + " failed.", e);
        }
    }

    @Override
    public UserConfirmAuthorizationEntity closeAuthorization(CloseAuthorizationRequest request) {
        String host = "https://api.mch.weixin.qq.com";
        String method = "POST";
        String path = "/v3/fund-app/mch-transfer/user-confirm-authorization/out-authorization-no/{out_authorization_no}/close";
        WechatMerchantConfig config = getMerchantConfig();

        String uri = path.replace("{out_authorization_no}", WechatPayUtils.urlEncode(request.outAuthorizationNo));

        Request.Builder reqBuilder = new Request.Builder().url(host + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", config.getWechatPayPublicKeyId());
        reqBuilder.addHeader("Authorization",
                WechatPayUtils.buildAuthorization(config.getMchid(), config.getCertificateSerialNo(),
                        config.getPrivateKey(), method, uri, null));
        reqBuilder.addHeader("Content-Type", "application/json");
        RequestBody emptyBody = RequestBody.create(null, "");
        reqBuilder.method(method, emptyBody);
        Request httpRequest = reqBuilder.build();

        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = WechatPayUtils.extractBody(httpResponse);
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                WechatPayUtils.validateResponse(config.getWechatPayPublicKeyId(), config.getWechatPayPublicKey(),
                        httpResponse.headers(), respBody);
                return WechatPayUtils.fromJson(respBody, UserConfirmAuthorizationEntity.class);
            } else {
                log.error("DefaultWechatTransferService.closeAuthorization 请求微信授权关闭接口失败，uri={}, code={}, respBody={}",
                        uri, httpResponse.code(), respBody);
                throw new WechatPayUtils.ApiException(httpResponse.code(), respBody, httpResponse.headers());
            }
        } catch (IOException e) {
            log.error("DefaultWechatTransferService.closeAuthorization 调用微信授权关闭接口异常，uri={}", uri, e);
            throw new UncheckedIOException("Sending request to " + uri + " failed.", e);
        }
    }

    private WechatMerchantConfig getMerchantConfig() {
        WechatMerchantConfig config = provider.getConfig();
        if (config == null) {
            throw new IllegalStateException("未获取到微信商户配置");
        }
        return config;
    }

    private boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }
}
