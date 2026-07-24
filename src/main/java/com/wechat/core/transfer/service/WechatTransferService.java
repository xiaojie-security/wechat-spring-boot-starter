package com.wechat.core.transfer.service;

import com.wechat.core.transfer.domain.CloseAuthorizationRequest;
import com.wechat.core.transfer.domain.GetRequest;
import com.wechat.core.transfer.domain.GetTransferBillByNoRequest;
import com.wechat.core.transfer.domain.GetTransferBillByOutNoRequest;
import com.wechat.core.transfer.domain.TransferToUserRequest;
import com.wechat.core.transfer.domain.TransferToUserResponse;
import com.wechat.core.transfer.domain.TransferBillEntity;
import com.wechat.core.transfer.domain.UserConfirmAuthorizationRequest;
import com.wechat.core.transfer.domain.UserConfirmAuthorizationEntity;

/**
 * 商家转账服务接口。
 * 封装发起转账、查询转账单、查询授权结果和解除授权等能力。
 */
public interface WechatTransferService {
    /**
     * 发起免确认收款授权。
     *
     * @param request 免确认收款授权请求参数，需包含商户授权单号、用户 OpenID、转账场景和授权通知地址；
     *                其中 {@code appid} 未传时自动使用服务中已初始化的商户应用 AppID
     * @return 授权受理结果，包含授权状态和拉起用户授权页面所需的 package 信息
     */
    UserConfirmAuthorizationEntity createAuthorization(UserConfirmAuthorizationRequest request);

    /**
     * 用户完成免确认收款授权后发起转账。
     *
     * @param request 转账请求参数，需通过 {@code authorizationId} 或 {@code outAuthorizationNo}
     *                指定已生效的用户授权；其中 {@code appid} 未传时自动使用服务中已初始化的商户应用 AppID
     * @return 转账受理结果
     */
    TransferToUserResponse transferAfterAuthorization(TransferToUserRequest request);

    /**
     * 发起转账并完成免确认收款授权。
     *
     * @param request 转账请求参数。
     *                其中 {@code appid} 支持自动注入：
     *                如果请求对象自身已传值，则优先使用请求值；
     *                如果未传，则自动使用服务中已初始化的商户应用 AppID。
     * @return 转账受理结果，包含转账单号、状态以及可能返回的授权拉起参数
     */
    TransferToUserResponse transferWithAutoApproval(TransferToUserRequest request);

    /**
     * 按商户授权单号查询授权结果。
     *
     * @param request 授权查询请求参数，需包含商户授权单号；
     *                可选传入是否返回授权拉起参数
     * @return 授权详情，包含授权状态、授权单号、授权时间和关闭信息等
     */
    UserConfirmAuthorizationEntity queryAuthorization(GetRequest request);

    /**
     * 按商户转账单号查询转账单。
     *
     * @param request 转账单查询请求参数，需包含商户转账单号
     * @return 转账单详情，包含转账状态、金额、收款用户和失败原因等
     */
    TransferBillEntity queryTransferBillByOutBillNo(GetTransferBillByOutNoRequest request);

    /**
     * 按微信支付转账单号查询转账单。
     *
     * @param request 转账单查询请求参数，需包含微信支付转账单号
     * @return 转账单详情，包含转账状态、金额、收款用户和失败原因等
     */
    TransferBillEntity queryTransferBillByTransferBillNo(GetTransferBillByNoRequest request);

    /**
     * 解除免确认收款授权。
     *
     * @param request 解除授权请求参数，需包含商户授权单号
     * @return 授权详情，通常可据此确认授权已关闭及关闭原因
     */
    UserConfirmAuthorizationEntity closeAuthorization(CloseAuthorizationRequest request);
}
