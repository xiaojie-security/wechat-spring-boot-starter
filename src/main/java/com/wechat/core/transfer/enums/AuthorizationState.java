package com.wechat.core.transfer.enums;

import com.google.gson.annotations.SerializedName;

public enum AuthorizationState {
    @SerializedName("TAKING_EFFECT")
    TAKING_EFFECT,
    @SerializedName("CLOSED")
    CLOSED,
    @SerializedName("WAIT_USER_CONFIRM")
    WAIT_USER_CONFIRM
  }
