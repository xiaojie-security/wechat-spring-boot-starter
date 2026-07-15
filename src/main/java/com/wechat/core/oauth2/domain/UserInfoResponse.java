package com.wechat.core.oauth2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户信息响应类
 * 用于封装获取用户信息接口的返回数据
 * 
 * @author YourName
 * @version 1.0
 * @since 2026-07-15
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoResponse {
    
    /**
     * 用户唯一标识
     * 普通用户的标识，对当前开发者账号唯一
     */
    private String openid;
    
    /**
     * 用户昵称
     */
    private String nickname;
    
    /**
     * 用户性别
     * 1 表示男性，2 表示女性，0 表示未知
     */
    private Integer sex;
    
    /**
     * 用户所在省份
     */
    private String province;
    
    /**
     * 用户所在城市
     */
    private String city;
    
    /**
     * 用户所在国家
     */
    private String country;
    
    /**
     * 用户头像链接
     * 用户头像的URL地址
     */
    private String headimgurl;
    
    /**
     * 用户特权信息
     * 用户拥有的特权列表
     */
    private List<String> privilege;
    
    /**
     * 用户统一标识
     * 当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段
     */
    private String unionid;

}
