package com.wechat.core.access;

import com.wechat.core.access.domain.WechatAccessTokenResponse;

/**
 * 微信接口调用凭据服务。
 */
public interface WechatAccessTokenService {

    /**
     * 获取稳定版接口调用凭据。
     *
     * @return 微信接口调用凭据响应
     */
    default WechatAccessTokenResponse getStableAccessToken() {
        return getStableAccessToken(false);
    }

    /**
     * 获取稳定版接口调用凭据。
     *
     * @param forceRefresh 是否强制刷新凭据
     * @return 微信接口调用凭据响应
     */
    WechatAccessTokenResponse getStableAccessToken(boolean forceRefresh);

    /**
     * 获取 access_token。
     *
     * @return access_token
     */
    default String getAccessToken() {
        return getStableAccessToken().getAccessToken();
    }

    /**
     * 获取 access_token。
     *
     * @param forceRefresh 是否强制刷新凭据
     * @return access_token
     */
    default String getAccessToken(boolean forceRefresh) {
        return getStableAccessToken(forceRefresh).getAccessToken();
    }
}
