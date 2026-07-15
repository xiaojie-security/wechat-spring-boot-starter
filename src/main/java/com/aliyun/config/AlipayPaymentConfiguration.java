package com.aliyun.config;

import com.aliyun.core.alipay.payment.AlipayPaymentService;
import com.aliyun.properties.AlipayPaymentProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(AlipayPaymentProperties.class)
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "alipay.payment", name = "enable", havingValue = "true", matchIfMissing = false)
public class AlipayPaymentConfiguration {

    private final AlipayPaymentProperties properties;

    @Bean
    @ConditionalOnMissingBean(AlipayPaymentService.class)
    public AlipayPaymentService alipayPaymentService() {
        return new AlipayPaymentService(properties);
    }
}
