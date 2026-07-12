package com.wechat.core.transfer.domain;

import com.google.gson.annotations.SerializedName;

/**
 * 转账场景报备项。
 * 用于描述某一项额外业务信息，提交给微信支付做场景合规校验。
 */
public class TransferSceneReportInfo {
    /**
     * 报备信息类型。
     * 具体可选值需以微信支付该场景文档要求为准。
     */
    @SerializedName("info_type")
    public String infoType;

    /**
     * 报备信息内容。
     * 与 {@link #infoType} 对应，通常是文字说明、编号或业务凭证内容。
     */
    @SerializedName("info_content")
    public String infoContent;
}
