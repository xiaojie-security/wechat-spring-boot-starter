package com.wechat.core.xcx.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.wechat.core.access.WechatAccessTokenService;
import com.wechat.core.xcx.WechatXcxQRCodeException;
import com.wechat.core.xcx.WechatXcxQRCodeService;
import com.wechat.core.xcx.domain.WechatXcxQRCodeLineColor;
import com.wechat.core.xcx.domain.WechatXcxQRCodeRequest;
import com.wechat.utils.WechatPayUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 微信小程序无限量二维码默认实现。
 */
@Slf4j
public class DefaultWechatXcxQRCodeService implements WechatXcxQRCodeService {

    private static final String QR_CODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";

    private static final String SCENE_PATTERN = "[0-9A-Za-z!#$&'()*+,/:;=?@._~-]+";

    private final WechatAccessTokenService accessTokenService;

    private final OkHttpClient client;

    /**
     * 使用默认 OkHttp 客户端创建二维码服务。
     *
     * @param accessTokenService 微信接口调用凭据服务
     */
    public DefaultWechatXcxQRCodeService(WechatAccessTokenService accessTokenService) {
        this(accessTokenService, new OkHttpClient.Builder().build());
    }

    /**
     * 创建微信小程序二维码服务。
     *
     * @param accessTokenService 微信接口调用凭据服务
     * @param client HTTP 客户端
     */
    public DefaultWechatXcxQRCodeService(WechatAccessTokenService accessTokenService, OkHttpClient client) {
        if (accessTokenService == null) {
            throw new IllegalArgumentException("WechatAccessTokenService 不能为空");
        }
        if (client == null) {
            throw new IllegalArgumentException("OkHttpClient 不能为空");
        }
        this.accessTokenService = accessTokenService;
        this.client = client;
    }

    @Override
    public byte[] getUnlimitedQRCode(WechatXcxQRCodeRequest request) {
        validateRequest(request);
        String accessToken = accessTokenService.getAccessToken();
        if (isBlank(accessToken)) {
            log.error("DefaultWechatXcxQRCodeService.getUnlimitedQRCode 获取到空 access_token");
            throw new WechatXcxQRCodeException("获取微信小程序二维码失败：access_token 为空");
        }

        HttpUrl url = HttpUrl.get(QR_CODE_URL).newBuilder()
                .addQueryParameter("access_token", accessToken)
                .build();
        String requestBody = WechatPayUtils.toJson(request);
        Request httpRequest = new Request.Builder()
                .url(url)
                .addHeader("Accept", "image/png, application/json")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody))
                .build();

        log.info("DefaultWechatXcxQRCodeService.getUnlimitedQRCode 开始获取小程序二维码，scene={}, page={}",
                request.getScene(), request.getPage());
        try (Response response = client.newCall(httpRequest).execute()) {
            byte[] responseBody = response.body() == null ? new byte[0] : response.body().bytes();
            if (!response.isSuccessful()) {
                log.error("DefaultWechatXcxQRCodeService.getUnlimitedQRCode 请求微信接口失败，code={}, responseBody={}",
                        response.code(), abbreviate(responseBody));
                throw buildApiException(responseBody, response.code());
            }
            if (isJsonResponse(response, responseBody)) {
                throw buildApiException(responseBody, response.code());
            }
            if (responseBody.length == 0) {
                log.error("DefaultWechatXcxQRCodeService.getUnlimitedQRCode 微信接口返回空二维码内容");
                throw new WechatXcxQRCodeException("微信小程序二维码响应为空", null, response.code());
            }
            return responseBody;
        } catch (IOException e) {
            log.error("DefaultWechatXcxQRCodeService.getUnlimitedQRCode 调用微信接口异常，scene={}",
                    request.getScene(), e);
            throw new UncheckedIOException("调用微信小程序二维码接口异常", e);
        }
    }

    @Override
    public String getUnlimitedQRCodeBase64(WechatXcxQRCodeRequest request) {
        return Base64.getEncoder().encodeToString(getUnlimitedQRCode(request));
    }

    private void validateRequest(WechatXcxQRCodeRequest request) {
        if (request == null) {
            log.error("DefaultWechatXcxQRCodeService.getUnlimitedQRCode 请求参数为空");
            throw new WechatXcxQRCodeException("请求参数不能为空");
        }
        if (isBlank(request.getScene())) {
            log.error("DefaultWechatXcxQRCodeService.getUnlimitedQRCode scene 不能为空");
            throw new WechatXcxQRCodeException("scene 不能为空");
        }
        if (request.getScene().length() > 32 || !request.getScene().matches(SCENE_PATTERN)) {
            log.error("DefaultWechatXcxQRCodeService.getUnlimitedQRCode scene 不符合微信接口要求，scene={}",
                    request.getScene());
            throw new WechatXcxQRCodeException("scene 最多 32 个字符，且只能使用微信接口支持的字符");
        }
        if (!isBlank(request.getPage())
                && (request.getPage().startsWith("/") || request.getPage().contains("?")
                || request.getPage().contains("#"))) {
            log.error("DefaultWechatXcxQRCodeService.getUnlimitedQRCode page 不符合微信接口要求，page={}",
                    request.getPage());
            throw new WechatXcxQRCodeException("page 不能以 / 开头，且不能携带参数");
        }
        if (request.getEnvVersion() == null) {
            log.error("DefaultWechatXcxQRCodeService.getUnlimitedQRCode envVersion 不符合微信接口要求，envVersion={}",
                    request.getEnvVersion());
            throw new WechatXcxQRCodeException("envVersion 只能是 release、trial 或 develop");
        }
        if (request.getWidth() == null || request.getWidth() < 280 || request.getWidth() > 1280) {
            log.error("DefaultWechatXcxQRCodeService.getUnlimitedQRCode width 不符合微信接口要求，width={}",
                    request.getWidth());
            throw new WechatXcxQRCodeException("width 取值范围为 280 至 1280");
        }
        validateColor(request.getLineColor());
    }

    private void validateColor(WechatXcxQRCodeLineColor color) {
        if (color == null) {
            return;
        }
        if (!isColorComponentValid(color.getR()) || !isColorComponentValid(color.getG())
                || !isColorComponentValid(color.getB())) {
            log.error("DefaultWechatXcxQRCodeService.getUnlimitedQRCode lineColor 不符合微信接口要求，lineColor={}",
                    color);
            throw new WechatXcxQRCodeException("lineColor 的 RGB 分量取值范围为 0 至 255");
        }
    }

    private boolean isColorComponentValid(Integer value) {
        return value != null && value >= 0 && value <= 255;
    }

    private WechatXcxQRCodeException buildApiException(byte[] responseBody, int httpStatus) {
        String responseText = new String(responseBody, StandardCharsets.UTF_8);
        try {
            JsonObject jsonObject = WechatPayUtils.fromJson(responseText, JsonObject.class);
            Integer errcode = jsonObject != null && jsonObject.has("errcode")
                    ? jsonObject.get("errcode").getAsInt() : null;
            String errmsg = jsonObject != null && jsonObject.has("errmsg")
                    ? jsonObject.get("errmsg").getAsString() : "";
            if (errcode != null) {
                log.error("DefaultWechatXcxQRCodeService.getUnlimitedQRCode 微信接口业务返回失败，errcode={}, errmsg={}",
                        errcode, errmsg);
                return new WechatXcxQRCodeException(
                        "微信小程序二维码业务返回失败，errcode=" + errcode + ", errmsg=" + errmsg,
                        errcode, httpStatus);
            }
        } catch (JsonParseException | IllegalStateException e) {
            log.error("DefaultWechatXcxQRCodeService.getUnlimitedQRCode 解析微信错误响应失败，httpStatus={}",
                    httpStatus, e);
        }
        return new WechatXcxQRCodeException(
                "请求微信小程序二维码失败，HTTP 状态码=" + httpStatus + ", responseBody=" + abbreviate(responseBody),
                null, httpStatus);
    }

    private boolean isJsonResponse(Response response, byte[] responseBody) {
        MediaType mediaType = response.body() == null ? null : response.body().contentType();
        if (mediaType != null && "application".equalsIgnoreCase(mediaType.type())
                && "json".equalsIgnoreCase(mediaType.subtype())) {
            return true;
        }
        for (byte value : responseBody) {
            if (!Character.isWhitespace((char) value)) {
                return value == '{';
            }
        }
        return false;
    }

    private String abbreviate(byte[] value) {
        String text = new String(value, StandardCharsets.UTF_8);
        return text.length() <= 512 ? text : text.substring(0, 512);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
