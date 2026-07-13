package com.wechat.core.payment.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.payment.enums.HashType;

public class BillDownloadEntity {
    @SerializedName("hash_type")
    public HashType hashType;

    @SerializedName("hash_value")
    public String hashValue;

    @SerializedName("download_url")
    public String downloadUrl;
}
