# EGLS-Android-Game-SDK-CN-4.X.X(Client-zhCN)
### 1. 简介
欢迎使用 EGLS Android Game SDK，这篇SDK对接文档说明适用于在**中国大陆**发行的游戏。<br/><br/>
从4.x.x版本起，我们采用了新的账号体系，所以并不兼容旧版（即同一个账号在登录后返回的uid与3.x.x版本的不一致）。如果您的游戏曾经接过旧版本的SDK，并且将要使用4.x.x版本的SDK时，请配合我们做游戏的强更及其他必要的更新操作（详情请咨询我方运营）。
### 2. 所需参数
#### 2.1 EGLS_APP_ID
由我方给游戏分配的应用id，一个游戏对应一个
#### 2.2 wx_app_id
在微信平台上分配的应用标识，用于微信登录及分享
#### 2.3 wx_secret
在微信平台上分配的秘钥，用于获取微信用户昵称
#### 2.4 qq_app_id
在QQ平台上分配的应用标识，用于QQ登录及分享
#### 2.5 alipay_app_id
在支付宝平台上分配的应用标识，用于支付宝支付
### 3. 环境搭建
#### 3.1 gradle版本及设置
gradle版本为4.1，并且需要在当前Project根目录下的build.gralde文件中加上如下配置：
```gradle
buildscript {
    repositories {
        jcenter()
        google()
    }
}

allprojects {
    repositories {
        jcenter()
        google()
    }
}
```
另外，还需要在当前Project根目录下的gradle.properties文件中加上如下配置：
```gradle
EGLS_AGP_VERSION=4.3.50
EGLS_AGS_VERSION=4.3.50
EGLS_SUPPORT_VERSION=4.3.50
android.enableAapt2=false
```
#### 3.2 依赖关系
![image](https://github.com/sonicdjgh/egls-android-game-sdk-release-studio/blob/master/res/S4000.png)<br/>
如上图所示：假设Demo为SDK对接完毕的安卓游戏工程，那么Demo引入Module“AGP”，则需要在Demo中的“build.gradle”里添加如下配置：
```gradle
android {
    compileSdkVersion 27
    buildToolsVersion "27.0.3"
}

repositories {
    flatDir {
    	dirs project(':AGP').file('libs')
        dirs project(':AGS').file('libs')
    }
}

dependencies {
    implementation project(':AGP')
}
```
#### 3.3 AGP lib 选择
针对于在中国大陆地区发行的游戏，请在Module“AGP”的“build.gradle”文件里打开如下图所示的配置：<br/>
```gradle
repositories {
    flatDir {
        dirs 'libs'
        dirs project(':AGS').file('libs')
    }
}

dependencies {
    // base begin
    api "com.egls.android:egls-agp-sdk:$EGLS_AGP_VERSION@aar"
    api project(':AGS')
    // base end
}
```
#### 3.4 AGS lib 选择
针对于在中国大陆地区发行的游戏，请在Module“AGS”的“build.gradle”文件里打开如下图所示的配置：<br/>
```gradle
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    // base begin
    api "com.egls.android:egls-ags-sdk:$EGLS_AGS_VERSION@aar"
    api "com.egls.android:egls-android-support:$EGLS_SUPPORT_VERSION@aar"
    api 'com.android.support.constraint:constraint-layout:1.0.2'
    // base end

    // cn begin
    api files('libs/cn/alipaySdk-20180316.jar')
    api files('libs/cn/open_sdk_r5781_lite.jar')
    api files('libs/cn/wechat-sdk-android-with-mta.jar')
    api files('libs/cn/openDefault-1.0.0-openDefaultRelease.jar')
    // cn end
}
```
#### 3.5 关于Unity的SDK接入
a. 首先使用Android Studio自建一个安卓项目工程后并完成SDK的接入工作；<br/><br/>
b. 请注意，游戏主Activity需要继承Unity的UnityPlayerActivity；<br/><br/>
c. Google推荐对危险权限的使用有一定要求，需要加入申请权限的逻辑。但由于Unity会自动申请“AndroidManifest.xml”文件中所配置的危险权限，不便于逻辑控制。如果有需要，请在“AndroidManifest.xml”文件中的“application”标签内加入如下配置：
```Xml
<meta-data
    android:name="unityplayer.SkipPermissionsDialog"
    android:value="true" />
```
#### 3.6 其他
minSdkVersion = 16，targetSdkVersion >= 26
### 4. AndroidManifest.xml文件配置
#### 4.1 AGP Permission 配置
```Xml
<!-- AGP begin -->
<!-- 暂没有可添加的配置 -->
<!-- AGP end -->
```
#### 4.2 AGS Permission 配置
```Xml
<!-- AGS begin -->
<!-- 支付宝 begin -->
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<!-- 支付宝 end -->
<!-- AGS end -->
```
请注意：以上 Permission 配置中只打开了SDK基础功能相关的配置，如果使用到其他功能，请打开对应的 Permission 配置！
#### 4.3 Application相关配置
```Xml
<application
    android:allowBackup="false"
    android:icon="@drawable/icon"
    android:label="AGSDK Demo"
    android:theme="@style/AppTheme" >
    <activity
        android:name="com.egls.demo.GameActivity"
        android:configChanges="fontScale|orientation|keyboardHidden|locale|navigation|screenSize|uiMode"
        android:screenOrientation="landscape"
        android:theme="@android:style/Theme.NoTitleBar.Fullscreen" >
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />

            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
	<!-- 微博 begin -->
	<!-- 如果使用微博分享功能，请打开以下配置 -->
	<!-- 
        <intent-filter>
            <action android:name="com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY" />

            <category android:name="android.intent.category.DEFAULT" />
        </intent-filter>
        -->
        <!-- 微博 end -->
    </activity>
	
    <!-- Base begin -->
    <!-- 替换"MY_APP_ID"字样为SDK初始化所需的eglsAppId -->
    <meta-data
        android:name="EGLS_APP_ID"
        android:value="\0MY_APP_ID" />
	
    <!-- 替换"MY_SERVER_TYPE"字样为对应的服务类别码，详见“附表 - serverType” -->
    <meta-data
        android:name="EGLS_SERVER_TYPE"
        android:value="MY_SERVER_TYPE" />
	
    <!-- 替换"MY_PAY_CHANNEL"字样为对应的支付渠道码，详见“附表 - payChannel” -->
    <meta-data
        android:name="EGLS_PAY_CHANNEL"
        android:value="MY_PAY_CHANNEL" />
	
    <!-- 当没有特殊要求时，“EGLS_PAY_IS_SANDBOX”的参数值为"false"即可 -->
    <meta-data
        android:name="EGLS_PAY_IS_SANDBOX"
        android:value="false" />
	
    <!-- 当没有特殊要求时，“EGLS_PAY_OTHER_PARAM”的参数值为""即可 -->
    <meta-data
        android:name="EGLS_PAY_OTHER_PARAM"
        android:value="" />
    <!-- Base end -->
	

    <!-- AGS begin -->
    <!-- 微信 begin -->
    <!-- 如果使用微信登录或微信分享功能，请打开以下配置 -->
    <!-- 替换“MY_PACKAGE_NAME”字样为游戏的正式包名 -->	
    <!--	
    <activity
        android:name="MY_PACKAGE_NAME.wxapi.WXEntryActivity"
        android:exported="true"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.NoDisplay"/>
    -->
    
    <!-- 如果使用微信登录或微信分享功能，请打开以下配置 -->	
    <!-- 替换“MY_WX_APP_ID”字样为微信平台上分配的应用标识 -->
    <!--	
    <meta-data
        android:name="wx_app_id"
        android:value="MY_WX_APP_ID" />
    -->
    
    <!-- 如果使用微信登录或微信分享功能，请打开以下配置 -->	
    <!-- 替换“MY_WX_SECRET”字样为微信平台上分配的秘钥 -->
    <!--	
    <meta-data
        android:name="wx_secret"
        android:value="MY_WX_SECRET" />
    -->
    <!-- 微信 end -->
	
    
    <!-- 微博 begin -->
    <!-- 如果使用微博分享功能，请打开以下配置 -->
    <!-- 替换“MY_WB_APP_KEY”字样为微博平台上分配的应用key -->
    <!--	
    <meta-data
        android:name="wb_app_key"
        android:value="\0MY_WB_APP_KEY" />
    -->
    <!-- 微博 end -->
	
	
    <!-- QQ begin -->
    <!-- 如果使用QQ登录或微信分享功能，请打开以下配置 -->	
    <!-- 替换“MY_QQ_APP_ID”字样为QQ平台上分配的应用标识 -->
    <!--
    <activity
        android:name="com.tencent.tauth.AuthActivity"
        android:launchMode="singleTask"
        android:noHistory="true">
        <intent-filter>
            <action android:name="android.intent.action.VIEW" />

            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />

            <data android:scheme="tencentMY_QQ_APP_ID" />
        </intent-filter>
    </activity>
    <activity
        android:name="com.tencent.connect.common.AssistActivity"
        android:configChanges="orientation|keyboardHidden"
        android:screenOrientation="behind"
        android:theme="@android:style/Theme.Translucent.NoTitleBar"/>
    -->
	
    <!-- 如果使用QQ登录或微信分享功能，请打开以下配置 -->		
    <!-- 替换“MY_QQ_APP_ID”字样为QQ平台上分配的应用标识 -->
    <!--	
    <meta-data
        android:name="qq_app_id"
        android:value="\0MY_QQ_APP_ID" />
    -->
    <!-- QQ end -->
	
	
    <!-- 支付宝 begin -->
    <activity
        android:name="com.alipay.sdk.app.H5PayActivity"
        android:screenOrientation="portrait"/>
    
    <!-- 替换“MY_ALIPAY_APP_ID”字样为支付宝平台上分配的应用标识 -->
    <meta-data
        android:name="alipay_app_id"
        android:value="MY_ALIPAY_APP_ID" />
    <!-- 支付宝 end -->
    <!-- AGS end -->
</application>
```
### 5. 基础方法实现（必接）
```Java 
@Override
protected void onResume() {
    super.onResume();
    AGPManager.onResume();
}
    
@Override
protected void onPause() {
    super.onPause();
    AGPManager.onPause();
}
	
@Override
protected void onDestroy() {
    super.onDestroy();
    AGPManager.onDestory();
}
	
@Override
protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    AGPManager.onNewIntent(intent);
}
	
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    AGPManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
}
	
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    AGPManager.onActivityResult(requestCode, resultCode, data);
}
```
### 6. SDK初始化（必接）
```Java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AGPManager.initSDK(this, AppUtil.getVersionName(this) + "", new AGPInitProcessListener() {// SDK初始化回调

        @Override
        public void onInitSDK(int code, String msg) {
            if (code == 0) {// 当SDK初始化成功后再做后续的事情

            }
        }
    });
}
```
### 7. SDK登录（必接）
```Java
boolean isOpenAutoLogin = false;
AGPManager.eglsLogin(isOpenAutoLogin, new AGPLoginProcessListener() {

    @Override
    public void onTokenFailure() {
	// token失效回调，这里需实现游戏返回到登录页面的逻辑
    }

    @Override
    public void onLoginProcess(int code, String token, String uid, String msg) {
	// 登录结果回调，只有当code=0时，示为登录成功
	// msg = "0"时，表示游客账号登录
	// msg = "1"时，表示EGLS账号登录
	// msg = "4"时，表示微信账号登录
    }

    @Override
    public void onLoginCancel() {
	// 登录取消回调
    }
});
```
### 8. SDK支付（必接）
```Java
String amount = "1";// 总金额
String productId = "PDT001";// 档位id
String productName = "钻石";// 档位名称
String cpOrderInfo = "2SDF34DF12GH0S23234GAER5";// CP订单号
AGPManager.eglsPay(amount, productId, productName, cpOrderInfo, new AGPClientPayProcessListener(){
	
    @Override
    public void onClientPayFinish(String message) {
	// 客户端支付完成回调
    }

    @Override
    public void onClientPayError() {
    	// 客户端支付错误回调
    }

    @Override
    public void onClientPayCancel() {
	// 客户端支付取消回调
    }
});
```
### 9. onEnterGame接口调用（必接）
```Java
//当玩家登录进入到游戏服务器之后，请务必调用该方法
AGPManager.onEnterGame();
```
### 10. SDK分享功能（选接）
```Java
改版中……
```
### 11. 关于微信功能的使用
SDK集成了“微信登录”功能及“微信分享”功能，除了添加相关的AndroidManifest.xml文件配置之外，还需要在项目工程中添加一个以“正式包名.wxapi”的package（以Demo为例，则添加的package为“com.egls.demo.wxapi”），并且在该package中添加一个名为“WXEntryActivity”的Activity类，这个类必须继承SDK中的“com.egls.socialization.wechat.WeChatEntryActivity”类，例如：
```java
package 正式包名.wxapi;

import com.egls.socialization.wechat.WeChatEntryActivity;

public class WXEntryActivity extends WeChatEntryActivity {

}
```
### 12. 关于微博功能的使用
SDK集成了“微博分享”功能，除了添加相关的AndroidManifest.xml文件配置之外，还需要在项目工程中的assets目录下添加一个有关微博的网页授权认证文件（相关问题可咨询我方运营）。
### 13. 其他注意事项
1. 凡是游戏项目工程为Android Studio工程，并且在Gradle里配置了productFlavor来控制打包流程的，请务必在调用“AGPManager.initSDK()”接口前，写上如下逻辑代码：
```Java
AGPManager.addFlavorsBasePackage(BuildConfig.class.getPackage().getName());
```
2. SDK会主动申请“android.permission.WRITE_EXTERNAL_STORAGE”权限，但如果游戏还另需申请其他的“危险权限”，可以在调用“AGPManager.initSDK()”接口前，使用“addNecessaryPermission()”接口。例如：
```Java
AGPManager.addNecessaryPermission(Manifest.permission.READ_PHONE_STATE);
AGPManager.addNecessaryPermission(Manifest.permission.RECORD_AUDIO);
```
### 附表 - serverType
serverType | value
---|---
中国大陆 | 1
港台地区 | 2
韩国 | 3
日本 | 4
美国 | 5

### 附表 - payChannel
payChannel | value
---|---
Google Play | 2
Mycard | 3
OneStore | 4
Gash | 5
