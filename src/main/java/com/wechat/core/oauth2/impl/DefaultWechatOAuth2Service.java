package com.wechat.core.oauth2.impl;

import cn.hutool.core.net.url.UrlBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.wechat.core.oauth2.WechatOAuth2Service;
import com.wechat.core.oauth2.domain.*;
import com.wechat.properties.MerchantIdentityProperties;
import com.wechat.utils.WechatPayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.UncheckedIOException;

@Slf4j
@RequiredArgsConstructor
public class DefaultWechatOAuth2Service implements WechatOAuth2Service {

    private static final String AUTH_URL_HOST = "https://open.weixin.qq.com/connect/qrconnect";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    private static final String AUTH_VALIDATE_URL = "https://api.weixin.qq.com/sns/auth";
    private static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    private final MerchantIdentityProperties merchantIdentityProperties;
    private final OkHttpClient client = new OkHttpClient.Builder().build();


    @Override
    public String generateAuthUrl(AuthorizationRequest request) {
        requireNonNull(request, "AuthorizationRequest");
        String appid = defaultIfBlank(request.getAppid(), merchantIdentityProperties.getAppid());
        String responseType = defaultIfBlank(request.getResponseType(), "code");
        String scope = defaultIfBlank(request.getScope(), "snsapi_login");
        String lang = defaultIfBlank(request.getLang(), "cn");
        requireNotBlank(appid, "appid");
        requireNotBlank(request.getRedirectUri(), "redirectUri");

        String authUrl = UrlBuilder.of(AUTH_URL_HOST)
                .addQuery("appid", appid)
                .addQuery("redirect_uri", request.getRedirectUri())
                .addQuery("state", request.getState())
                .addQuery("response_type", responseType)
                .addQuery("scope", scope)
                .addQuery("lang", lang)
                .build() + "#wechat_redirect";
        log.info("DefaultWechatOAuth2Service.generateAuthUrl 生成微信网页授权地址成功，appid={}, redirectUri={}, scope={}, lang={}",
                maskValue(appid), request.getRedirectUri(), scope, lang);
        return authUrl;
    }

    @Override
    public AccessTokenResponse getAccessTokenByCode(AccessTokenRequest request) {
        requireNonNull(request, "AccessTokenRequest");
        String appid = defaultIfBlank(request.getAppid(), merchantIdentityProperties.getAppid());
        String secret = defaultIfBlank(request.getSecret(), merchantIdentityProperties.getAppSecret());
        String grantType = defaultIfBlank(request.getGrantType(), "authorization_code");
        requireNotBlank(appid, "appid");
        requireNotBlank(secret, "secret");
        requireNotBlank(request.getCode(), "code");
        String url = UrlBuilder.of(ACCESS_TOKEN_URL)
                .addQuery("appid", appid)
                .addQuery("secret", secret)
                .addQuery("code", request.getCode())
                .addQuery("grant_type", grantType)
                .build();
        log.info("DefaultWechatOAuth2Service.getAccessTokenByCode 开始通过code换取网站授权access_token，appid={}, code={}",
                maskValue(appid), maskValue(request.getCode()));
        return executeGet(url, "DefaultWechatOAuth2Service.getAccessTokenByCode", AccessTokenResponse.class);
    }

    @Override
    public AccessTokenValidateResponse validateAccessToken(AccessTokenValidateRequest request) {
        requireNonNull(request, "AccessTokenValidateRequest");
        requireNotBlank(request.getAccessToken(), "accessToken");
        requireNotBlank(request.getOpenid(), "openid");
        String url = UrlBuilder.of(AUTH_VALIDATE_URL)
                .addQuery("access_token", request.getAccessToken())
                .addQuery("openid", request.getOpenid())
                .build();
        log.info("DefaultWechatOAuth2Service.validateAccessToken 开始校验网站授权access_token，openid={}",
                maskValue(request.getOpenid()));
        return executeGet(url, "DefaultWechatOAuth2Service.validateAccessToken", AccessTokenValidateResponse.class);
    }

    @Override
    public UserInfoResponse queryUserInfoShare(UserInfoRequest request) {
        requireNonNull(request, "UserInfoRequest");
        requireNotBlank(request.getAccessToken(), "accessToken");
        requireNotBlank(request.getOpenid(), "openid");
        String url = UrlBuilder.of(USER_INFO_URL)
                .addQuery("access_token", request.getAccessToken())
                .addQuery("openid", request.getOpenid())
                .addQuery("lang", defaultIfBlank(request.getLang(), "en"))
                .build();
        log.info("DefaultWechatOAuth2Service.queryUserInfoShare 开始获取微信用户信息，openid={}, lang={}",
                maskValue(request.getOpenid()), defaultIfBlank(request.getLang(), "en"));
        return executeGet(url, "DefaultWechatOAuth2Service.queryUserInfoShare", UserInfoResponse.class);
    }

    @Override
    public RefreshTokenResponse refreshAccessToken(RefreshTokenRequest request) {
        requireNonNull(request, "RefreshTokenRequest");
        String appid = defaultIfBlank(request.getAppid(), merchantIdentityProperties.getAppid());
        String grantType = defaultIfBlank(request.getGrantType(), "refresh_token");
        requireNotBlank(appid, "appid");
        requireNotBlank(request.getRefreshToken(), "refreshToken");
        String url = UrlBuilder.of(REFRESH_TOKEN_URL)
                .addQuery("appid", appid)
                .addQuery("grant_type", grantType)
                .addQuery("refresh_token", request.getRefreshToken())
                .build();
        log.info("DefaultWechatOAuth2Service.refreshAccessToken 开始刷新网站授权access_token，appid={}, refreshToken={}",
                maskValue(appid), maskValue(request.getRefreshToken()));
        return executeGet(url, "DefaultWechatOAuth2Service.refreshAccessToken", RefreshTokenResponse.class);
    }

    private <T> T executeGet(String url, String action, Class<T> responseClass) {
        Request httpRequest = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = httpResponse.body() == null ? "" : httpResponse.body().string();
            if (!httpResponse.isSuccessful()) {
                log.error("{} 微信OAuth2接口HTTP请求失败，url={}, code={}, respBody={}",
                        action, sanitizeUrl(url), httpResponse.code(), abbreviate(respBody));
                throw new WechatOAuth2Exception("微信OAuth2接口HTTP请求失败，code=" + httpResponse.code());
            }
            validateBusinessResponse(action, url, respBody);
            T response = WechatPayUtils.fromJson(respBody, responseClass);
            log.info("{} 微信OAuth2接口调用成功，url={}, summary={}",
                    action, sanitizeUrl(url), buildSuccessSummary(response));
            return response;
        } catch (IOException e) {
            log.error("{} 调用微信OAuth2接口异常，url={}", action, sanitizeUrl(url), e);
            throw new UncheckedIOException("调用微信OAuth2接口异常，url=" + sanitizeUrl(url), e);
        } catch (JsonSyntaxException e) {
            log.error("{} 解析微信OAuth2接口响应失败，url={}", action, sanitizeUrl(url), e);
            throw new WechatOAuth2Exception("解析微信OAuth2接口响应失败");
        }
    }

    private void validateBusinessResponse(String action, String url, String respBody) {
        JsonObject jsonObject = WechatPayUtils.fromJson(respBody, JsonObject.class);
        if (jsonObject == null || !jsonObject.has("errcode")) {
            return;
        }
        int errcode = jsonObject.get("errcode").getAsInt();
        if (errcode == 0) {
            return;
        }
        String errmsg = jsonObject.has("errmsg") ? jsonObject.get("errmsg").getAsString() : "";
        log.error("{} 微信OAuth2接口业务返回失败，url={}, errcode={}, errmsg={}, respBody={}",
                action, sanitizeUrl(url), errcode, errmsg, abbreviate(respBody));
        throw new WechatOAuth2Exception("微信OAuth2接口业务返回失败，errcode=" + errcode + ", errmsg=" + errmsg);
    }

    private String buildSuccessSummary(Object response) {
        if (response instanceof AccessTokenResponse accessTokenResponse) {
            return "openid=" + maskValue(accessTokenResponse.getOpenid())
                    + ", scope=" + accessTokenResponse.getScope()
                    + ", expiresIn=" + accessTokenResponse.getExpiresIn()
                    + ", unionid=" + maskValue(accessTokenResponse.getUnionid());
        }
        if (response instanceof RefreshTokenResponse refreshTokenResponse) {
            return "openid=" + maskValue(refreshTokenResponse.getOpenid())
                    + ", scope=" + refreshTokenResponse.getScope()
                    + ", expiresIn=" + refreshTokenResponse.getExpiresIn();
        }
        if (response instanceof UserInfoResponse userInfoResponse) {
            return "openid=" + maskValue(userInfoResponse.getOpenid())
                    + ", unionid=" + maskValue(userInfoResponse.getUnionid());
        }
        if (response instanceof AccessTokenValidateResponse validateResponse) {
            return "errcode=" + validateResponse.getErrcode()
                    + ", errmsg=" + validateResponse.getErrmsg();
        }
        return response == null ? "null" : response.getClass().getSimpleName();
    }

    private String sanitizeUrl(String url) {
        String sanitized = url;
        sanitized = sanitized.replaceAll("([?&]secret=)[^&#]*", "$1***");
        sanitized = sanitized.replaceAll("([?&]access_token=)[^&#]*", "$1***");
        sanitized = sanitized.replaceAll("([?&]refresh_token=)[^&#]*", "$1***");
        sanitized = sanitized.replaceAll("([?&]code=)[^&#]*", "$1***");
        sanitized = sanitized.replaceAll("([?&]openid=)[^&#]*", "$1***");
        return sanitized;
    }

    private String abbreviate(String value) {
        if (value == null || value.length() <= 512) {
            return value;
        }
        return value.substring(0, 512);
    }

    private String defaultIfBlank(String value, String defaultValue) {
        return isBlank(value) ? defaultValue : value;
    }

    private void requireNonNull(Object value, String fieldName) {
        if (value == null) {
            throw new WechatOAuth2Exception("请求参数不能为空: " + fieldName);
        }
    }

    private void requireNotBlank(String value, String fieldName) {
        if (isBlank(value)) {
            throw new WechatOAuth2Exception("请求参数不能为空: " + fieldName);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String maskValue(String value) {
        if (isBlank(value)) {
            return value;
        }
        if (value.length() <= 8) {
            return "***";
        }
        return value.substring(0, 4) + "***" + value.substring(value.length() - 4);
    }

    private static final class WechatOAuth2Exception extends RuntimeException {

        private WechatOAuth2Exception(String message) {
            super(message);
        }
    }
}
