package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 商家转账到用户请求参数。
 * 用于封装调用微信支付转账接口时提交的请求体字段。
 */
public class TransferToUserRequest {
    /**
     * 商户应用 AppID。
     * 该值需要与商户号存在绑定关系。
     */
    @SerializedName("appid")
    public String appid;

    /**
     * 商户转账单号。
     * 商户系统内部的唯一业务单号，用于幂等和后续查询。
     */
    @SerializedName("out_bill_no")
    public String outBillNo;

    /**
     * 转账场景 ID。
     * 用于标识这笔转账属于哪一种业务场景，例如佣金、退款补差等。
     */
    @SerializedName("transfer_scene_id")
    public String transferSceneId;

    /**
     * 收款用户 OpenID。
     * 表示这笔钱要转给哪个微信用户。
     */
    @SerializedName("openid")
    public String openid;

    /**
     * 收款用户真实姓名。
     * 敏感字段，通常需要使用微信支付公钥加密后再传输。
     */
    @SerializedName("user_name")
    public String userName;

    /**
     * 转账金额，单位为分。
     */
    @SerializedName("transfer_amount")
    public Long transferAmount;

    /**
     * 转账备注。
     * 会展示给收款用户，用于说明打款用途。
     */
    @SerializedName("transfer_remark")
    public String transferRemark;

    /**
     * 转账结果回调通知地址。
     * 微信支付在转账状态变更后会向该地址推送通知。
     * 外部未显式传递时，服务层会从商户配置快照注入 transferNotifyUrl。
     */
    @SerializedName("notify_url")
    public String notifyUrl;

    /**
     * 用户收款感知信息。
     * 用于向收款用户展示本次转账的业务说明。
     */
    @SerializedName("user_recv_perception")
    public String userRecvPerception;

    /**
     * 转账场景报备信息列表。
     * 某些业务场景下，微信支付要求补充业务说明或凭证信息。
     */
    @SerializedName("transfer_scene_report_infos")
    public List<TransferSceneReportInfo> transferSceneReportInfos;

    /**
     * 免确认收款授权信息。
     * 首次转账时可附带该对象，引导用户在收款确认过程中完成后续免确认授权。
     */
    @SerializedName("authorization_info")
    public UserConfirmAuthorizationInfo authorizationInfo;

    /**
     * 出资商户号。
     * 服务商模式或特定业务模式下可能需要传该字段。
     */
    @SerializedName("sponsor_mchid")
    public String sponsorMchid;

    /**
     * 微信免确认收款授权单号。
     * 与 outAuthorizationNo 二选一，用于用户授权后转账。
     */
    @SerializedName("authorization_id")
    public String authorizationId;

    /**
     * 商户侧免确认收款授权单号。
     * 与 authorizationId 二选一，用于用户授权后转账。
     */
    @SerializedName("out_authorization_no")
    public String outAuthorizationNo;
}
