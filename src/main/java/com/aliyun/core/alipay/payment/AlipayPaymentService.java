package com.aliyun.core.alipay.payment;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.aliyun.core.alipay.payment.domain.AlipayPaymentRequest;
import com.aliyun.core.alipay.payment.domain.AlipayPaymentResponse;
import com.aliyun.properties.AlipayPaymentProperties;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 支付宝支付服务。
 * 封装订单码支付、APP 支付、H5 支付和 PC 页面支付四类下单能力。
 */
@Slf4j
@RequiredArgsConstructor
public class AlipayPaymentService implements InitializingBean {

    private static final String PAGE_EXECUTE_METHOD = "POST";
    private static final String PRODUCT_CODE_PRECREATE = "FACE_TO_FACE_PAYMENT";
    private static final String PRODUCT_CODE_APP = "QUICK_MSECURITY_PAY";
    private static final String PRODUCT_CODE_WAP = "QUICK_WAP_WAY";
    private static final String PRODUCT_CODE_PAGE = "FAST_INSTANT_TRADE_PAY";

    private static final Gson GSON = new Gson();

    private final AlipayPaymentProperties properties;

    private AlipayClient alipayClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(properties.getServerUrl(), "alipay.payment.server-url must not be blank");
        Assert.hasText(properties.getAppId(), "alipay.payment.app-id must not be blank");
        Assert.hasText(properties.getPrivateKey(), "alipay.payment.private-key must not be blank");
        if (!properties.isKeyMode() && !properties.isCertMode()) {
            throw new IllegalArgumentException("Alipay client requires either alipayPublicKey or certificate paths");
        }

        AlipayConfig config = new AlipayConfig();
        config.setServerUrl(properties.getServerUrl());
        config.setAppId(properties.getAppId());
        config.setPrivateKey(properties.getPrivateKey());
        config.setFormat(properties.getFormat());
        config.setCharset(properties.getCharset());
        config.setSignType(properties.getSignType());
        if (properties.isKeyMode()) {
            config.setAlipayPublicKey(properties.getAlipayPublicKey());
        } else {
            config.setAppCertPath(properties.getAppCertPath());
            config.setAlipayPublicCertPath(properties.getAlipayPublicCertPath());
            config.setRootCertPath(properties.getRootCertPath());
        }
        if (Boolean.TRUE.equals(properties.getEncrypt())) {
            config.setEncryptKey(properties.getEncryptKey());
        }
        alipayClient = new DefaultAlipayClient(config);
    }

    /**
     * alipay.trade.precreate 订单码支付。
     */
    public AlipayPaymentResponse precreate(AlipayPaymentRequest request) {
        AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest();
        fillCommonRequest(alipayRequest, request);
        alipayRequest.setBizContent(buildBizContent(request, defaultIfBlank(request.productCode, PRODUCT_CODE_PRECREATE)));
        try {
            AlipayTradePrecreateResponse response = alipayClient.execute(alipayRequest);
            AlipayPaymentResponse result = buildBaseResponse(response);
            result.qrCode = response.getQrCode();
            result.outTradeNo = response.getOutTradeNo();
            return result;
        } catch (AlipayApiException e) {
            log.error("AlipayPaymentService.precreate 调用支付宝接口异常, outTradeNo={}", request.outTradeNo, e);
            throw new IllegalStateException("Call alipay.trade.precreate failed", e);
        }
    }

    /**
     * alipay.trade.app.pay APP 支付。
     */
    public AlipayPaymentResponse appPay(AlipayPaymentRequest request) {
        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
        fillCommonRequest(alipayRequest, request);
        alipayRequest.setBizContent(buildBizContent(request, defaultIfBlank(request.productCode, PRODUCT_CODE_APP)));
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayRequest);
            AlipayPaymentResponse result = buildBaseResponse(response);
            result.orderStr = response.getBody();
            result.body = response.getBody();
            result.outTradeNo = response.getOutTradeNo();
            result.tradeNo = response.getTradeNo();
            if (isBlank(result.code) && !isBlank(result.orderStr)) {
                result.success = true;
            }
            return result;
        } catch (AlipayApiException e) {
            log.error("AlipayPaymentService.appPay 调用支付宝接口异常, outTradeNo={}", request.outTradeNo, e);
            throw new IllegalStateException("Call alipay.trade.app.pay failed", e);
        }
    }

    /**
     * alipay.trade.wap.pay 手机网站支付。
     */
    public AlipayPaymentResponse wapPay(AlipayPaymentRequest request) {
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        fillCommonRequest(alipayRequest, request);
        if (!isBlank(request.quitUrl)) {
            alipayRequest.putOtherTextParam("quit_url", request.quitUrl);
        }
        alipayRequest.setBizContent(buildBizContent(request, defaultIfBlank(request.productCode, PRODUCT_CODE_WAP)));
        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest, PAGE_EXECUTE_METHOD);
            AlipayPaymentResponse result = buildBaseResponse(response);
            result.body = response.getBody();
            if (isBlank(result.code) && !isBlank(result.body)) {
                result.success = true;
            }
            return result;
        } catch (AlipayApiException e) {
            log.error("AlipayPaymentService.wapPay 调用支付宝接口异常, outTradeNo={}", request.outTradeNo, e);
            throw new IllegalStateException("Call alipay.trade.wap.pay failed", e);
        }
    }

    /**
     * alipay.trade.page.pay PC 页面支付。
     */
    public AlipayPaymentResponse pagePay(AlipayPaymentRequest request) {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        fillCommonRequest(alipayRequest, request);
        alipayRequest.setBizContent(buildBizContent(request, defaultIfBlank(request.productCode, PRODUCT_CODE_PAGE)));
        try {
            AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayRequest, PAGE_EXECUTE_METHOD);
            AlipayPaymentResponse result = buildBaseResponse(response);
            result.body = response.getBody();
            if (isBlank(result.code) && !isBlank(result.body)) {
                result.success = true;
            }
            return result;
        } catch (AlipayApiException e) {
            log.error("AlipayPaymentService.pagePay 调用支付宝接口异常, outTradeNo={}", request.outTradeNo, e);
            throw new IllegalStateException("Call alipay.trade.page.pay failed", e);
        }
    }

    private void fillCommonRequest(com.alipay.api.AlipayRequest<?> target, AlipayPaymentRequest source) {
        target.setNotifyUrl(defaultIfBlank(source.notifyUrl, properties.getNotifyUrl()));
        target.setReturnUrl(defaultIfBlank(source.returnUrl, properties.getReturnUrl()));
        target.setNeedEncrypt(Boolean.TRUE.equals(defaultEncrypt(source.needEncrypt)));
    }

    private String buildBizContent(AlipayPaymentRequest request, String productCode) {
        JsonObject bizContent = new JsonObject();
        addProperty(bizContent, "out_trade_no", request.outTradeNo);
        addProperty(bizContent, "total_amount", request.totalAmount);
        addProperty(bizContent, "subject", request.subject);
        addProperty(bizContent, "body", request.body);
        addProperty(bizContent, "product_code", productCode);
        addProperty(bizContent, "timeout_express", request.timeoutExpress);
        addProperty(bizContent, "seller_id", request.sellerId);
        addProperty(bizContent, "store_id", request.storeId);
        addProperty(bizContent, "operator_id", request.operatorId);
        addProperty(bizContent, "terminal_id", request.terminalId);
        addProperty(bizContent, "goods_type", request.goodsType);
        addProperty(bizContent, "passback_params", request.passbackParams);
        addProperty(bizContent, "merchant_order_no", request.merchantOrderNo);
        addProperty(bizContent, "qr_code_timeout_express", request.qrCodeTimeoutExpress);
        addProperty(bizContent, "promo_params", request.promoParams);
        addJson(bizContent, "extend_params", request.extendParams);
        addJson(bizContent, "business_params", request.businessParams);
        addJson(bizContent, "settle_info", request.settleInfo);
        addJson(bizContent, "goods_detail", request.goodsDetail);
        mergeExtraBizContent(bizContent, request.extraBizContent);
        return GSON.toJson(bizContent);
    }

    private AlipayPaymentResponse buildBaseResponse(AlipayResponse response) {
        AlipayPaymentResponse result = new AlipayPaymentResponse();
        result.code = response.getCode();
        result.msg = response.getMsg();
        result.subCode = response.getSubCode();
        result.subMsg = response.getSubMsg();
        result.success = isSuccessful(response);
        return result;
    }

    private boolean isSuccessful(AlipayResponse response) {
        if (response == null) {
            return false;
        }
        if ("10000".equals(response.getCode())) {
            return true;
        }
        return isBlank(response.getCode()) && isBlank(response.getSubCode());
    }

    private void mergeExtraBizContent(JsonObject target, Map<String, Object> extraBizContent) {
        if (extraBizContent == null || extraBizContent.isEmpty()) {
            return;
        }
        JsonObject extra = GSON.toJsonTree(extraBizContent).getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : extra.entrySet()) {
            target.add(entry.getKey(), entry.getValue());
        }
    }

    private void addJson(JsonObject target, String key, Object value) {
        if (value == null) {
            return;
        }
        target.add(key, GSON.toJsonTree(value));
    }

    private void addProperty(JsonObject target, String key, String value) {
        if (isBlank(value)) {
            return;
        }
        target.addProperty(key, value);
    }

    private Boolean defaultEncrypt(Boolean needEncrypt) {
        if (needEncrypt != null) {
            return needEncrypt;
        }
        return properties.getEncrypt();
    }

    private String defaultIfBlank(String value, String defaultValue) {
        if (!isBlank(value)) {
            return value;
        }
        return defaultValue;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
