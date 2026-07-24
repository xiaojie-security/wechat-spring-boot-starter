package com.wechat.core.xcx.domain;

import com.google.gson.annotations.SerializedName;
import com.wechat.core.xcx.enums.WechatXcxEnvVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信小程序无限量二维码请求参数。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatXcxQRCodeRequest {

    /**
     * 二维码场景值，最多 32 个可见字符。
     */
    private String scene;

    /**
     * 小程序页面路径，根路径前不能带斜杠。
     */
    private String page;

    /**
     * 是否检查 page 参数是否存在于现网版本小程序中。
     */
    @Builder.Default
    @SerializedName("check_path")
    private Boolean checkPath = true;

    /**
     * 小程序版本，可选值为 release、trial、develop。
     */
    @Builder.Default
    @SerializedName("env_version")
    private WechatXcxEnvVersion envVersion = WechatXcxEnvVersion.RELEASE;

    /**
     * 二维码宽度，单位为像素，取值范围为 280 至 1280。
     */
    @Builder.Default
    private Integer width = 430;

    /**
     * 是否自动配置线条颜色。
     */
    @Builder.Default
    @SerializedName("auto_color")
    private Boolean autoColor = false;

    /**
     * 是否需要透明底色。
     */
    @Builder.Default
    @SerializedName("is_hyaline")
    private Boolean hyaline = false;

    /**
     * 二维码线条颜色。
     */
    @SerializedName("line_color")
    private WechatXcxQRCodeLineColor lineColor;
}
