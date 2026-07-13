package com.wechat.config;

import com.wechat.core.payment.service.WechatPaymentService;
import com.wechat.core.payment.service.WechatPaymentCallbackService;
import com.wechat.core.payment.service.impl.DefaultWechatPaymentCallbackService;
import com.wechat.core.payment.service.impl.DefaultWechatPaymentService;
import com.wechat.core.profitsharing.service.WechatProfitsharingService;
import com.wechat.core.profitsharing.service.impl.DefaultWechatProfitsharingService;
import com.wechat.core.transfer.service.WechatTransferService;
import com.wechat.core.transfer.service.WechatAutoApprovalResultNotifyService;
import com.wechat.core.transfer.service.impl.DefaultWechatAutoApprovalResultNotifyService;
import com.wechat.core.transfer.service.impl.DefaultWechatTransferService;
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
public class WechatPaymentConfiguration {

    private final MerchantIdentityProperties properties;

    @Bean
    @ConditionalOnMissingBean(WechatPaymentService.class)
    @ConditionalOnProperty(prefix = "wechat.payment", name = "enable", havingValue = "true", matchIfMissing = false)
    public WechatPaymentService wechatPaymentService(){
        return new DefaultWechatPaymentService(properties);
    }

    @Bean
    @ConditionalOnMissingBean(WechatPaymentCallbackService.class)
    @ConditionalOnProperty(prefix = "wechat.payment", name = "enable", havingValue = "true", matchIfMissing = false)
    public WechatPaymentCallbackService wechatPaymentCallbackService() {
        return new DefaultWechatPaymentCallbackService(properties);
    }

    @Bean
    @ConditionalOnMissingBean(WechatProfitsharingService.class)
    @ConditionalOnProperty(prefix = "wechat.profitsharing", name = "enable", havingValue = "true", matchIfMissing = false)
    public WechatProfitsharingService wechatProfitsharingService(){
        return new DefaultWechatProfitsharingService(properties);
    }

    @Bean
    @ConditionalOnMissingBean(WechatTransferService.class)
    @ConditionalOnProperty(prefix = "wechat.transfer", name = "enable", havingValue = "true", matchIfMissing = false)
    public WechatTransferService wechatTransferService(){
        return new DefaultWechatTransferService(properties);
    }

    @Bean
    @ConditionalOnMissingBean(WechatAutoApprovalResultNotifyService.class)
    @ConditionalOnProperty(prefix = "wechat.transfer", name = "enable", havingValue = "true", matchIfMissing = false)
    public WechatAutoApprovalResultNotifyService wechatAutoApprovalResultNotifyService() {
        return new DefaultWechatAutoApprovalResultNotifyService(properties);
    }
}
