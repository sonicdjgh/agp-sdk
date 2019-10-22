# EGLS-Android-Game-SDK-CN-4.X.X(Client-zhCN)
### 1. 简介
欢迎使用 EGLS Android Game SDK，这篇SDK对接文档说明适用于在**中国大陆**发行的游戏。<br/><br/>
从4.x.x版本起，我们采用了新的账号体系，所以并不兼容旧版（即同一个账号在登录后返回的uid与3.x.x版本的不一致）。如果您的游戏曾经接过旧版本的SDK，并且将要使用4.x.x版本的SDK时，请配合我们做游戏的强更及其他必要的更新操作（详情请咨询我方运营）。
### 2. 所需参数
#### 2.1 EGLS_APP_ID
由我方给游戏分配的应用id，一个游戏对应一个
#### 2.2 wx_app_id
在微信平台上分配的应用标识，用于微信登录、支付及分享
#### 2.3 wx_secret
在微信平台上分配的秘钥，用于微信登录、支付及分享
#### 2.4 qq_app_id
在QQ平台上分配的应用标识，用于QQ登录及分享
#### 2.5 alipay_app_id
在支付宝平台上分配的应用标识，用于支付宝支付
### 3. 环境搭建
#### 3.1 gradle版本及库引用地址设置
gradle版本为4.1，并且需要在当前Project根目录下的build.gralde文件中加上如下配置：
```gradle
buildscript {
    repositories {
    	google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.1'
    }
}

allprojects {
    repositories {
    	google()
        jcenter()
	mavenCentral()
	maven { url "https://dl.bintray.com/sonicdjgh/maven/" }
	
	// 如果使用新浪微博分享，请打开以下配置
        // maven { url "https://dl.bintray.com/thelasterstar/maven/" }
    }
}
```
另外，还需要在当前Project根目录下的gradle.properties文件中加上如下配置：
```gradle
EGLS_SDK_VERSION=4.7.2
android.enableAapt2=false
```
#### 3.2 lib 选择
针对于在中国大陆地区发行的游戏，请在Module“AGS”的“build.gradle”文件里打开如下图所示的配置：<br/>
```gradle
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    // base begin
    api "com.egls.android:platform:$EGLS_SDK_VERSION@aar"
    api "com.egls.android:support:$EGLS_SDK_VERSION@aar"
    api 'com.android.support.constraint:constraint-layout:1.1.0'
    // base 
    
    // wechat begin
    api files('libs/cn/wechat-sdk-android-with-mta.jar')
    // wechat end
    
    // qq begin
    api files('libs/cn/open_sdk_r5781_lite.jar')
    // qq end
   	
    // alipay begin
    api files('libs/cn/alipaySdk-20180316.jar')
    // alipay end

    // weibo begin
    // 如果使用新浪微博分享，请打开如下配置
    // api 'com.sina.weibo.sdk:core:4.3.4:openDefaultRelease@aar'
    // weibo end
}
```
#### 3.3 关于Unity的SDK接入
a. 首先使用Android Studio自建一个安卓项目工程后并完成SDK的接入工作；<br/><br/>
b. 请注意，游戏主Activity需要继承Unity的UnityPlayerActivity；<br/><br/>
c. Google推荐对危险权限的使用有一定要求，需要加入申请权限的逻辑。但由于Unity会自动申请“AndroidManifest.xml”文件中所配置的危险权限，不便于逻辑控制。如果有需要，请在“AndroidManifest.xml”文件中的“application”标签内加入如下配置：
```Xml
<meta-data
    android:name="unityplayer.SkipPermissionsDialog"
    android:value="true" />
```
#### 3.4 其他
minSdkVersion = 17，targetSdkVersion = 28
### 4. AndroidManifest.xml文件配置
#### 4.1 AndroidManifest.xml中的参数配置
```gradle
// 在游戏Module的“build.gradle”中的“defaultConfig”里添加如下配置：
manifestPlaceholders = [
                // base begin
                EGLS_APP_ID              : "",// 用于SDK初始化 
                EGLS_PUBLISHMENT_AREA    : "",// 用于SDK识别发行区，可详见文档附录
                EGLS_PAY_CHANNEL         : "",// 用于SDK识别支付方式，可详见文档附录
                EGLS_PAY_IS_SANDBOX      : "false",// 大陆发行区设为false即可
                // base end
		
		// other begin
		ALIPAY_APP_ID            : "",// 用于SDK支付
                WX_APP_ID                : "",// 用于微信登录、支付及分享，若无需求可不填
                WX_SECRET                : "",// 用于微信登录、支付及分享，若无需求可不填
                WB_APP_KEY               : "",// 用于新浪微博分享，若无需求可不填
                QQ_APP_ID                : "",// 用于QQ登录、分享，若无需求可不填
                // other end
        ]
```
#### 4.2 Permission 配置
```Xml
<!-- 支付宝 begin -->
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<!-- 支付宝 end -->
```
请注意：以上 Permission 配置中只打开了SDK基础功能相关的配置，如果使用到其他功能，请打开对应的 Permission 配置！
#### 4.3 Application相关配置
```Xml
<application
    android:name="com.egls.support.components.EglsApplication"
    android:allowBackup="false"
    android:icon="@drawable/icon"
    android:label="AGSDK Demo"
    android:networkSecurityConfig="@xml/network_security_config" >

    <!-- 游戏Activity -->
    <activity
        android:name="com.egls.demo.GameActivity"
        android:configChanges="fontScale|orientation|keyboardHidden|locale|navigation|screenSize|uiMode"
        android:screenOrientation="landscape"
        android:theme="@style/EglsTheme.AppCompat.Translucent.NoActionBar.Fullscreen.NoAnimation" >
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />

            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>
	
    <!-- Base begin -->
    <meta-data
        android:name="EGLS_APP_ID"
        android:value="${EGLS_APP_ID}" />
	
    <meta-data
        android:name="EGLS_PUBLISHMENT_AREA"
        android:value="${EGLS_PUBLISHMENT_AREA}" />
	
    <meta-data
        android:name="EGLS_PAY_CHANNEL"
        android:value="${EGLS_PAY_CHANNEL}" />
	
    <meta-data
        android:name="EGLS_PAY_IS_SANDBOX"
        android:value="${EGLS_PAY_IS_SANDBOX}" />
    <!-- Base end -->
	

    <!-- Wechat begin -->
    <!-- 需要在工程里建立一个名为“xxx.wxapi”的package，其中“xxx”为游戏的正式包名 -->	
    <activity
        android:name="xxx.wxapi.WXEntryActivity"
        android:exported="true"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.NoDisplay"/>
	
    <!-- 如果使用微信支付功能，请打开以下配置 -->
    <!-- 需要在工程里建立一个名为“xxx.wxapi”的package，其中“xxx”为游戏的正式包名 -->	
    <!--
    <activity
        android:name="xxx.wxapi.WXPayEntryActivity"
        android:exported="true"
        android:screenOrientation="behind"
        android:theme="@android:style/Theme.NoDisplay" />
    -->    
		
    <meta-data
        android:name="wx_app_id"
        android:value="${WX_APP_ID}" />
    
    <meta-data
        android:name="wx_secret"
        android:value="${WX_SECRET}" />
    <!-- Wechat end -->
		
	
    <!-- QQ begin -->
    <activity
        android:name="com.tencent.tauth.AuthActivity"
        android:launchMode="singleTask"
        android:noHistory="true">
        <intent-filter>
            <action android:name="android.intent.action.VIEW" />

            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />

            <data android:scheme="tencent${QQ_APP_ID}" />
        </intent-filter>
    </activity>
    <activity
        android:name="com.tencent.connect.common.AssistActivity"
        android:configChanges="orientation|keyboardHidden"
        android:screenOrientation="behind"
        android:theme="@android:style/Theme.Translucent.NoTitleBar"/>
		
    <meta-data
        android:name="qq_app_id"
        android:value="${QQ_APP_ID}" />
    <!-- QQ end -->
	
	
    <!-- Alipay begin -->
    <activity
        android:name="com.alipay.sdk.app.H5PayActivity"
        android:screenOrientation="portrait"/>
    
    <meta-data
        android:name="alipay_app_id"
        android:value="${ALIPAY_APP_ID}" />
    <!-- Alipay end -->
	
    
    <!-- Sina Weibo begin -->
    <!-- 如果使用微博分享功能，请打开以下配置 -->
    <!-- 替换“MY_WB_APP_KEY”字样为微博平台上分配的应用key -->
    <!--	
    <meta-data
        android:name="wb_app_key"
        android:value="${WB_APP_KEY}" />
    -->
    <!-- Sina Weibo end -->
</application>
```
### 5. 基础方法实现（必接）
```Java 
@Override
protected void onResume() {
    super.onResume();
    EglsPlatform.onResume();
}
    
@Override
protected void onPause() {
    super.onPause();
    EglsPlatform.onPause();
}
	
@Override
protected void onDestroy() {
    super.onDestroy();
    EglsPlatform.onDestory();
}
	
@Override
protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    EglsPlatform.onNewIntent(intent);
}
	
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    EglsPlatform.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
}
	
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    EglsPlatform.onActivityResult(requestCode, resultCode, data);
}
```
### 6. SDK初始化（必接）
```Java
// 如果游戏工程中有需要自定义Application的需求，那么请在自定义的Application类中，按照如下进行接口的调用：
@Override
public void onCreate() {
    super.onCreate();
    EglsPlatform.initApplication(this);
}
```
```Java
// 请在游戏的主Activity类中，按照如下进行接口的调用：
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EglsPlatform.initActivity(this, AppUtil.getVersionName(this), new SDKActionHandler() {

        @Override
        public void onHandleInit(int state, String message) {// SDK初始化的結果处理
            if (state == Constants.SDK_STATE_SUCCESS) {
                // 初始化成功后的处理
            } else {
                // 初始化失败后的处理
            }
        }

        @Override
        public void onHandleAgreement(boolean isAgree) {// SDK是否同意用户协议的結果处理
            if (isAgree) {
                // 玩家选择同意协议后的处理
            } else {
                // 玩家选择不同意协议后的处理
            }
        }

        @Override
        public void onHandleLogin(int state, String token, String uid, String accountType, String nickName) {// SDK登录的結果处理
            switch (state) {
                case Constants.SDK_STATE_SUCCESS:// 登录成功后的处理
                    // accountType = "0"时，表示游客账号登录
                    // accountType = "1"时，表示EGLS账号登录
                    // accountType = "4"时，表示微信账号登录
                    break;
                case Constants.SDK_STATE_CANCEL:// 登录取消后的处理
                    break;
                case Constants.SDK_STATE_ERROR:// 登录失败后的处理
                    break;
                }
            }

        @Override
        public void onHandleGuestBind(int state, String accountType, String nickName) {// SDK游客绑定的結果处理
            switch (state) {
                case Constants.SDK_STATE_SUCCESS:// 游客绑定成功后的处理
                    break;
                case Constants.SDK_STATE_CANCEL:// 游客绑定取消后的处理
                    break;
                case Constants.SDK_STATE_ERROR:// 游客绑定失败后的处理
                    break;
            }
        }

        @Override
        public void onHandleTokenFailure() {// SDK登录token失效的结果处理（需要游戏实现返回到登录页面的逻辑）

        }

        @Override
        public void onHandlePay(int state, String eglsOrderInfo) {// SDK支付的結果处理
            switch (state) {
                case Constants.SDK_STATE_SUCCESS:// 支付完成后的处理（仅表示客户端支付操作完成，最终要以服务器的通知为准）
                    break;
                case Constants.SDK_STATE_CANCEL:// 支付取消后的处理
                    break;
                case Constants.SDK_STATE_ERROR:// 支付失败后的处理
                    break;
            }
        }

        @Override
        public void onHandleShare(int state, int type, String message) {// SDK分享的結果处理
            switch (state) {
                case Constants.SDK_STATE_SUCCESS:// 分享成功后的处理
                    // 当type为Constants.TYPE_SHARE_WECHAT时，表示微信分享
		    // 当type为Constants.TYPE_SHARE_WEIBO时，表示微博分享
		    // 当type为Constants.TYPE_SHARE_QQ时，表示QQ分享
                    break;
                case Constants.SDK_STATE_CANCEL:// 分享失成功的处理
                    break;
                case Constants.SDK_STATE_ERROR:// 分享失败后的处理
                    break;
            }
        }

        @Override
        public void onHandleExit(boolean isExit) {// SDK退出游戏的結果处理（该方法只针对游戏调用SDK的"AGPManager.eglsExit()"接口的响应）
            if (isExit) {
                // 玩家选择退出后的处理
            } else {
                // 玩家选择继续游戏后的处理
            }
        }
    });
}
```
### 7. SDK登录（必接）
```Java
EglsPlatform.eglsLogin(Constants.MODE_LOGIN_COMMON);
```
### 8. SDK切换账号（选接）
```Java
EglsPlatform.eglsSwitch();
```
### 9. SDK支付（必接）
```Java
String amount = "1";// 总金额
String productId = "PDT001";// 档位id
String productName = "钻石";// 档位名称
String cpOrderInfo = "2SDF34DF12GH0S23234GAER5";// CP订单号
String flag = "";// 额外标记，一般传空字符串即可
EglsPlatform.eglsPay(amount, productId, productName, cpOrderInfo, flag);
```
### 10. onEnterGame接口调用（必接）
```Java
//当玩家登录进入到游戏服务器之后，请务必调用该方法
EglsPlatform.onEnterGame();
```
### 11. SDK分享功能（选接）
```Java
int type = Constants.TYPE_SHARE_WECHAT;
String shareTitle = "";// 分享标题
String shareText = "";// 分享文本
String shareImageFilePath = "";// 分享图片（绝对路径）
String shareLink = "";// 分享链接
boolean isTimelineCb = false;// 仅当用于微信分享，当isTimelineCb为true时，SDK启用微信分享到朋友圈，否则启用微信分享到好友
EglsPlatform.eglsShare(this, type, shareTitle, shareText, shareImageFilePath, shareLink, isTimelineCb);
```
### 12. 关于微信功能的使用
SDK集成了“微信登录”功能及“微信分享”功能，除了添加相关的AndroidManifest.xml文件配置之外，还需要在项目工程中添加一个以“正式包名.wxapi”的package（以Demo为例，则添加的package为“com.egls.demo.wxapi”），并且在该package中添加一个名为“WXEntryActivity”的Activity类，这个类必须继承SDK中的“com.egls.socialization.wechat.WeChatEntryActivity”类，例如：
```java
package 正式包名.wxapi;

import com.egls.socialization.wechat.WeChatEntryActivity;

public class WXEntryActivity extends WeChatEntryActivity {

}
```
### 13. 关于H5游戏的SDK接入
从4.5.31版本起，我们为SDK添加了“H5”游戏模式，即在调用SDK初始化接口时，“isWebMode”参数值设置为true。初始化完成后，SDK会自动向SDK平台请求并获取H5游戏启动页面的网址并执行网页跳转。其实，本质上与原生接入方式无太大差异，只是需要一个H5与Android原生交互的过程。Demo中新添了一个“MainH5Activity”类，里面包含了Android原生部分的对接示例；另外在“assets/web”目录下添加了一个"demo.html"文件，里面包含了H5部分的对接示例。
### 14. 其他注意事项
1. 凡是游戏项目工程为Android Studio工程，并且在Gradle里配置了productFlavor来控制打包流程的，请务必在调用“AGPManager.initSDK()”接口前，写上如下逻辑代码：
```Java
EglsPlatform.addFlavorsBasePackage(BuildConfig.class.getPackage().getName());
```
2. SDK会主动申请“android.permission.WRITE_EXTERNAL_STORAGE”权限，但如果游戏还另需申请其他的“危险权限”，可以在调用“AGPManager.initSDK()”接口前，使用“addNecessaryPermission()”接口。例如：
```Java
EglsPlatform.addNecessaryPermission(Manifest.permission.READ_PHONE_STATE);
EglsPlatform.addNecessaryPermission(Manifest.permission.RECORD_AUDIO);
```
### 附表 - publishmentArea
publishmentArea | value
---|---
中国大陆 | 1
港奥台地区 | 2
韩国 | 3
日本 | 4
美国 | 5
俄罗斯 | 6
泰国 | 7
越南 | 8
印度尼西亚 | 9
新加坡 | 10

### 附表 - payChannel
payChannel | value
---|---
EGLS | 1
Google Play | 2
Mycard | 3
OneStore | 4
Gash | 5
