package com.wechat.core.transfer.service.impl;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.transfer.domain.AutoApprovalResultNotifyEntity;
import com.wechat.core.transfer.service.WechatAutoApprovalResultNotifyService;
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
public class DefaultWechatAutoApprovalResultNotifyService implements WechatAutoApprovalResultNotifyService {
    private static final String FAIL_RESPONSE = "{\"code\":\"FAIL\",\"message\":\"失败\"}";

    private final MerchantIdentityProperties merchantIdentityProperties;

    @Override
    public AutoApprovalResultNotifyEntity parseNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            String body = request.getReader().lines().reduce("", String::concat);
            CallbackNotifyRequest notifyRequest = WechatPayUtils.fromJson(body, CallbackNotifyRequest.class);
            AesUtil aesUtil = new AesUtil(merchantIdentityProperties.getApiV3Secret().getBytes(StandardCharsets.UTF_8));
            String decryptJson = aesUtil.decryptToString(
                    notifyRequest.resource.associatedData.getBytes(StandardCharsets.UTF_8),
                    notifyRequest.resource.nonce.getBytes(StandardCharsets.UTF_8),
                    notifyRequest.resource.ciphertext
            );
            AutoApprovalResultNotifyEntity notifyEntity =
                    WechatPayUtils.fromJson(decryptJson, AutoApprovalResultNotifyEntity.class);
            response.setStatus(HttpServletResponse.SC_OK);
            return notifyEntity;
        } catch (IOException | GeneralSecurityException e) {
            log.error("DefaultWechatAutoApprovalResultNotifyService.parseNotify 处理免确认收款授权结果通知失败", e);
            writeFailResponse(response);
            throw new IllegalStateException("处理免确认收款授权结果通知失败", e);
        } catch (RuntimeException e) {
            log.error("DefaultWechatAutoApprovalResultNotifyService.parseNotify 解析免确认收款授权结果通知失败", e);
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
            log.error("DefaultWechatAutoApprovalResultNotifyService.writeFailResponse 回写免确认收款授权结果通知失败应答异常", e);
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
