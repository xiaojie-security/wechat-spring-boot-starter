package com.wechat.core.profitsharing.service;

import com.wechat.core.profitsharing.domain.DeleteProfitsharingReceiverRequest;
import com.wechat.core.profitsharing.domain.ProfitsharingAmountEntity;
import com.wechat.core.profitsharing.domain.ProfitsharingBillDownloadEntity;
import com.wechat.core.profitsharing.domain.ProfitsharingBillRequest;
import com.wechat.core.profitsharing.domain.ProfitsharingOrderEntity;
import com.wechat.core.profitsharing.domain.ProfitsharingOrderRequest;
import com.wechat.core.profitsharing.domain.ProfitsharingReceiverRequest;
import com.wechat.core.profitsharing.domain.ProfitsharingReturnOrderEntity;
import com.wechat.core.profitsharing.domain.ProfitsharingReturnOrderRequest;
import com.wechat.core.profitsharing.domain.QueryProfitsharingAmountRequest;
import com.wechat.core.profitsharing.domain.QueryProfitsharingOrderRequest;
import com.wechat.core.profitsharing.domain.QueryProfitsharingReturnOrderRequest;
import com.wechat.core.profitsharing.domain.UnfreezeProfitsharingOrderRequest;

/**
 * 微信分账服务接口。
 * 封装请求分账、分账回退、接收方管理以及分账账单申请等能力。
 */
public interface WechatProfitsharingService {
    /**
     * 请求分账。
     *
     * @param request 分账请求参数。
     *                其中 {@code appid} 支持自动注入：
     *                如果请求对象自身已传值，则优先使用请求值；
     *                如果未传，则自动使用服务中已初始化的商户应用 AppID。
     * @return 分账单详情，包含分账单状态与接收方明细结果
     */
    ProfitsharingOrderEntity createOrder(ProfitsharingOrderRequest request);

    /**
     * 查询分账结果。
     *
     * @param request 查询请求参数，需包含商户分账单号与微信支付订单号
     * @return 分账单详情，包含分账状态与接收方处理结果
     */
    ProfitsharingOrderEntity queryOrderByOutOrderNo(QueryProfitsharingOrderRequest request);

    /**
     * 请求分账回退。
     *
     * @param request 分账回退请求参数。
     *                其中 {@code returnMchid} 支持自动注入：
     *                如果请求对象自身已传值，则优先使用请求值；
     *                如果未传，则自动使用服务中已初始化的商户号。
     * @return 分账回退单详情，包含回退结果与失败原因等
     */
    ProfitsharingReturnOrderEntity createReturnOrder(ProfitsharingReturnOrderRequest request);

    /**
     * 查询分账回退结果。
     *
     * @param request 查询请求参数，需包含商户分账回退单号
     * @return 分账回退单详情，包含回退状态、金额和失败原因等
     */
    ProfitsharingReturnOrderEntity queryReturnOrderByOutReturnNo(QueryProfitsharingReturnOrderRequest request);

    /**
     * 解冻剩余资金。
     *
     * @param request 解冻请求参数，需包含微信支付订单号、商户分账单号与解冻描述
     * @return 分账单详情，可据此确认该分账单当前状态及接收方结果
     */
    ProfitsharingOrderEntity unfreezeRemainingFunds(UnfreezeProfitsharingOrderRequest request);

    /**
     * 查询剩余待分金额。
     *
     * @param request 查询请求参数，需包含微信支付订单号
     * @return 剩余待分金额信息
     */
    ProfitsharingAmountEntity queryRemainingAmount(QueryProfitsharingAmountRequest request);

    /**
     * 添加分账接收方。
     *
     * @param request 接收方请求参数。
     *                其中 {@code appid} 支持自动注入：
     *                如果请求对象自身已传值，则优先使用请求值；
     *                如果未传，则自动使用服务中已初始化的商户应用 AppID。
     */
    void addReceiver(ProfitsharingReceiverRequest request);

    /**
     * 删除分账接收方。
     *
     * @param request 删除接收方请求参数。
     *                其中 {@code appid} 支持自动注入：
     *                如果请求对象自身已传值，则优先使用请求值；
     *                如果未传，则自动使用服务中已初始化的商户应用 AppID。
     */
    void deleteReceiver(DeleteProfitsharingReceiverRequest request);

    /**
     * 申请分账账单。
     *
     * @param request 账单申请参数，需包含账单日期与压缩格式
     * @return 账单下载信息，包含下载地址与摘要校验信息
     */
    ProfitsharingBillDownloadEntity getBill(ProfitsharingBillRequest request);
}
