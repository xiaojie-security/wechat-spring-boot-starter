package com.wechat.core.payment.service.impl;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.payment.domain.PaymentCallbackEntity;
import com.wechat.core.payment.service.WechatPaymentCallbackService;
import com.wechat.properties.MerchantIdentityProperties;
import com.wechat.utils.AesUtil;
import com.wechat.utils.WechatPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

@Slf4j
@RequiredArgsConstructor
public class DefaultWechatPaymentCallbackService implements WechatPaymentCallbackService {
    private static final String FAIL_RESPONSE = "{\"code\":\"FAIL\",\"message\":\"失败\"}";

    private final MerchantIdentityProperties merchantIdentityProperties;

    @Override
    public PaymentCallbackEntity parseCallback(HttpServletRequest request, HttpServletResponse response) {
        try {
            String body = request.getReader().lines().reduce("", String::concat);
            CallbackNotifyRequest notifyRequest = WechatPayUtils.fromJson(body, CallbackNotifyRequest.class);
            AesUtil aesUtil = new AesUtil(merchantIdentityProperties.getApiV3Secret().getBytes(StandardCharsets.UTF_8));
            String decryptJson = aesUtil.decryptToString(
                    notifyRequest.resource.associatedData.getBytes(StandardCharsets.UTF_8),
                    notifyRequest.resource.nonce.getBytes(StandardCharsets.UTF_8),
                    notifyRequest.resource.ciphertext
            );
            PaymentCallbackEntity callbackEntity = WechatPayUtils.fromJson(decryptJson, PaymentCallbackEntity.class);
            response.setStatus(HttpServletResponse.SC_OK);
            return callbackEntity;
        } catch (IOException | GeneralSecurityException e) {
            log.error("DefaultWechatPaymentCallbackService.parseCallback 处理微信支付回调失败", e);
            writeFailResponse(response);
            throw new IllegalStateException("处理微信支付回调失败", e);
        } catch (RuntimeException e) {
            log.error("DefaultWechatPaymentCallbackService.parseCallback 解析微信支付回调失败", e);
            writeFailResponse(response);
            throw e;
        }
    }

    private void writeFailResponse(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");
        try {
            response.getWriter().write(FAIL_RESPONSE);
        } catch (IOException ex) {
            log.error("DefaultWechatPaymentCallbackService.writeFailResponse 回写微信支付回调失败应答异常", ex);
        }
    }

    /**
     * 微信支付回调通知原始报文。
     */
    private static class CallbackNotifyRequest {
        /**
         * 加密资源信息。
         */
        Resource resource;
    }

    /**
     * 微信支付回调加密资源。
     */
    private static class Resource {
        /**
         * 附加数据。
         */
        @SerializedName("associated_data")
        String associatedData;

        /**
         * 随机串。
         */
        String nonce;

        /**
         * 密文。
         */
        String ciphertext;
    }
}
