package com.wechat.config;

import com.wechat.properties.MerchantIdentityProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 微信支付 Starter 自动装配配置。
 */
@AutoConfiguration
@EnableConfigurationProperties(MerchantIdentityProperties.class)
public class WechatConfiguration {
}
