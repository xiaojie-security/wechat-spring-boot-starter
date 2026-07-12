package com.wechat.core.transfer.enums;

import com.google.gson.annotations.SerializedName;

public enum CloseReason {
    @SerializedName("CLOSE_VIA_MCH_API")
    CLOSE_VIA_MCH_API,
    @SerializedName("USER_CLOSE")
    USER_CLOSE,
    @SerializedName("USER_OVERDUE_UNCONFIRMED")
    USER_OVERDUE_UNCONFIRMED,
    @SerializedName("TRANSFER_RISK")
    TRANSFER_RISK,
    @SerializedName("USER_ACCOUNT_ABNORMAL")
    USER_ACCOUNT_ABNORMAL
  }
