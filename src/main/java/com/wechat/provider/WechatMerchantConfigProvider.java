package com.wechat.provider;

import com.wechat.provider.domain.WechatMerchantConfig;

/**
 * 微信商户配置提供者。
 * <p>
 * Starter 在运行时通过该接口获取当前生效的商户配置，
 * 调用方可以基于配置文件、数据库、缓存或远程配置中心提供实现。
 *
 * @author YourName
 * @version 1.0
 * @since 2026-07-15
 */
public interface WechatMerchantConfigProvider {

    /**
     * 获取当前生效的微信商户配置快照。
     * <p>
     * 返回结果应为业务层可直接使用的最终配置内容，
     * 例如证书私钥、公钥等字段应由具体 Provider 自行完成来源解析。
     *
     * @return 微信商户配置
     */
    WechatMerchantConfig getConfig();
}
