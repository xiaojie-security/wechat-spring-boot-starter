package com.wechat.core.xcx.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小程序二维码线条 RGB 颜色。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatXcxQRCodeLineColor {

    /**
     * 红色分量，取值范围为 0 至 255。
     */
    private Integer r;

    /**
     * 绿色分量，取值范围为 0 至 255。
     */
    private Integer g;

    /**
     * 蓝色分量，取值范围为 0 至 255。
     */
    private Integer b;
}
