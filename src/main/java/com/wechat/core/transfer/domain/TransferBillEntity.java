package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.transfer.enums.TransferBillStatus;

public class TransferBillEntity {
    @SerializedName("mch_id")
    public String mchId;
  
    @SerializedName("out_bill_no")
    public String outBillNo;
  
    @SerializedName("transfer_bill_no")
    public String transferBillNo;
  
    @SerializedName("appid")
    public String appid;
  
    @SerializedName("state")
    public TransferBillStatus state;
  
    @SerializedName("transfer_amount")
    public Long transferAmount;
  
    @SerializedName("transfer_remark")
    public String transferRemark;
  
    @SerializedName("fail_reason")
    public String failReason;
  
    @SerializedName("openid")
    public String openid;
  
    @SerializedName("user_name")
    public String userName;
  
    @SerializedName("create_time")
    public String createTime;
  
    @SerializedName("update_time")
    public String updateTime;
}
