package com.wechat.config;

import com.wechat.core.payment.service.WechatPaymentService;
import com.wechat.core.payment.service.WechatPaymentCallbackService;
import com.wechat.core.payment.service.impl.DefaultWechatPaymentCallbackService;
import com.wechat.core.payment.service.impl.DefaultWechatPaymentService;
import com.wechat.provider.WechatMerchantConfigProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * 微信支付 Starter 自动装配配置。
 */
@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "wechat.payment", name = "enable", havingValue = "true", matchIfMissing = false)
public class WechatPaymentConfiguration {


    @Bean
    @ConditionalOnMissingBean(WechatPaymentService.class)
    public WechatPaymentService wechatPaymentService(WechatMerchantConfigProvider provider){
        return new DefaultWechatPaymentService(provider);
    }

    @Bean
    @ConditionalOnMissingBean(WechatPaymentCallbackService.class)
    public WechatPaymentCallbackService wechatPaymentCallbackService(WechatMerchantConfigProvider provider) {
        return new DefaultWechatPaymentCallbackService(provider);
    }
}
