package com.wechat.core.transfer.service;

import com.wechat.core.transfer.domain.AutoApprovalResultNotifyEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 免确认收款授权结果通知服务接口。
 * 用于接收微信支付授权结果通知并解密业务内容。
 */
public interface WechatAutoApprovalResultNotifyService {
    /**
     * 处理免确认收款授权结果通知。
     * 方法会读取请求报文、解密 resource 字段中的业务内容，并将结果封装为独立实体返回。
     * 同时会根据处理结果向微信支付回写回调应答。
     *
     * @param request 微信支付回调请求
     * @param response 微信支付回调响应
     * @return 解密后的授权结果通知业务内容
     */
    AutoApprovalResultNotifyEntity parseNotify(HttpServletRequest request, HttpServletResponse response);
}
