package com.wechat.core.oauth2;

import com.wechat.core.oauth2.domain.XcxCode2SessionRequest;
import com.wechat.core.oauth2.domain.XcxCode2SessionResponse;

/**
 * 微信小程序 OAuth2 服务。
 */
public interface WechatXcxOAuth2Service {

    /**
     * 通过小程序登录凭证 code 换取用户 session 信息。
     *
     * @param request 小程序登录凭证请求
     * @return 小程序用户 session 信息
     */
    XcxCode2SessionResponse getSessionByCode(XcxCode2SessionRequest request);
}
