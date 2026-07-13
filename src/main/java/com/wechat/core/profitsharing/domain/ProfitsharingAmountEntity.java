package com.wechat.core.profitsharing.domain;

import com.google.gson.annotations.SerializedName;

/**
 * 剩余待分金额实体。
 * 用于承接查询剩余待分金额接口返回的数据。
 */
public class ProfitsharingAmountEntity {
    /**
     * 当前订单剩余待分金额，单位为分。
     */
    @SerializedName("unsplit_amount")
    public Long unsplitAmount;
}
