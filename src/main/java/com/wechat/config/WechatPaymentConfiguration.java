package com.wechat.config;

import com.wechat.core.payment.service.WechatPaymentService;
import com.wechat.core.payment.service.WechatPaymentCallbackService;
import com.wechat.core.payment.service.impl.DefaultWechatPaymentCallbackService;
import com.wechat.core.payment.service.impl.DefaultWechatPaymentService;
import com.wechat.properties.MerchantIdentityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 微信支付 Starter 自动装配配置。
 */
@AutoConfiguration
@EnableConfigurationProperties(MerchantIdentityProperties.class)
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "wechat.payment", name = "enable", havingValue = "true", matchIfMissing = false)
public class WechatPaymentConfiguration {

    private final MerchantIdentityProperties properties;

    @Bean
    @ConditionalOnMissingBean(WechatPaymentService.class)
    public WechatPaymentService wechatPaymentService(){
        return new DefaultWechatPaymentService(properties);
    }

    @Bean
    @ConditionalOnMissingBean(WechatPaymentCallbackService.class)
    public WechatPaymentCallbackService wechatPaymentCallbackService() {
        return new DefaultWechatPaymentCallbackService(properties);
    }
}
