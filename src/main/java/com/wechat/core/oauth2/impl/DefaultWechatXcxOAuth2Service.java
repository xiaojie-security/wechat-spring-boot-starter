package com.wechat.core.oauth2.impl;

import cn.hutool.core.net.url.UrlBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.wechat.core.oauth2.WechatXcxOAuth2Service;
import com.wechat.core.oauth2.domain.XcxCode2SessionRequest;
import com.wechat.core.oauth2.domain.XcxCode2SessionResponse;
import com.wechat.provider.WechatMerchantConfigProvider;
import com.wechat.provider.domain.WechatMerchantConfig;
import com.wechat.utils.WechatPayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * 默认微信小程序 OAuth2 服务。
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultWechatXcxOAuth2Service implements WechatXcxOAuth2Service {

    private static final String CODE_2_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    private final OkHttpClient client = new OkHttpClient.Builder().build();
    private final WechatMerchantConfigProvider provider;

    @Override
    public XcxCode2SessionResponse getSessionByCode(XcxCode2SessionRequest request) {
        requireNonNull(request, "XcxCode2SessionRequest");
        WechatMerchantConfig config = getMerchantConfig();
        String appid = defaultIfBlank(request.getAppid(), config.getAppid());
        String secret = defaultIfBlank(request.getSecret(), config.getAppSecret());
        String grantType = defaultIfBlank(request.getGrantType(), "authorization_code");
        requireNotBlank(appid, "appid");
        requireNotBlank(secret, "secret");
        requireNotBlank(request.getJsCode(), "jsCode");

        String url = UrlBuilder.of(CODE_2_SESSION_URL)
                .addQuery("appid", appid)
                .addQuery("secret", secret)
                .addQuery("js_code", request.getJsCode())
                .addQuery("grant_type", grantType)
                .build();
        log.info("DefaultWechatXcxOAuth2Service.getSessionByCode 开始通过code换取小程序session，appid={}, jsCode={}",
                maskValue(appid), maskValue(request.getJsCode()));
        return executeGet(url, "DefaultWechatXcxOAuth2Service.getSessionByCode");
    }

    private XcxCode2SessionResponse executeGet(String url, String action) {
        Request httpRequest = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = httpResponse.body() == null ? "" : httpResponse.body().string();
            if (!httpResponse.isSuccessful()) {
                log.error("{} 微信小程序OAuth2接口HTTP请求失败，url={}, code={}, respBody={}",
                        action, sanitizeUrl(url), httpResponse.code(), abbreviate(respBody));
                throw new WechatXcxOAuth2Exception("微信小程序OAuth2接口HTTP请求失败，code=" + httpResponse.code());
            }
            validateBusinessResponse(action, url, respBody);
            XcxCode2SessionResponse response = WechatPayUtils.fromJson(respBody, XcxCode2SessionResponse.class);
            log.info("{} 微信小程序OAuth2接口调用成功，url={}, summary={}",
                    action, sanitizeUrl(url), buildSuccessSummary(response));
            return response;
        } catch (IOException e) {
            log.error("{} 调用微信小程序OAuth2接口异常，url={}", action, sanitizeUrl(url), e);
            throw new UncheckedIOException("调用微信小程序OAuth2接口异常，url=" + sanitizeUrl(url), e);
        } catch (JsonSyntaxException e) {
            log.error("{} 解析微信小程序OAuth2接口响应失败，url={}", action, sanitizeUrl(url), e);
            throw new WechatXcxOAuth2Exception("解析微信小程序OAuth2接口响应失败");
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
        log.error("{} 微信小程序OAuth2接口业务返回失败，url={}, errcode={}, errmsg={}, respBody={}",
                action, sanitizeUrl(url), errcode, errmsg, abbreviate(respBody));
        throw new WechatXcxOAuth2Exception("微信小程序OAuth2接口业务返回失败，errcode=" + errcode + ", errmsg=" + errmsg);
    }

    private String buildSuccessSummary(XcxCode2SessionResponse response) {
        if (response == null) {
            return "null";
        }
        return "openid=" + maskValue(response.getOpenid())
                + ", unionid=" + maskValue(response.getUnionid())
                + ", errcode=" + response.getErrcode()
                + ", errmsg=" + response.getErrmsg();
    }

    private String sanitizeUrl(String url) {
        String sanitized = url;
        sanitized = sanitized.replaceAll("([?&]secret=)[^&#]*", "$1***");
        sanitized = sanitized.replaceAll("([?&]js_code=)[^&#]*", "$1***");
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

    private WechatMerchantConfig getMerchantConfig() {
        WechatMerchantConfig config = provider.getConfig();
        if (config == null) {
            throw new WechatXcxOAuth2Exception("未获取到微信商户配置");
        }
        return config;
    }

    private void requireNonNull(Object value, String fieldName) {
        if (value == null) {
            throw new WechatXcxOAuth2Exception("请求参数不能为空: " + fieldName);
        }
    }

    private void requireNotBlank(String value, String fieldName) {
        if (isBlank(value)) {
            throw new WechatXcxOAuth2Exception("请求参数不能为空: " + fieldName);
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

    /**
     * 微信小程序 OAuth2 调用异常。
     */
    private static final class WechatXcxOAuth2Exception extends RuntimeException {

        private WechatXcxOAuth2Exception(String message) {
            super(message);
        }
    }
}
