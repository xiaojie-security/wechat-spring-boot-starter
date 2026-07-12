# aliyun-spring-boot-starter

一个面向 Spring Boot 3 的阿里云能力 Starter，将 OSS、IMM、STS、短信、PNS 和支付宝能力做成自动装配。

- OSS 对象存储上传、下载、删除、分片上传、签名访问
- IMM 媒体转码
- STS 临时凭证获取
- 短信发送
- PNS 号码认证与短信验证码校验
- 支付宝 APP 支付、扫码支付、资金转账、OAuth2 授权

## 配置总则

- 所有配置都放在 `aliyun` 根前缀下。
- 各个服务分开配置，不再把所有信息聚合到一起。
- 支付宝子配置支持回退到公共配置：子配置不写的字段，直接使用 `aliyun.pay` 里的值。
- 支付宝子配置名字是 `app`、`fund`、`scan-code`、`oauth`，不是 `oauth2`。
- OSS、IMM、SMS、PNS 如果使用 `ramRoleArn` 获取临时凭证，建议同时开启 `aliyun.sts`。

## 各服务配置

### STS 临时凭证

```yaml
aliyun:
  sts:
    enable: true
    access-key-id: your-access-key-id
    access-key-secret: your-access-key-secret
    endpoint: sts.cn-hangzhou.aliyuncs.com
    expire: 3600
```

- 必填：`enable`、`access-key-id`、`access-key-secret`、`endpoint`
- 可选：`expire`

### OSS 对象存储

```yaml
aliyun:
  oss:
    enable: true
    endpoint: oss-cn-hangzhou.aliyuncs.com
    region: cn-hangzhou
    default-bucket: your-default-bucket
    uri: https://
    expire: 900
    ram-role-arn: acs:ram::xxxx:role/your-role
    callback: https://your-domain.com/api/oss/callback
    buckets:
      image: your-image-bucket
      video: your-video-bucket
```

- 必填：`enable`、`endpoint`、`region`、`default-bucket`
- 建议：`uri`、`expire`、`ram-role-arn`、`callback`、`buckets`

### IMM 媒体转码

```yaml
aliyun:
  imm:
    enable: true
    project-name: your-imm-project
    region: cn-hangzhou
    codec: H.264
    endpoint-override: imm.cn-hangzhou.aliyuncs.com
    container: mp4
    uri: oss://
    ram-role-arn: acs:ram::xxxx:role/your-role
```

- 必填：`enable`、`project-name`、`region`、`codec`、`endpoint-override`、`container`、`uri`、`ram-role-arn`

### 短信服务

```yaml
aliyun:
  sms:
    enable: true
    endpoint: dysmsapi.aliyuncs.com
    region: cn-hangzhou
    default-sign-name: 示例签名
    sign-names:
      login_register: 示例签名
    ram-role-arn: acs:ram::xxxx:role/your-role
```

- 必填：`enable`、`endpoint`、`region`、`default-sign-name`
- 可选：`sign-names`、`ram-role-arn`

### PNS 号码认证

```yaml
aliyun:
  pns:
    enable: true
    sign-name: 示例签名
    endpoint: dypnsapi.aliyuncs.com
    region: cn-hangzhou
    ram-role-arn: acs:ram::xxxx:role/your-role
```

- 必填：`enable`、`sign-name`、`endpoint`、`region`
- 可选：`ram-role-arn`

### 支付宝配置

- 公共配置前缀：`aliyun.pay`
- 子配置前缀：`aliyun.pay.app`、`aliyun.pay.scan-code`、`aliyun.pay.fund`、`aliyun.pay.oauth`
- 子配置只要开启 `enable`，未配置的字段会自动回退到公共配置。

#### 公共配置

```yaml
aliyun:
  pay:
    enable: true
    app-id: your-common-app-id
    gate-way: https://openapi.alipay.com/gateway.do
    private-key: your-common-private-key
    public-key: your-common-public-key
    certificates: false
    app-cert-path: cert/appCertPublicKey.crt
    alipay-public-cert-path: cert/alipayCertPublicKey_RSA2.crt
    root-cert-path: cert/alipayRootCert.crt
    seller-id: your-common-seller-id
    validity-time: 1800000
```

- 必填：`enable`、`gate-way`、`private-key`
- 二选一：`public-key` 或者证书模式的三个证书路径

#### APP 支付

```yaml
aliyun:
  pay:
    enable: true
    gate-way: https://openapi.alipay.com/gateway.do
    private-key: your-common-private-key
    public-key: your-common-public-key

    app:
      enable: true
      app-id: your-app-specific-app-id
```

#### 扫码支付

```yaml
aliyun:
  pay:
    enable: true
    gate-way: https://openapi.alipay.com/gateway.do
    private-key: your-common-private-key
    public-key: your-common-public-key

    scan-code:
      enable: true
      app-id: your-scan-specific-app-id
      seller-id: your-seller-id
```

#### 资金转账

```yaml
aliyun:
  pay:
    enable: true
    gate-way: https://openapi.alipay.com/gateway.do
    private-key: your-common-private-key
    public-key: your-common-public-key

    fund:
      enable: true
      app-id: your-fund-specific-app-id
```

#### OAuth2 授权

```yaml
aliyun:
  pay:
    enable: true
    gate-way: https://openapi.alipay.com/gateway.do
    private-key: your-common-private-key
    public-key: your-common-public-key

    oauth:
      enable: true
      app-id: your-oauth-specific-app-id
```

## 使用示例

### OSS

```java
import com.aliyun.core.oss.AliyunOssService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class OssDemoController {

    private final AliyunOssService aliyunOssService;

    @PostMapping("/demo/upload")
    public Object upload(@RequestParam("file") MultipartFile file) throws Exception {
        return aliyunOssService.upload(file.getOriginalFilename(), file.getInputStream());
    }
}
```

### OAuth2

```java
import com.aliyun.core.pay.AliPayOAuth2Service;
import com.aliyun.model.AliPaySystemOauthDetails;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlipayOAuthDemoController {

    private final AliPayOAuth2Service aliPayOAuth2Service;

    @GetMapping("/demo/alipay/oauth/token")
    public AliPaySystemOauthDetails token(@RequestParam String code) {
        return aliPayOAuth2Service.querySystemOAuthTokenByAuthorizationCode(code);
    }

    @GetMapping("/demo/alipay/oauth/user")
    public AlipayUserInfoShareResponse user(@RequestParam String accessToken) {
        return aliPayOAuth2Service.queryUserInfoShare(accessToken);
    }
}
```

## 构建

```bash
mvn clean package
```

- 要求：编译成功并产生 `target/aliyun-spring-boot-starter-1.0.0.jar`

## 注意事项

- 不要提交真实的 `accessKeyId`、`accessKeySecret`、私钥和证书文件
- OSS 回调地址要能被业务系统公网访问
- 支付宝证书路径建议由业务项目外部配置
