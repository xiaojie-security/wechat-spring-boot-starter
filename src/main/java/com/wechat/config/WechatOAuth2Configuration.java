package com.wechat.config;

import com.wechat.core.oauth2.WechatOAuth2Service;
import com.wechat.core.oauth2.impl.DefaultWechatOAuth2Service;
import com.wechat.provider.WechatMerchantConfigProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "wechat.oauth2", name = "enable", havingValue = "true", matchIfMissing = false)
public class WechatOAuth2Configuration {

    @Bean
    @ConditionalOnMissingBean(WechatOAuth2Service.class)
    public WechatOAuth2Service wechatOAuth2Service(WechatMerchantConfigProvider provider) {
        return new DefaultWechatOAuth2Service(provider);
    }
}
