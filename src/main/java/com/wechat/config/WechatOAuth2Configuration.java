package com.wechat.config;

import com.wechat.core.oauth2.WechatOAuth2Service;
import com.wechat.core.oauth2.impl.DefaultWechatOAuth2Service;
import com.wechat.properties.MerchantIdentityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(MerchantIdentityProperties.class)
@ConditionalOnProperty(prefix = "wechat.oauth2", name = "enable", havingValue = "true", matchIfMissing = false)
public class WechatOAuth2Configuration {

    private final MerchantIdentityProperties properties;

    @Bean
    @ConditionalOnMissingBean(WechatOAuth2Service.class)
    public WechatOAuth2Service wechatOAuth2Service() {
        return new DefaultWechatOAuth2Service(properties);
    }
}
