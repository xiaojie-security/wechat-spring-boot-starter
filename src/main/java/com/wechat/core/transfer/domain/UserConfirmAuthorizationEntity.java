package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.transfer.enums.AuthorizationState;

public class UserConfirmAuthorizationEntity {
    @SerializedName("out_authorization_no")
    public String outAuthorizationNo;
  
    @SerializedName("appid")
    public String appid;
  
    @SerializedName("openid")
    public String openid;
  
    @SerializedName("user_display_name")
    public String userDisplayName;
  
    @SerializedName("authorization_id")
    public String authorizationId;
  
    @SerializedName("state")
    public AuthorizationState state;
  
    @SerializedName("authorize_time")
    public String authorizeTime;
  
    @SerializedName("close_info")
    public UserConfirmAuthorizationCloseInfo closeInfo;
  
    @SerializedName("transfer_scene_id")
    public String transferSceneId;
  
    @SerializedName("user_recv_perception")
    public String userRecvPerception;
  
    @SerializedName("create_time")
    public String createTime;
  
    @SerializedName("package_info")
    public String packageInfo;
  }
