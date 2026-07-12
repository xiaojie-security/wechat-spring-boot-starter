package com.wechat.core.transfer.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTransferBillByOutNoRequest {
    @SerializedName("out_bill_no")
    @Expose(serialize = false)
    public String outBillNo;
  }
