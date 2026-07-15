package com.wechat.config;

import com.wechat.core.transfer.service.WechatAutoApprovalResultNotifyService;
import com.wechat.core.transfer.service.WechatTransferService;
import com.wechat.core.transfer.service.impl.DefaultWechatAutoApprovalResultNotifyService;
import com.wechat.core.transfer.service.impl.DefaultWechatTransferService;
import com.wechat.provider.WechatMerchantConfigProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "wechat.transfer", name = "enable", havingValue = "true", matchIfMissing = false)
public class WechatTransferConfiguration {


    @Bean
    @ConditionalOnMissingBean(WechatTransferService.class)
    public WechatTransferService wechatTransferService(WechatMerchantConfigProvider provider){
        return new DefaultWechatTransferService(provider);
    }

    @Bean
    @ConditionalOnMissingBean(WechatAutoApprovalResultNotifyService.class)
    public WechatAutoApprovalResultNotifyService wechatAutoApprovalResultNotifyService(WechatMerchantConfigProvider provider) {
        return new DefaultWechatAutoApprovalResultNotifyService(provider);
    }
}
