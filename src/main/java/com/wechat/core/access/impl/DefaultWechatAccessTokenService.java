package com.wechat.core.access.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.wechat.core.access.WechatAccessTokenException;
import com.wechat.core.access.WechatAccessTokenService;
import com.wechat.core.access.domain.WechatAccessTokenResponse;
import com.wechat.provider.WechatMerchantConfigProvider;
import com.wechat.provider.domain.WechatMerchantConfig;
import com.wechat.utils.WechatPayUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * 微信稳定版接口调用凭据默认实现。
 */
@Slf4j
public class DefaultWechatAccessTokenService implements WechatAccessTokenService {

    private static final String STABLE_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/stable_token";

    private static final String GRANT_TYPE = "client_credential";

    private final WechatMerchantConfigProvider provider;

    private final OkHttpClient client;

    /**
     * 使用默认 OkHttp 客户端创建服务。
     *
     * @param provider 微信商户配置提供者
     */
    public DefaultWechatAccessTokenService(WechatMerchantConfigProvider provider) {
        this(provider, new OkHttpClient.Builder().build());
    }

    /**
     * 创建微信稳定版接口调用凭据服务。
     *
     * @param provider 微信商户配置提供者
     * @param client HTTP 客户端
     */
    public DefaultWechatAccessTokenService(WechatMerchantConfigProvider provider, OkHttpClient client) {
        if (provider == null) {
            throw new IllegalArgumentException("WechatMerchantConfigProvider 不能为空");
        }
        if (client == null) {
            throw new IllegalArgumentException("OkHttpClient 不能为空");
        }
        this.provider = provider;
        this.client = client;
    }

    @Override
    public WechatAccessTokenResponse getStableAccessToken(boolean forceRefresh) {
        WechatMerchantConfig config = getMerchantConfig();
        requireNotBlank(config.getAppid(), "appid");
        requireNotBlank(config.getAppSecret(), "secret");

        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("grant_type", GRANT_TYPE);
        requestJson.addProperty("appid", config.getAppid());
        requestJson.addProperty("secret", config.getAppSecret());
        requestJson.addProperty("force_refresh", forceRefresh);
        String requestBody = WechatPayUtils.toJson(requestJson);
        Request request = new Request.Builder()
                .url(STABLE_TOKEN_URL)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody))
                .build();

        log.info("DefaultWechatAccessTokenService.getStableAccessToken 开始获取微信稳定版接口调用凭据，appid={}, forceRefresh={}",
                maskValue(config.getAppid()), forceRefresh);
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() == null ? "" : response.body().string();
            if (!response.isSuccessful()) {
                log.error("DefaultWechatAccessTokenService.getStableAccessToken 请求微信接口失败，code={}, responseBody={}",
                        response.code(), abbreviate(responseBody));
                throw new WechatAccessTokenException(
                        "请求微信稳定版接口调用凭据失败，HTTP 状态码=" + response.code(), null, response.code());
            }
            return parseResponse(responseBody, response.code());
        } catch (IOException e) {
            log.error("DefaultWechatAccessTokenService.getStableAccessToken 调用微信接口异常，appid={}",
                    maskValue(config.getAppid()), e);
            throw new UncheckedIOException("调用微信稳定版接口调用凭据接口异常", e);
        }
    }

    private WechatAccessTokenResponse parseResponse(String responseBody, int httpStatus) {
        try {
            JsonObject jsonObject = WechatPayUtils.fromJson(responseBody, JsonObject.class);
            if (jsonObject == null) {
                throw new WechatAccessTokenException("微信稳定版接口调用凭据响应为空", null, httpStatus);
            }
            Integer errcode = getInteger(jsonObject, "errcode");
            String errmsg = getString(jsonObject, "errmsg");
            if (errcode != null && errcode != 0) {
                log.error("DefaultWechatAccessTokenService.getStableAccessToken 微信接口业务返回失败，errcode={}, errmsg={}",
                        errcode, errmsg);
                throw new WechatAccessTokenException(
                        "微信稳定版接口调用凭据业务返回失败，errcode=" + errcode + ", errmsg=" + errmsg,
                        errcode, httpStatus);
            }
            WechatAccessTokenResponse result = WechatPayUtils.fromJson(responseBody, WechatAccessTokenResponse.class);
            if (result == null || isBlank(result.getAccessToken()) || result.getExpiresIn() == null
                    || result.getExpiresIn() <= 0) {
                log.error("DefaultWechatAccessTokenService.getStableAccessToken 微信接口响应缺少有效凭据，responseBody={}",
                        abbreviate(responseBody));
                throw new WechatAccessTokenException("微信稳定版接口调用凭据响应缺少有效 access_token", null, httpStatus);
            }
            return result;
        } catch (JsonParseException | IllegalStateException e) {
            log.error("DefaultWechatAccessTokenService.getStableAccessToken 解析微信接口响应失败，responseBody={}",
                    abbreviate(responseBody), e);
            throw new WechatAccessTokenException("解析微信稳定版接口调用凭据响应失败", e);
        }
    }

    private WechatMerchantConfig getMerchantConfig() {
        WechatMerchantConfig config = provider.getConfig();
        if (config == null) {
            log.error("DefaultWechatAccessTokenService.getStableAccessToken 未获取到微信商户配置");
            throw new WechatAccessTokenException("未获取到微信商户配置");
        }
        return config;
    }

    private void requireNotBlank(String value, String fieldName) {
        if (isBlank(value)) {
            log.error("DefaultWechatAccessTokenService.getStableAccessToken 微信商户配置缺少必要参数，fieldName={}", fieldName);
            throw new WechatAccessTokenException("微信商户配置不能为空: " + fieldName);
        }
    }

    private Integer getInteger(JsonObject jsonObject, String memberName) {
        return jsonObject.has(memberName) && !jsonObject.get(memberName).isJsonNull()
                ? jsonObject.get(memberName).getAsInt() : null;
    }

    private String getString(JsonObject jsonObject, String memberName) {
        return jsonObject.has(memberName) && !jsonObject.get(memberName).isJsonNull()
                ? jsonObject.get(memberName).getAsString() : "";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String abbreviate(String value) {
        return value == null || value.length() <= 512 ? value : value.substring(0, 512);
    }

    private String maskValue(String value) {
        if (isBlank(value)) {
            return value;
        }
        return value.length() <= 8 ? "***" : value.substring(0, 4) + "***" + value.substring(value.length() - 4);
    }
}
