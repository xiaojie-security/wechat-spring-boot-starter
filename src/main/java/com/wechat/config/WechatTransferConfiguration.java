package com.wechat.config;

import com.wechat.core.transfer.service.WechatAutoApprovalResultNotifyService;
import com.wechat.core.transfer.service.WechatTransferService;
import com.wechat.core.transfer.service.impl.DefaultWechatAutoApprovalResultNotifyService;
import com.wechat.core.transfer.service.impl.DefaultWechatTransferService;
import com.wechat.properties.MerchantIdentityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "wechat.transfer", name = "enable", havingValue = "true", matchIfMissing = false)
public class WechatTransferConfiguration {

    private final MerchantIdentityProperties properties;

    @Bean
    @ConditionalOnMissingBean(WechatTransferService.class)
    public WechatTransferService wechatTransferService(){
        return new DefaultWechatTransferService(properties);
    }

    @Bean
    @ConditionalOnMissingBean(WechatAutoApprovalResultNotifyService.class)
    public WechatAutoApprovalResultNotifyService wechatAutoApprovalResultNotifyService() {
        return new DefaultWechatAutoApprovalResultNotifyService(properties);
    }
}
