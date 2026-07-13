package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.payment.enums.HashType;

/**
 * 账单下载信息实体。
 * 用于承接申请交易账单或资金账单接口返回的下载地址与摘要信息。
 */
public class BillDownloadEntity {
    /**
     * 摘要算法类型。
     */
    @SerializedName("hash_type")
    public HashType hashType;

    /**
     * 账单文件摘要值。
     * 可用于下载后校验文件完整性。
     */
    @SerializedName("hash_value")
    public String hashValue;

    /**
     * 账单下载地址。
     */
    @SerializedName("download_url")
    public String downloadUrl;
}
