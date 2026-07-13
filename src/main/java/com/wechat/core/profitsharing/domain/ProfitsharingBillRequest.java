package com.wechat.core.profitsharing.domain;

import com.wechat.core.profitsharing.enums.ProfitsharingBillTarType;

/**
 * 申请分账账单请求参数。
 */
public class ProfitsharingBillRequest {
    /**
     * 账单日期。
     * 按 yyyy-MM-dd 格式传递。
     */
    public String billDate;

    /**
     * 压缩类型。
     * 当前官方示例通常为 GZIP。
     */
    public ProfitsharingBillTarType tarType;
}
