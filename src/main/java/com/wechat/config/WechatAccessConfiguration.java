package com.wechat.config;

import com.wechat.core.access.WechatAccessTokenService;
import com.wechat.core.access.impl.DefaultWechatAccessTokenService;
import com.wechat.provider.WechatMerchantConfigProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * 微信接口调用凭据自动装配配置。
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "wechat.access", name = "enable", havingValue = "true", matchIfMissing = false)
public class WechatAccessConfiguration {

    /**
     * 注册微信接口调用凭据服务。
     *
     * @param provider 微信商户配置提供者
     * @return 微信接口调用凭据服务
     */
    @Bean
    @ConditionalOnMissingBean(WechatAccessTokenService.class)
    public WechatAccessTokenService wechatAccessTokenService(WechatMerchantConfigProvider provider) {
        return new DefaultWechatAccessTokenService(provider);
    }
}
