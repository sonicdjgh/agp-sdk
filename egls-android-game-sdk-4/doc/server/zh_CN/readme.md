## EGLS Game SDK 服务端接入文档 V1.0.1

### 一、文档说明
本文档包含两个服务器端需要对接的接口，一是token验证接口；另外一个是支付信息回调接口。

#### 1. token验证接口
用户在客户端调用SDK登录时，需增加服务器端验证token是否失效；每次登录时，服务器端必须调用该接口进行服务器端token验证，该接口是CP向EGLS SDK服务端发起请求。

#### 2. 支付信息回调接口
在客户端支付完成后，CP方需提供一个接口来接收EGLS SDK服务器端发起的请求， 用于同步用户的支付信息。
<br /><br />

### 二、 交互协议

#### 1. 编码格式
使用UTF-8格式编码。

#### 2. 接口说明

##### 2.1 token验证接口（GET）

##### 2.1.1 接口地址
地区 | 地址
---|---
中国大陆 | http://cnpassport.eglsgame.com/passport/egls/tokenVerify
港澳台地区 | http://twpassport.eglsgame.com/passport/egls/tokenVerify
韩国 | http://krpassport.eglsgame.com/passport/egls/tokenVerify
美国 | http://uspassport.eglsgame.com/passport/egls/tokenVerify

##### 2.1.2 请求参数及实例
参数名称 | 参数说明 | 必要
---|---|---
uid | EGLS用户ID,由32位字符组成  | 是
ticket | 用户会话验证票 | 是
appId | 商户Id，由EGLS分配 | 	是
sign | 大写化的签名串，MD5(appId+uid+ticket+密钥) | 是

**示例**：http://twpassport.eglsgame.com/passport/egls/tokenVerify?uid=7cc1d5bb4e9844eab0e31f9021&ticket=abcdefggg&appId=1&sign=E866C08C984405C3DBD39ECAE1ED5224

##### 2.1.3 响应参数
{“code”:0,“message”:”响应信息”}

##### 2.2 支付信息回调接口(POST，由cp方提供)

##### 2.2.1 接口地址
接口地址由CP方提供。

##### 2.2.2 请求参数及实例
参数名称 | 参数说明 | 必传
---|---|---
appId | 商户id | 是
cpOrder | CP自定义数据（建议值为CP订单号）,调用SDK的支付接口时由CP传入。注：不可含有“&#124;”，“=”，“@” | 是
money | 充值金额,浮点值 | 是
payTime | 支付时间，毫秒值 | 是
expiryTime | 订阅过期时间，毫秒值 | 否 
order | EGLS订单号,由30位字符组成 | 是
currency | 货币种类 | 是
sandbox | 是否为测试环境, true或者false | 是
sign | 大写化的签名数据：对所有不为空的参数按参数名升序排列(除sign外)，通过”&”连接各参数值，拼装成签名串，并签名串末尾追加appSecret（由EGLS分配）。示例：MD5(value1&value2…appSecret) | 是

请求示例：http://xxx.xx.xx/notify?appId=000&cpOrder=xxxxxxxxxxxxx&money=1.0&payTime=1486530505000&order=5E3DC6F52063A2DD51057B870206E6&currency=RMB&sandbox=false&sign=E866C08C984405C3DBD39ECAE1ED5224

签名源串示例（假设密钥为AAAAAA）：
000&xxxxxxxxxxxxx&RMB&1.0&5E3DC6F52063A2DD51057B870206E6&1486530505000&falseAAAAAA

##### 2.2.3 响应参数（CP方需要返回的数据）
成功收到充值通知后需返回字符串success, 其他认为失败。

**注意**：如收到通知不返回success, SDK平台会重复发送订单。
<br /><br />

### 三、 帮助
如遇到在测试支付时，弹出提示“支付数据错误”，那么最普遍的原因如下：<br /><br />
1. 定价表未配置；<br /><br />
2. cpOrder数据异常(例如：含有“|”，“=”，“@”等特殊字符)。

### 附录

token验证接口code对应错误码信息：

code | message
---|---
1 | 验签失败
2 | Session过期
3 | Session无效
99 | 异常，请检查appId是否为EGLS提供的商户ID
