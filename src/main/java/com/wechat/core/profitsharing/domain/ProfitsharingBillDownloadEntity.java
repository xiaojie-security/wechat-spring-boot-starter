package com.wechat.core.profitsharing.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.profitsharing.enums.ProfitsharingBillHashType;

/**
 * 分账账单下载信息。
 */
public class ProfitsharingBillDownloadEntity {
    /**
     * 账单下载地址。
     */
    @SerializedName("download_url")
    public String downloadUrl;

    /**
     * 账单摘要算法。
     */
    @SerializedName("hash_type")
    public ProfitsharingBillHashType hashType;

    /**
     * 账单摘要值。
     */
    @SerializedName("hash_value")
    public String hashValue;
}
