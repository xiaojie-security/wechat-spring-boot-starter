package com.wechat.config;

import com.wechat.core.payment.service.impl.DefaultWechatPaymentService;
import com.wechat.core.profitsharing.service.impl.DefaultWechatProfitsharingService;
import com.wechat.core.transfer.service.impl.DefaultWechatTransferService;
import com.wechat.properties.MerchantIdentityProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * 微信支付 Starter 自动装配配置。
 * 负责注册属性绑定对象以及核心支付、转账、分账服务。
 */
@AutoConfiguration
@EnableConfigurationProperties(MerchantIdentityProperties.class)
@Import({
        DefaultWechatPaymentService.class,
        DefaultWechatTransferService.class,
        DefaultWechatProfitsharingService.class
})
public class WechatPaymentConfiguration {
}
