package com.wechat.provider;

import com.wechat.properties.MerchantIdentityProperties;
import com.wechat.provider.domain.WechatMerchantConfig;
import com.wechat.utils.ConfigStringLoader;
import com.wechat.utils.WechatPayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PropertiesWechatMerchantConfigProvider implements WechatMerchantConfigProvider {

    private final MerchantIdentityProperties properties;

    @Override
    public WechatMerchantConfig getConfig() {
        String publicKey = ConfigStringLoader.load(properties.getPublicKey(), "微信支付公钥");
        String certificate = ConfigStringLoader.load(properties.getCertificate(), "商户API证书私钥");
        return new WechatMerchantConfig(
                properties.getMerchantId(),
                properties.getAppid(),
                properties.getAppSecret(),
                WechatPayUtils.loadPrivateKeyFromString(certificate),
                properties.getSerialNo(),
                WechatPayUtils.loadPublicKeyFromString(publicKey),
                properties.getPublicKeyId(),
                properties.getApiV3Secret(),
                properties.getPaymentNotifyUrl(),
                properties.getTransferNotifyUrl(),
                properties.getAuthorizationNotifyUrl()
        );
    }
}
