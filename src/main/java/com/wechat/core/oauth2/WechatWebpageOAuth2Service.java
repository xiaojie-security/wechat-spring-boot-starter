package com.wechat.core.oauth2;

import com.wechat.core.oauth2.domain.*;


public interface WechatWebpageOAuth2Service {

    String generateAuthUrl(AuthorizationRequest request);

    AccessTokenResponse getAccessTokenByCode(AccessTokenRequest request);

    AccessTokenValidateResponse validateAccessToken(AccessTokenValidateRequest request);

    UserInfoResponse queryUserInfoShare(UserInfoRequest request);

    RefreshTokenResponse refreshAccessToken(RefreshTokenRequest request);
}
