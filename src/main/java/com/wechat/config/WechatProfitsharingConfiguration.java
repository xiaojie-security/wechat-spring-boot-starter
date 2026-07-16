package com.wechat.config;

import com.wechat.core.profitsharing.service.WechatProfitsharingService;
import com.wechat.core.profitsharing.service.impl.DefaultWechatProfitsharingService;
import com.wechat.provider.WechatMerchantConfigProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "wechat.profitsharing", name = "enable", havingValue = "true", matchIfMissing = false)
public class WechatProfitsharingConfiguration {


    @Bean
    @ConditionalOnMissingBean(WechatProfitsharingService.class)
    public WechatProfitsharingService wechatProfitsharingService(WechatMerchantConfigProvider provider){
        return new DefaultWechatProfitsharingService(provider);
    }
}
