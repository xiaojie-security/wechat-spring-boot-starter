package com.wechat.core.transfer.service.impl;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.transfer.domain.TransferCallbackEntity;
import com.wechat.core.transfer.service.WechatTransferCallbackService;
import com.wechat.provider.WechatMerchantConfigProvider;
import com.wechat.provider.domain.WechatMerchantConfig;
import com.wechat.utils.AesUtil;
import com.wechat.utils.WechatPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

/**
 * 微信商家转账结果回调通知默认实现。
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultWechatTransferCallbackService implements WechatTransferCallbackService {
    private static final String FAIL_RESPONSE = "{\"code\":\"FAIL\",\"message\":\"失败\"}";

    private final WechatMerchantConfigProvider provider;

    @Override
    public TransferCallbackEntity parseCallback(HttpServletRequest request, HttpServletResponse response) {
        try {
            String body = request.getReader().lines().reduce("", String::concat);
            CallbackNotifyRequest notifyRequest = WechatPayUtils.fromJson(body, CallbackNotifyRequest.class);
            WechatMerchantConfig config = getMerchantConfig();
            AesUtil aesUtil = new AesUtil(config.getApiV3Secret().getBytes(StandardCharsets.UTF_8));
            String decryptJson = aesUtil.decryptToString(
                    notifyRequest.resource.associatedData.getBytes(StandardCharsets.UTF_8),
                    notifyRequest.resource.nonce.getBytes(StandardCharsets.UTF_8),
                    notifyRequest.resource.ciphertext
            );
            TransferCallbackEntity callbackEntity = WechatPayUtils.fromJson(decryptJson, TransferCallbackEntity.class);
            response.setStatus(HttpServletResponse.SC_OK);
            return callbackEntity;
        } catch (IOException | GeneralSecurityException e) {
            log.error("DefaultWechatTransferCallbackService.parseCallback 处理微信商家转账回调失败", e);
            writeFailResponse(response);
            throw new IllegalStateException("处理微信商家转账回调失败", e);
        } catch (RuntimeException e) {
            log.error("DefaultWechatTransferCallbackService.parseCallback 解析微信商家转账回调失败", e);
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
        } catch (IOException e) {
            log.error("DefaultWechatTransferCallbackService.writeFailResponse 回写微信商家转账回调失败应答异常", e);
        }
    }

    private WechatMerchantConfig getMerchantConfig() {
        WechatMerchantConfig config = provider.getConfig();
        if (config == null) {
            throw new IllegalStateException("未获取到微信商户配置");
        }
        if (config.getApiV3Secret() == null || config.getApiV3Secret().trim().isEmpty()) {
            throw new IllegalStateException("微信商户配置缺少APIv3密钥");
        }
        return config;
    }

    /**
     * 微信支付转账回调通知原始报文。
     */
    private static class CallbackNotifyRequest {
        /**
         * 加密资源信息。
         */
        Resource resource;
    }

    /**
     * 微信支付转账回调加密资源。
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
