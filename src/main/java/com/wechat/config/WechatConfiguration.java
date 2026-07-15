package com.wechat.config;

import com.wechat.properties.MerchantIdentityProperties;
import com.wechat.provider.WechatMerchantConfigProvider;
import com.wechat.provider.PropertiesWechatMerchantConfigProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 微信支付 Starter 自动装配配置。
 */
@AutoConfiguration
@EnableConfigurationProperties(MerchantIdentityProperties.class)
public class WechatConfiguration {


    @Bean
    @ConditionalOnMissingBean(WechatMerchantConfigProvider.class)
    public WechatMerchantConfigProvider wechatMerchantConfigProvider(MerchantIdentityProperties properties){
        return new PropertiesWechatMerchantConfigProvider(properties);
    }
}
