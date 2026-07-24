package com.wechat.core.transfer.service;

import com.wechat.core.transfer.domain.TransferCallbackEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 微信商家转账结果回调通知服务接口。
 * 用于接收微信支付转账结果通知并解密通知内容。
 */
public interface WechatTransferCallbackService {
    /**
     * 处理微信商家转账结果回调通知。
     * 方法会读取请求报文、解密 resource 字段中的业务内容，并将结果封装为独立实体返回。
     * 同时会根据处理结果向微信支付回写回调应答。
     *
     * @param request 微信支付转账回调请求
     * @param response 微信支付转账回调响应
     * @return 解密后的转账结果回调业务内容
     */
    TransferCallbackEntity parseCallback(HttpServletRequest request, HttpServletResponse response);
}
