package com.wechat.provider;

import com.wechat.properties.MerchantIdentityProperties;
import com.wechat.provider.domain.WechatMerchantConfig;
import com.wechat.utils.WechatPayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PropertiesWechatMerchantConfigProvider implements WechatMerchantConfigProvider {

    private final MerchantIdentityProperties properties;

    @Override
    public WechatMerchantConfig getConfig() {
        return new WechatMerchantConfig(
                properties.getMerchantId(),
                properties.getAppid(),
                properties.getAppSecret(),
                WechatPayUtils.loadPrivateKeyFromString(properties.getCertificate()),
                properties.getSerialNo(),
                WechatPayUtils.loadPublicKeyFromString(properties.getPublicKey()),
                properties.getPublicKeyId(),
                properties.getApiV3Secret()
        );
    }
}
