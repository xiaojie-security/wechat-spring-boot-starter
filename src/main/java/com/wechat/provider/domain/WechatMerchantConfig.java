package com.wechat.provider.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信商户配置快照。
 * <p>
 * 该对象用于承载一次业务调用所需的商户身份、OAuth2 与微信支付相关配置，
 * 具体来源可由配置文件、数据库、缓存或其他外部系统提供。
 *
 * @author YourName
 * @version 1.0
 * @since 2026-07-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WechatMerchantConfig {

    /**
     * 微信支付商户号。
     */
    private String merchantId;

    /**
     * 微信应用唯一标识。
     */
    private String appid;

    /**
     * 微信应用密钥。
     */
    private String appSecret;

    /**
     * 商户 API 证书私钥内容。
     */
    private String certificate;

    /**
     * 商户 API 证书序列号。
     */
    private String serialNo;

    /**
     * 微信支付公钥内容。
     */
    private String publicKey;

    /**
     * 微信支付公钥 ID。
     */
    private String publicKeyId;

    /**
     * 微信支付 APIv3 密钥。
     */
    private String apiV3Secret;
}
