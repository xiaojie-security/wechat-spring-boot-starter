package com.aliyun.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "alipay.payment")
@Data
@Slf4j
public class AlipayPaymentProperties implements InitializingBean {

    /**
     * 支付宝开放平台网关地址。
     */
    private String serverUrl = "https://openapi.alipay.com/gateway.do";

    /**
     * 支付宝应用 ID。
     */
    private String appId;

    /**
     * 商户应用私钥，通常为 PKCS8 格式字符串。
     */
    private String privateKey;

    /**
     * 请求数据格式，默认 JSON。
     */
    private String format = "json";

    /**
     * 编码格式，默认 UTF-8。
     */
    private String charset = "UTF-8";

    /**
     * 签名算法，默认 RSA2。
     */
    private String signType = "RSA2";

    /**
     * 支付宝公钥。
     * 配置该值时使用普通公钥模式。
     */
    private String alipayPublicKey;

    /**
     * 应用公钥证书路径。
     * 未配置支付宝公钥时，可通过证书模式初始化客户端。
     */
    private String appCertPath;

    /**
     * 支付宝公钥证书路径。
     */
    private String alipayPublicCertPath;

    /**
     * 支付宝根证书路径。
     */
    private String rootCertPath;

    /**
     * 默认异步通知地址。
     */
    private String notifyUrl;

    /**
     * 默认同步跳转地址。
     */
    private String returnUrl;

    /**
     * 是否启用请求加密。
     */
    private Boolean encrypt = Boolean.FALSE;

    /**
     * 请求加密密钥。
     */
    private String encryptKey;

    public boolean isKeyMode() {
        return hasText(alipayPublicKey);
    }

    public boolean isCertMode() {
        return hasText(appCertPath) && hasText(alipayPublicCertPath) && hasText(rootCertPath);
    }

    @Override
    public void afterPropertiesSet() {
        log.debug("AlipayPaymentProperties.afterPropertiesSet initialized");
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
