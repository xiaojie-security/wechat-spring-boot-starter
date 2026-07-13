package com.wechat.core.transfer.service.impl;

import com.wechat.core.transfer.domain.*;
import com.wechat.core.transfer.service.WechatTransferService;
import com.wechat.properties.MerchantIdentityProperties;
import com.wechat.utils.WechatPayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
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
public class DefaultWechatTransferService implements WechatTransferService, InitializingBean {

    private final MerchantIdentityProperties merchantIdentityProperties;

    private String mchid;
    private String appid;
    private String certificateSerialNo;
    private PrivateKey privateKey;
    private String wechatPayPublicKeyId;
    private PublicKey wechatPayPublicKey;

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
    public TransferToUserResponse transferWithAutoApproval(TransferToUserRequest request) {
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "POST";
        String PATH = "/v3/fund-app/mch-transfer/transfer-bills/pre-transfer-with-authorization";

        if (request.appid == null || request.appid.isEmpty()) {
            request.appid = appid;
        }
        if (request.userName != null && !request.userName.isEmpty()) {
            request.userName = WechatPayUtils.encrypt(wechatPayPublicKey, request.userName);
        }

        String reqBody = WechatPayUtils.toJson(request);
        Request.Builder reqBuilder = new Request.Builder().url(HOST + PATH);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", wechatPayPublicKeyId);
        reqBuilder.addHeader("Authorization",
                WechatPayUtils.buildAuthorization(
                        mchid,
                        certificateSerialNo,
                        privateKey,
                        METHOD,
                        PATH,
                        reqBody));
        reqBuilder.addHeader("Content-Type", "application/json");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqBody);
        reqBuilder.method(METHOD, requestBody);
        Request httpRequest = reqBuilder.build();

        // 发送HTTP请求
        OkHttpClient client = new OkHttpClient.Builder().build();
        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = WechatPayUtils.extractBody(httpResponse);
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                // 2XX 成功，验证应答签名
                WechatPayUtils.validateResponse(wechatPayPublicKeyId, wechatPayPublicKey,
                        httpResponse.headers(), respBody);

                // 从HTTP应答报文构建返回数据
                return WechatPayUtils.fromJson(respBody, TransferToUserResponse.class);
            } else {
                log.error("DefaultWechatTransferService.transferWithAutoApproval 请求微信商家转账接口失败，path={}, code={}, respBody={}",
                        PATH, httpResponse.code(), respBody);
                throw new WechatPayUtils.ApiException(httpResponse.code(), respBody, httpResponse.headers());
            }
        } catch (IOException e) {
            log.error("DefaultWechatTransferService.transferWithAutoApproval 调用微信商家转账接口异常，path={}", PATH, e);
            throw new UncheckedIOException("Sending request to " + PATH + " failed.", e);
        }
    }

    @Override
    public UserConfirmAuthorizationEntity queryAuthorization(GetRequest request) {

        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "GET";
        String PATH = "/v3/fund-app/mch-transfer/user-confirm-authorization/out-authorization-no/{out_authorization_no}";

        String uri = PATH;
        uri = uri.replace("{out_authorization_no}", WechatPayUtils.urlEncode(request.outAuthorizationNo));
        Map<String, Object> args = new HashMap<>();
        args.put("is_display_authorization", request.isDisplayAuthorization);
        String queryString = WechatPayUtils.urlEncode(args);
        if (!queryString.isEmpty()) {
            uri = uri + "?" + queryString;
        }

        Request.Builder reqBuilder = new Request.Builder().url(HOST + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", wechatPayPublicKeyId);
        reqBuilder.addHeader("Authorization", WechatPayUtils.buildAuthorization(mchid, certificateSerialNo, privateKey, METHOD, uri, null));
        reqBuilder.method(METHOD, null);
        Request httpRequest = reqBuilder.build();

        // 发送HTTP请求
        OkHttpClient client = new OkHttpClient.Builder().build();
        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = WechatPayUtils.extractBody(httpResponse);
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                // 2XX 成功，验证应答签名
                WechatPayUtils.validateResponse(this.wechatPayPublicKeyId, this.wechatPayPublicKey,
                        httpResponse.headers(), respBody);

                // 从HTTP应答报文构建返回数据
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
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "GET";
        String PATH = "/v3/fund-app/mch-transfer/transfer-bills/out-bill-no/{out_bill_no}";

        String uri = PATH;
        uri = uri.replace("{out_bill_no}", WechatPayUtils.urlEncode(request.outBillNo));

        Request.Builder reqBuilder = new Request.Builder().url(HOST + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", wechatPayPublicKeyId);
        reqBuilder.addHeader("Authorization",
                WechatPayUtils.buildAuthorization(mchid, certificateSerialNo, privateKey, METHOD, uri, null));
        reqBuilder.method(METHOD, null);
        Request httpRequest = reqBuilder.build();

        OkHttpClient client = new OkHttpClient.Builder().build();
        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = WechatPayUtils.extractBody(httpResponse);
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                WechatPayUtils.validateResponse(this.wechatPayPublicKeyId, this.wechatPayPublicKey,
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
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "GET";
        String PATH = "/v3/fund-app/mch-transfer/transfer-bills/transfer-bill-no/{transfer_bill_no}";

        String uri = PATH;
        uri = uri.replace("{transfer_bill_no}", WechatPayUtils.urlEncode(request.transferBillNo));

        Request.Builder reqBuilder = new Request.Builder().url(HOST + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", wechatPayPublicKeyId);
        reqBuilder.addHeader("Authorization",
                WechatPayUtils.buildAuthorization(mchid, certificateSerialNo, privateKey, METHOD, uri, null));
        reqBuilder.method(METHOD, null);
        Request httpRequest = reqBuilder.build();

        OkHttpClient client = new OkHttpClient.Builder().build();
        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = WechatPayUtils.extractBody(httpResponse);
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                WechatPayUtils.validateResponse(this.wechatPayPublicKeyId, this.wechatPayPublicKey,
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
        String HOST = "https://api.mch.weixin.qq.com";
        String METHOD = "POST";
        String PATH = "/v3/fund-app/mch-transfer/user-confirm-authorization/out-authorization-no/{out_authorization_no}/close";

        String uri = PATH;
        uri = uri.replace("{out_authorization_no}", WechatPayUtils.urlEncode(request.outAuthorizationNo));

        Request.Builder reqBuilder = new Request.Builder().url(HOST + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", wechatPayPublicKeyId);
        reqBuilder.addHeader("Authorization",
                WechatPayUtils.buildAuthorization(mchid, certificateSerialNo, privateKey, METHOD, uri, null));
        reqBuilder.addHeader("Content-Type", "application/json");
        RequestBody emptyBody = RequestBody.create(null, "");
        reqBuilder.method(METHOD, emptyBody);
        Request httpRequest = reqBuilder.build();

        OkHttpClient client = new OkHttpClient.Builder().build();
        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = WechatPayUtils.extractBody(httpResponse);
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                WechatPayUtils.validateResponse(this.wechatPayPublicKeyId, this.wechatPayPublicKey,
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
}
