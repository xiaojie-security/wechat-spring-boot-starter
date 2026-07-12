package com.wechat.core.transfer.service;

import com.wechat.core.transfer.domain.TransferToUserResponse;
import com.wechat.core.transfer.domain.TransferBillEntity;
import com.wechat.core.transfer.domain.UserConfirmAuthorizationEntity;
import com.wechat.core.transfer.domain.UserConfirmAuthorizationInfo;

public interface WechatTransferService {


    /**
     * 发起转账并完成免确认收款授权
     * @param outBillNo 商户系统内部的商家单号，要求此参数只能由数字、大小写字母组成，在商户系统内部唯一
     * @param transferSceneId  该笔转账使用的转账场景，可前往“商户平台-产品中心-商家转账”中申请。如：1000（现金营销），1006（企业报销）等
     * @param openid 用户在商户appid下的唯一标识。发起转账前需获取到用户的OpenID
     * @param userName 收款方真实姓名。若传入收款用户姓名，微信支付会校验收款用户与输入姓名是否一致。转账金额>=2,000元时，必须传入该值。
     * @param transferAmount 转账金额，单位为“分”
     * @param transferRemark 转账备注，用户收款时可见该备注信息，UTF8编码，最多允许32个字符
     * @return
     */
    TransferToUserResponse transferWithAutoApproval(
            String outBillNo,
            String transferSceneId,
            String openid,
            String userName,
            Long transferAmount,
            String transferRemark,
            UserConfirmAuthorizationInfo authorizationInfo
    );


    /**
     * 商户单号查询授权结果
     * 若用户收款成功且免确认收款授权成功
     *
     * 接口返回的HTTP状态码为200，且授权状态为 TAKING_EFFECT
     *
     * 若用户未收款成功或收款成功但未同意免确认收款授权，或者因业务规则、风控策略等限制无法完成授权
     *
     * 系统将不会处理授权信息，接口将返回NOT_FOUND 错误
     *
     */
    UserConfirmAuthorizationEntity queryAuthorization(String outAuthorizationNo, boolean  isDisplayAuthorization);


    /**
     * 商户单号查询转账单
     */
    TransferBillEntity queryTransferBillByOutBillNo(String outBillNo);

    /**
     * 微信单号查询转账单
     */
    TransferBillEntity queryTransferBillByTransferBillNo(String transferBillNo);

    /**
     * 解除免确认收款授权
     */
    UserConfirmAuthorizationEntity closeAuthorization(String outAuthorizationNo);
}
