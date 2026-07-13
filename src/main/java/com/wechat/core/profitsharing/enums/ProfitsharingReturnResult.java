package com.wechat.core.profitsharing.enums;

import com.google.gson.annotations.SerializedName;

/**
 * 分账回退结果枚举。
 */
public enum ProfitsharingReturnResult {
    /**
     * 回退处理中。
     */
    @SerializedName("PROCESSING")
    PROCESSING,

    /**
     * 回退成功。
     */
    @SerializedName("SUCCESS")
    SUCCESS,

    /**
     * 回退失败。
     */
    @SerializedName("FAILED")
    FAILED
}
