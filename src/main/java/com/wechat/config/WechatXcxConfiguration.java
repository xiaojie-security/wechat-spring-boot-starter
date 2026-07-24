package com.wechat.config;

import com.wechat.core.access.WechatAccessTokenService;
import com.wechat.core.access.impl.DefaultWechatAccessTokenService;
import com.wechat.core.xcx.WechatXcxQRCodeService;
import com.wechat.core.xcx.impl.DefaultWechatXcxQRCodeService;
import com.wechat.provider.WechatMerchantConfigProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * 微信小程序能力自动装配配置。
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "wechat.xcx", name = "enable", havingValue = "true", matchIfMissing = false)
public class WechatXcxConfiguration {

    /**
     * 当小程序能力单独启用时，补充注册接口调用凭据服务。
     *
     * @param provider 微信商户配置提供者
     * @return 微信接口调用凭据服务
     */
    @Bean
    @ConditionalOnMissingBean(WechatAccessTokenService.class)
    public WechatAccessTokenService xcxWechatAccessTokenService(WechatMerchantConfigProvider provider) {
        return new DefaultWechatAccessTokenService(provider);
    }

    /**
     * 注册微信小程序二维码服务。
     *
     * @param accessTokenService 微信接口调用凭据服务
     * @return 微信小程序二维码服务
     */
    @Bean
    @ConditionalOnMissingBean(WechatXcxQRCodeService.class)
    public WechatXcxQRCodeService wechatXcxQRCodeService(WechatAccessTokenService accessTokenService) {
        return new DefaultWechatXcxQRCodeService(accessTokenService);
    }
}
