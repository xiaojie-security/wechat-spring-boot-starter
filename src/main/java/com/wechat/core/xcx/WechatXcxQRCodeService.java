package com.wechat.core.xcx;

import com.wechat.core.xcx.domain.WechatXcxQRCodeRequest;

/**
 * 微信小程序二维码服务。
 */
public interface WechatXcxQRCodeService {

    /**
     * 获取小程序无限量带参数二维码。
     *
     * @param request 二维码请求参数
     * @return 二维码 PNG 图片字节
     */
    byte[] getUnlimitedQRCode(WechatXcxQRCodeRequest request);

    /**
     * 获取小程序无限量带参数二维码的 Base64 内容。
     *
     * @param request 二维码请求参数
     * @return 纯 Base64 编码的二维码 PNG 内容，不包含 data URI 前缀
     */
    String getUnlimitedQRCodeBase64(WechatXcxQRCodeRequest request);
}
