package com.wechat.core.profitsharing.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.profitsharing.enums.ProfitsharingReceiverType;

import java.util.List;

/**
 * 请求分账参数。
 */
public class ProfitsharingOrderRequest {
    /**
     * 商户应用 AppID。
     * 服务层会在发起请求前自动注入。
     */
    @SerializedName("appid")
    public String appid;

    /**
     * 微信支付订单号。
     */
    @SerializedName("transaction_id")
    public String transactionId;

    /**
     * 商户分账单号。
     * 需在商户系统内保持唯一。
     */
    @SerializedName("out_order_no")
    public String outOrderNo;

    /**
     * 分账接收方列表。
     */
    @SerializedName("receivers")
    public List<Receiver> receivers;

    /**
     * 是否解冻剩余未分资金。
     * 当本次分账完成后无需继续分账时可传 true。
     */
    @SerializedName("unfreeze_unsplit")
    public Boolean unfreezeUnsplit;

    /**
     * 单个分账接收方参数。
     */
    public static class Receiver {
        /**
         * 分账接收方类型。
         */
        @SerializedName("type")
        public ProfitsharingReceiverType type;

        /**
         * 分账接收方账号。
         */
        @SerializedName("account")
        public String account;

        /**
         * 分账金额，单位为分。
         */
        @SerializedName("amount")
        public Long amount;

        /**
         * 分账描述。
         * 该描述会体现在微信支付侧分账明细中。
         */
        @SerializedName("description")
        public String description;

        /**
         * 接收方姓名。
         * 服务层会在请求发送前自动加密。
         */
        @SerializedName("name")
        public String name;
    }
}
