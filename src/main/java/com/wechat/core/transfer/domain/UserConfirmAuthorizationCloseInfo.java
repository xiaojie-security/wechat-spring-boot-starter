package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.transfer.enums.CloseReason;

public class UserConfirmAuthorizationCloseInfo {
    @SerializedName("close_time")
    public String closeTime;
  
    @SerializedName("close_reason")
    public CloseReason closeReason;
  }
