package com.wechat.core.wechat;

import com.google.gson.JsonObject;
import com.wechat.core.access.WechatAccessTokenService;
import com.wechat.core.access.domain.WechatAccessTokenResponse;
import com.wechat.core.access.impl.DefaultWechatAccessTokenService;
import com.wechat.core.xcx.WechatXcxQRCodeException;
import com.wechat.core.xcx.domain.WechatXcxQRCodeRequest;
import com.wechat.core.xcx.impl.DefaultWechatXcxQRCodeService;
import com.wechat.provider.WechatMerchantConfigProvider;
import com.wechat.provider.domain.WechatMerchantConfig;
import com.wechat.utils.WechatPayUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 微信小程序 access_token 与二维码服务合约测试。
 */
class WechatXcxServiceTest {

    @Test
    void shouldRequestStableAccessTokenWithOfficialJsonFields() throws Exception {
        AtomicReference<String> requestBody = new AtomicReference<>();
        HttpServer server = createServer("/cgi-bin/stable_token", exchange -> {
            requestBody.set(readBody(exchange));
            writeResponse(exchange, 200, "application/json",
                    "{\"access_token\":\"stable-token\",\"expires_in\":7200}");
        });
        try {
            DefaultWechatAccessTokenService service = new DefaultWechatAccessTokenService(
                    provider(), rewriteClient(server));

            WechatAccessTokenResponse response = service.getStableAccessToken(true);

            assertEquals("stable-token", response.getAccessToken());
            assertEquals(7200, response.getExpiresIn());
            JsonObject requestJson = WechatPayUtils.fromJson(requestBody.get(), JsonObject.class);
            assertEquals("client_credential", requestJson.get("grant_type").getAsString());
            assertEquals("wx-test-appid", requestJson.get("appid").getAsString());
            assertEquals("test-secret", requestJson.get("secret").getAsString());
            assertEquals(true, requestJson.get("force_refresh").getAsBoolean());
        } finally {
            server.stop(0);
        }
    }

    @Test
    void shouldReturnQRCodeBytesAndSendAccessTokenAsQueryParameter() throws Exception {
        AtomicReference<String> query = new AtomicReference<>();
        AtomicReference<String> requestBody = new AtomicReference<>();
        byte[] image = new byte[]{0, 1, 2, 3, 4};
        HttpServer server = createServer("/wxa/getwxacodeunlimit", exchange -> {
            query.set(exchange.getRequestURI().getQuery());
            requestBody.set(readBody(exchange));
            writeResponse(exchange, 200, "image/png", image);
        });
        try {
            WechatAccessTokenService tokenService = forceRefresh -> WechatAccessTokenResponse.builder()
                    .accessToken("stable-token")
                    .expiresIn(7200)
                    .build();
            DefaultWechatXcxQRCodeService service = new DefaultWechatXcxQRCodeService(
                    tokenService, rewriteClient(server));

            byte[] response = service.getUnlimitedQRCode(WechatXcxQRCodeRequest.builder()
                    .scene("order=123")
                    .page("pages/order/detail")
                    .build());

            assertArrayEquals(image, response);
            assertEquals("access_token=stable-token", query.get());
            JsonObject requestJson = WechatPayUtils.fromJson(requestBody.get(), JsonObject.class);
            assertEquals("order=123", requestJson.get("scene").getAsString());
            assertEquals("pages/order/detail", requestJson.get("page").getAsString());
            assertEquals("release", requestJson.get("env_version").getAsString());
            assertEquals(430, requestJson.get("width").getAsInt());
            assertEquals("AAECAwQ=", service.getUnlimitedQRCodeBase64(WechatXcxQRCodeRequest.builder()
                    .scene("order=123")
                    .build()));
        } finally {
            server.stop(0);
        }
    }

    @Test
    void shouldExposeWechatJsonErrorFromQRCodeEndpoint() throws Exception {
        HttpServer server = createServer("/wxa/getwxacodeunlimit", exchange -> writeResponse(
                exchange, 200, "application/json", "{\"errcode\":41030,\"errmsg\":\"invalid page\"}"));
        try {
            WechatAccessTokenService tokenService = forceRefresh -> WechatAccessTokenResponse.builder()
                    .accessToken("stable-token")
                    .expiresIn(7200)
                    .build();
            DefaultWechatXcxQRCodeService service = new DefaultWechatXcxQRCodeService(
                    tokenService, rewriteClient(server));

            WechatXcxQRCodeException exception = assertThrows(WechatXcxQRCodeException.class,
                    () -> service.getUnlimitedQRCode(WechatXcxQRCodeRequest.builder()
                            .scene("order=123")
                            .build()));

            assertEquals(41030, exception.getErrcode());
            assertEquals("微信小程序二维码业务返回失败，errcode=41030, errmsg=invalid page", exception.getMessage());
        } finally {
            server.stop(0);
        }
    }

    private WechatMerchantConfigProvider provider() {
        WechatMerchantConfig config = new WechatMerchantConfig();
        config.setAppid("wx-test-appid");
        config.setAppSecret("test-secret");
        return () -> config;
    }

    private HttpServer createServer(String path, Handler handler) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext(path, handler::handle);
        server.start();
        return server;
    }

    private OkHttpClient rewriteClient(HttpServer server) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl rewritten = new HttpUrl.Builder()
                            .scheme("http")
                            .host("localhost")
                            .port(server.getAddress().getPort())
                            .encodedPath(original.url().encodedPath())
                            .encodedQuery(original.url().encodedQuery())
                            .build();
                    return chain.proceed(original.newBuilder().url(rewritten).build());
                })
                .build();
    }

    private String readBody(HttpExchange exchange) throws IOException {
        try (InputStream inputStream = exchange.getRequestBody()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private void writeResponse(HttpExchange exchange, int status, String contentType, String body) throws IOException {
        writeResponse(exchange, status, contentType, body.getBytes(StandardCharsets.UTF_8));
    }

    private void writeResponse(HttpExchange exchange, int status, String contentType, byte[] body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(status, body.length);
        exchange.getResponseBody().write(body);
        exchange.close();
    }

    @FunctionalInterface
    private interface Handler {
        void handle(HttpExchange exchange) throws IOException;
    }
}
