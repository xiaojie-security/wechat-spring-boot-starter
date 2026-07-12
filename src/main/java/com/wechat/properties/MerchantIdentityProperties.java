package com.wechat.properties;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serial;

@ConfigurationProperties(prefix = "wechat.merchant")
@Slf4j
@Data
@Component
public class MerchantIdentityProperties {

    /**
     * mchid(商户号)商户在商户平台申请的唯一身份标识，所有接口在调用时都必须传商户号，微信支付通过商户号确认商户身份。
     */
    private String merchantId;

    /**
     * appid(开发者ID)是商户在微信开放平台(移动应用)或公众平台(服务号/政府或媒体类型的公众号/小程序)上的账号开发识别码，该appid必须与商户号mchid进行绑定。
     */
    private String appid;

    /**
     * 商户发起APIv3接口请求时，需要使用商户API证书私钥对请求进行签名。
     */
    private String certificate;

    /**
     *
     * 调用APIv3接口时请求头中的必传参数(serial_no)，需要与生成签名值时使用的商户API证书对应。
     */
    private String serialNo;

    /**
     * 商户会在以下两种场景中使用微信支付公钥：
     *
     * 1、接收到APIv3接口的返回内容，需要使用微信支付公钥进行验签；
     *
     * 2、调用某些含有敏感信息参数(如姓名、身份证号码)的接口时，需要使用微信支付公钥加密敏感信息后再传输参数。
     */
    private String publicKey;

    /**
     *
     * 商户会在以下两种场景中使用微信支付公钥id(Wechatpay-Serial)：
     *
     * 1、接收到APIv3接口的返回内容，请求头中会携带公钥id，商户需使用对应的微信支付公钥进行验签；
     *
     * 2、当调用的接口带有使用微信支付公钥加密的敏感信息参数时，需在请求头中传公钥id参数。
     */
    private String publicKeyId;

    /**
     *
     * 商户会在以下两种场景中使用APIv3密钥：
     *
     * 1、微信支付会使用APIv3密钥加密回调信息，然后将加密后的密文回调给商户，商户接收到APIv3回调通知的密文后，需使用该密钥进行解密。
     *
     * 2、在下载平台证书公钥时，需要使用该密钥进行解密。
     */
    private String apiV3Secret;
}
