package com.wechat.core.transfer.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRequest {
    @SerializedName("out_authorization_no")
    @Expose(serialize = false)
    public String outAuthorizationNo;
  
    @SerializedName("is_display_authorization")
    @Expose(serialize = false)
    public Boolean isDisplayAuthorization;
  }
