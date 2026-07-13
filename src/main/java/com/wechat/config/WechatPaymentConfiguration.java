package com.wechat.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.wechat.core","com.wechat.properties"})
public class WechatPaymentConfiguration {
}
