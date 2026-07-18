package com.wechat.config;

import com.wechat.core.oauth2.WechatWebpageOAuth2Service;
import com.wechat.core.oauth2.WechatXcxOAuth2Service;
import com.wechat.core.oauth2.impl.DefaultWechatWebpageOAuth2Service;
import com.wechat.core.oauth2.impl.DefaultWechatXcxOAuth2Service;
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
    @ConditionalOnMissingBean(WechatWebpageOAuth2Service.class)
    public WechatWebpageOAuth2Service wechatWebpageOAuth2Service(WechatMerchantConfigProvider provider) {
        return new DefaultWechatWebpageOAuth2Service(provider);
    }

    @Bean
    @ConditionalOnMissingBean(WechatXcxOAuth2Service.class)
    public WechatXcxOAuth2Service wechatXcxOAuth2Service(WechatMerchantConfigProvider provider) {
        return new DefaultWechatXcxOAuth2Service(provider);
    }
}
