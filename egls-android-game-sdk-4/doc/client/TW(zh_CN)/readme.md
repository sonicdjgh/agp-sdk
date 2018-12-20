# EGLS-Android-Game-SDK-TW-4.X.X(Client-zhCN)
### 1. 简介
欢迎使用 EGLS Android Game SDK，这篇SDK对接文档说明适用于在**港台**地区发行的游戏。<br/><br/>
从4.x.x版本起，我们采用了新的账号体系，所以并不兼容旧版（即同一个账号在登录后返回的uid与3.x.x版本的不一致）。如果您的游戏曾经接过旧版本的SDK，并且将要使用4.x.x版本的SDK时，请配合我们做游戏的强更及其他必要的更新操作（详情请咨询我方运营）。
### 2. 所需参数
#### 2.1 eglsAppId
由我方给游戏分配的应用id，一个游戏对应一个。
#### 2.2 CHANNEL_GOOGLE_PUBLIC_KEY
在Goole Play后台生成的支付公钥。
#### 2.3 CHANNEL_SERVER_CLIENT_ID
在Google API后台“OAuth 2.0 客户端 ID”配置的列表中，关于“Web Client”项对应的“Client ID”参数值。
#### 2.4 com.facebook.sdk.ApplicationId
在Facebook后台生成的应用id。
### 3. 环境搭建
#### 3.1 gradle版本及设置
gradle版本为4.1，并且需要在当前Project根目录下的build.gralde文件中加上如下配置：
```gradle
buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.1'
	
        // 如果使用Firebase云消息推送功能，请打开以下配置
    	// classpath 'com.google.gms:google-services:3.0.0'
    }
}

allprojects {
    repositories {
        jcenter()
        google()
	mavenCentral()
    }
}
```
如果使用Firebase云消息推送功能，请在当前游戏Module工程目录下的build.gradle文件中加上如下配置：
```gradle
apply plugin: 'com.google.gms.google-services'
```
另外，还需要在当前Project根目录下的gradle.properties文件中加上如下配置：
```gradle
EGLS_AGP_VERSION=4.4.1
EGLS_AGS_VERSION=4.4.1
EGLS_SUPPORT_VERSION=4.4.1
android.enableAapt2=false
```
#### 3.2 依赖关系
![image](https://github.com/sonicdjgh/egls-android-game-sdk-release-studio/blob/master/res/tw/S4TW000.png)<br/>
如上图所示：假设Demo为SDK对接完毕的安卓游戏工程，那么Demo引入Module“AGP”，则需要在Demo中的“build.gradle”里添加如下配置：
```gradle
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
针对于在港台地区发行的游戏，请在Module“AGP”的“build.gradle”文件里打开如下图所示的配置：<br/>
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


    // tw begin
    api 'com.appsflyer:af-android-sdk:4+@aar'
    api 'com.android.installreferrer:installreferrer:1.0'
    // tw end
}
```
#### 3.4 AGS lib 选择
针对于在港台地区发行的游戏，请在Module“AGS”的“build.gradle”文件里打开如下图所示的配置：<br/>
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
    api 'com.android.support.constraint:constraint-layout:1.1.0'
    // base end

    // tw begin
    api 'com.google.android.gms:play-services-auth:11.0.1'
    api 'com.google.android.gms:play-services-auth-base:11.0.1'
    api 'com.google.android.gms:play-services-base:11.0.1'
    api 'com.google.android.gms:play-services-basement:11.0.1'
    api 'com.google.android.gms:play-services-drive:11.0.1'
    api 'com.google.android.gms:play-services-games:11.0.1'
    api 'com.google.android.gms:play-services-gcm:11.0.1'
    api 'com.google.android.gms:play-services-iid:11.0.1'
    api 'com.google.android.gms:play-services-tasks:11.0.1'
    
    // 如果使用 Firebase 云消息推送，请打下面的配置
    // api 'com.google.firebase:firebase-analytics:11.0.1'
    // api 'com.google.firebase:firebase-analytics-impl:11.0.1'
    // api 'com.google.firebase:firebase-common:11.0.1'
    // api 'com.google.firebase:firebase-core:11.0.1'
    // api 'com.google.firebase:firebase-iid:11.0.1'
    // api 'com.google.firebase:firebase-messaging:11.0.1'
    
    api 'com.facebook.android:facebook-core:4.+'
    api 'com.facebook.android:facebook-login:4.+'
    api 'com.facebook.android:facebook-share:4.+'
    
    // 如果使用 MyCard 支付，请打开下面的配置https://github.com/sonicdjgh/egls-android-game-sdk-release-studio/blob/master/egls-android-game-sdk-4/doc/client/TW(zh_CN)/readme.md
    // api files('libs/tw/MyCardPaySDK.jar')
    
    // 如果使用 Gash 支付，请打开下面的配置
    // api files('libs/tw/clientsdk_product_v2.jar')
    // tw end
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
<!-- AppsFlyer begin -->
<!-- 如果现在接入的安卓包是针对除Google Play以外的其他应用商店，那么此权限一定需要声明，否则要删除该权限声明 -->
<!-- <uses-permission android:name="android.permission.READ_PHONE_STATE" /> -->
<!-- AppsFlyer end -->
<!-- AGP end -->
```
#### 4.2 AGS Permission 配置
```Xml
<!-- AGS begin -->
<!-- Google Play begin -->
<!-- 如果使用Google Play支付功能，请打开以下配置 -->
<!--
<uses-permission android:name="com.android.vending.BILLING" />
<uses-feature
    android:name="android.hardware.camera"
    android:required="false" />
<uses-feature
    android:name="android.hardware.camera.autofocus"
    android:required="false" />
<uses-feature
    android:name="android.hardware.telephony"
    android:required="false" />
<uses-feature
    android:name="android.hardware.microphone"
    android:required="false" />
-->
<!-- Google Play end -->


<!-- Mycard begin -->
<!-- 如果使用Mycard支付，请打开以下配置 -->
<!--
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.FLASHLIGHT" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
<uses-permission android:name="android.permission.READ_LOGS" />
<uses-feature android:name="android.hardware.camera" />
<uses-feature android:name="android.hardware.camera.autofocus" />
-->
<!-- Mycard end -->
<!-- AGS end -->
```
请注意：以上 Permission 配置中只打开了SDK基础功能相关的配置，如果使用到其他功能，请打开对应的 Permission 配置！
#### 4.3 Application相关配置
```Xml
<!-- 如果用Mycard支付功能，请在application标签内添加属性 android:name="tw.com.mycard.sdk.libs.PSDKApplication" -->
<application
    android:allowBackup="false"
    android:icon="@drawable/icon"
    android:label="AGSDK Demo"
    android:theme="@style/AppTheme" >
    <activity
        android:name="com.egls.sdk.demo.MainActivity"
        android:configChanges="fontScale|orientation|keyboardHidden|locale|navigation|screenSize|uiMode"
        android:screenOrientation="landscape"
        android:theme="@android:style/Theme.NoTitleBar.Fullscreen" >
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />

            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
        <!-- DeepLink begin -->
        <!-- DeepLink配置为台湾地区LINE积分墙功能所使用 -->
        <!-- 替换“MY_PACKAGE_NAME”字样为正式包名 -->
        <intent-filter>
            <data
                android:host="MY_PACKAGE_NAME"
                android:scheme="egls" />

            <action android:name="android.intent.action.VIEW" />

            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
        </intent-filter>
        <!-- DeepLink end -->
    </activity>
	
    <!-- Base begin -->
    <!-- 替换"MY_APP_ID"字样为SDK初始化所需的eglsAppId -->
    <meta-data
        android:name="EGLS_APP_ID"
        android:value="\0MY_APP_ID" />
	
    <!-- 替换"MY_PUBLISHMENT_AREA"字样为对应的发行区标识码，详见“附表 - publishmentArea” -->
    <meta-data
        android:name="EGLS_PUBLISHMENT_AREA"
        android:value="MY_PUBLISHMENT_AREA" />
	
    <!-- 替换"MY_PAY_CHANNEL"字样为对应的支付渠道标识码，详见“附表 - payChannel” -->
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

    <!-- 替换“MY_SERVER_CLIENT_ID”字样为在Google API后台“OAuth 2.0 客户端 ID”配置的列表中，关于“Web Client”项对应的“Client ID”参数值 -->
    <meta-data
        android:name="CHANNEL_SERVER_CLIENT_ID"
        android:value="MY_SERVER_CLIENT_ID"/>

    <!-- 替换“MY_APPLICATION_ID”字样为Facebook后台配置的applicationId -->
    <meta-data
        android:name="com.facebook.sdk.ApplicationId"
        android:value="\0MY_APPLICATION_ID" />
    <!-- Base end -->
        
    
    <!-- AGP begin -->
    <!-- AppsFlyer begin -->
    <!-- AppsFlyer为港台地区所使用的内嵌统计功能 -->
    <!-- 为了确保所有Install Referrer监听器可以成功监听由系统播放的referrer参数，请一定在AndroidManifest.xml中将AppsFlyer的监听器置于所有同类监听器第一位，并保证receiver tag在application tag中 -->
    <!-- 如果已经有其他的receiver来监听“INSTALL_REFERRER”， 那么请用“MultipleInstallBroadcastReceiver” -->
    <receiver
        android:name="com.appsflyer.SingleInstallBroadcastReceiver"
        android:exported="true" >
        <intent-filter>
            <action android:name="com.android.vending.INSTALL_REFERRER" />
        </intent-filter>
    </receiver>
    
    <!-- 如果有特殊需求修改devkey时，请打开以下配置 -->	
    <!-- 替换“MY_AF_DEV_KEY”字样为AppsFlyer后台配置的devkey -->
    <!--	
    <meta-data
        android:name="CHANNEL_AF_DEV_KEY"
        android:value="MY_AF_DEV_KEY" />
    -->
    <!-- AppsFlyer end -->
    <!-- AGP end -->


    <!-- AGS begin -->
    <!-- 如果使用Firebase云消息推送，请打开以下配置 -->
    <!-- Firebase begin -->
    <!--
    <service android:name="com.egls.socialization.backend.AGSPushMessageService">
        <intent-filter>
            <action android:name="com.google.firebase.MESSAGING_EVENT" />
        </intent-filter>
    </service>
    <service android:name="com.egls.socialization.backend.AGSPushTokenService">
        <intent-filter>
            <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />
        </intent-filter>
    </service>
    -->
    <!-- Firebase云消息推送所使用的icon图案 -->
    <!-- 如果使用Firebase云消息推送，请打开以下配置 -->
    <!--
    <meta-data
        android:name="com.google.firebase.messaging.default_notification_icon"
        android:resource="@drawable/egls_push_icon" />
    -->	
    <!-- Firebase云消息推送所使用的icon底色 -->
    <!-- 如果使用Firebase云消息推送，请打开以下配置 -->
    <!--
    <meta-data
        android:name="com.google.firebase.messaging.default_notification_color"
        android:resource="@color/support_egls" />
    -->
    <!-- Firebase end -->
	
	
    <!-- Google Play Game begin -->
    <!-- 如果使用Google Play Game成就功能，请打开以下配置 -->
    <!-- 替换“MY_GAMES_APP_ID”字样为"MY_SERVER_CLIENT_ID"的第一处"-"左边的纯数字部分 -->
    <!--
    <meta-data
        android:name="com.google.android.gms.games.APP_ID"
        android:value="\0MY_GAMES_APP_ID" />
    -->
    <!-- Google Play Game end -->


    <!-- Google Play begin -->
    <!-- 如果使用Google Play支付功能，请打开以下配置 -->
    <!-- 替换“MY_PUBLIC_KEY”字样为Google Play后台配置的publicKey -->
    <!-- 4.1.0版本以前name属性为“com.egls.socialization.google.play.BillingActivity” -->
    <!--
    <activity
        android:name="com.egls.socialization.google.play.GooglePlayActivity"
        android:configChanges="fontScale|orientation|keyboardHidden|locale|navigation|screenSize|uiMode"
        android:screenOrientation="behind"
        android:theme="@style/EglsTheme.Translucent.NoTitleBar.Fullscreen.NoAnimation" />

    <meta-data
        android:name="CHANNEL_GOOGLE_PUBLIC_KEY"
        android:value="MY_PUBLIC_KEY" />
    -->
    <!-- Google Play end -->
    

    <!-- Facebook begin -->
    <!-- 替换“MY_APPLICATION_ID”字样为Facebook后台配置的applicationId -->
    <provider
        android:name="com.facebook.FacebookContentProvider"
        android:authorities="com.facebook.app.FacebookContentProviderMY_APPLICATION_ID"
        android:exported="true" />
	
    <!-- 港澳台发行必须开启Facebook的“email”权限 --> 	
    <meta-data
        android:name="CNANNEL_PERMISSION_EMAIL"
        android:value="true" />
	
    <!--如果游戏需要开启Facebook的“USER_FRIEND”权限，请打开以下配置 --> 
    <!--
    <meta-data
        android:name="CNANNEL_PERMISSION_USER_FRIEND"
        android:value="true"/>
    -->
    <!-- Facebook end  -->


    <!-- Mycard begin -->
    <!-- 如果使用Mycard支付功能，请打开以下配置 -->
    <!--
    <activity
        android:name="soft_world.mycard.paymentapp.ui.SplashActivity"
        android:screenOrientation="portrait" >
    </activity>
    <activity
        android:name="soft_world.mycard.paymentapp.ui.MainActivity"
        android:screenOrientation="portrait"
        android:windowSoftInputMode="adjustPan" >
    </activity>
    <activity
        android:name="soft_world.mycard.paymentapp.ui.TrainActivity"
        android:screenOrientation="portrait" >
    </activity>
    <activity
        android:name="com.google.zxing.CaptureActivity"
        android:screenOrientation="portrait" >
    </activity>
    <activity
        android:name="tw.com.mycard.paymentsdk.PSDKActivity"
        android:screenOrientation="portrait" >
    </activity>
    <activity
        android:name="soft_world.mycard.paymentapp.ui.billing.BillingWebViewActivity"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Dialog" >
    </activity>
    <activity
        android:name="soft_world.mycard.paymentapp.Ecom.ATMMenuActivity"
        ndroid:screenOrientation="portrait" >
    </activity>
    <activity
        android:name="com.xmobilepay.xpaymentlibs.XCardTypeForm"
        android:screenOrientation="portrait" >
    </activity>
    <activity
        android:name="com.xmobilepay.xpaymentlibs.PaymentErrResultForm"
        android:screenOrientation="portrait" >
    </activity>
    <activity
        android:name="com.fet.iap.activity.FetLoginActivity"
        android:configChanges="keyboardHidden|orientation|screenSize"
        android:theme="@android:style/Theme.Translucent.NoTitleBar"
        android:windowSoftInputMode="adjustPan" >
    </activity>
    <activity android:name="com.cht.iap.api.ChtRegMainActivity" />
    <activity android:name="com.cht.iap.api.ChtPhoneNumPayConfirmActivity" />
    <activity android:name="com.cht.iap.api.ChtRegEInvoiceInfo" />
    <activity android:name="com.cht.iap.api.ChtRegVerifyOTP" />
    <activity android:name="com.cht.iap.api.ChtRegHNDataTabActivity" />
    <activity android:name="com.cht.iap.api.ChtRegHNAccountActivity" />
    <activity android:name="com.cht.iap.api.ChtRegMobileAuth" />
    <activity android:name="com.cht.iap.api.ChtRegMobileHNData" />
    <activity android:name="com.cht.iap.api.ChtTransactionAuth" />
    <activity android:name="com.cht.iap.api.ChtRegVerifyMessage" />
    <activity
        android:name="com.softmobile.ui.PayPageActivity"
        android:configChanges="orientation"
        android:screenOrientation="portrait" />
    <activity
        android:name="com.payeasenet.token.lib.ui.TokenPayTypeCheckUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    <activity
        android:name="com.payeasenet.token.lib.ui.CardTypeCheckUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    <activity
         android:name="com.payeasenet.token.lib.ui.TokenCreateUI"
         android:screenOrientation="portrait"
         android:theme="@android:style/Theme.Light.NoTitleBar" >
    </activity>
    <activity
        android:name="com.payeasenet.token.lib.ui.TokenCreateResultUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    <activity
        android:name="com.payeasenet.token.lib.ui.TokenPayUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    <activity
        android:name="com.payeasenet.token.lib.ui.PEPayRelUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    <activity
        android:name="com.payeasenet.token.lib.ui.TokenIntroductionUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" >
    </activity>
    <activity
        android:name="com.payeasenet.token.lib.ui.TokenUnBindedUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" >
    </activity>
    <activity
        android:name="com.payeasenet.token.lib.ui.MoreAboutUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    <activity
        android:name="com.payeasenet.token.lib.ui.PEQuickPayUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    <activity
        android:name="com.payeasenet.token.lib.ui.PEUpmpPayUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    <activity
        android:name="com.payeasenet.token.lib.ui.PEVisaPayUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    <activity
        android:name="com.payeasenet.token.lib.ui.PEVisaInfoUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    <activity
        android:name="com.payeasenet.token.lib.ui.PEVisaBillInfoUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    <activity
        android:name="com.payeasenet.token.lib.ui.PEDebitBillInfoUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    <activity
        android:name="com.payeasenet.token.lib.ui.PEQuickInfoUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    <activity
        android:name="com.payeasenet.token.lib.ui.PEUpmpInfoUI"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" />
    -->
    <!-- Mycard end -->
    
    
    <!-- Gash begin -->
    <!-- 如果使用Gash支付功能，请打开以下配置 -->
    <!--
    <activity
        android:name="com.gashpoint.gpclientsdk.SdkActivity"
	android:configChanges="fontScale|keyboard|keyboardHidden|locale|mnc|mcc|navigation|orientation|screenLayout|screenSize|smallestScreenSize|uiMode|touchscreen"
        android:exported="true"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen" />
    -->
    <!-- Gash end -->
    <!-- EGLS Android Game Socialization SDK end -->
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
    AGPManager.initSDK(this, AppUtil.getVersionName(this), new AGPInitProcessListener() {// SDK初始化回调

        @Override
        public void onInitProcess(int action, String msg) {
            if (action == 0) {// 当SDK初始化成功后再做后续的事情

            }
        }
    });
}
```
### 7. SDK登录（必接）
```Java
int loginMode = Constants.MODE_LOGIN_COMMON;
AGPManager.eglsLogin(loginMode, new AGPLoginProcessListener() {

    @Override
    public void onTokenFailure() {
	// token失效回调，这里需实现游戏返回到登录页面的逻辑
    }

    @Override
    public void onLoginProcess(int action, String token, String uid, String msg) {
	// 登录结果回调，只有当action为0时，示为登录成功
	// msg = "0"时，表示游客账号登录
	// msg = "1"时，表示EGLS账号登录
	// msg = "2"时，表示Google账号登录
	// msg = "3"时，表示Facebook账号登录
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
String cpOrderInfo = "2SDF34DF12GH0S23234GAER5";// CP订单信息，由接入方生成
String flag = "";// 额外标记，一般传空字符串即可
AGPManager.eglsPay(amount, productId, productName, cpOrderInfo, flag, new AGPClientPayProcessListener(){
	
    @Override
    public void onClientPayFinish(String message) {
	// 客户端支付完成回调
	// 客户端的支付完成并不能够完全代表支付操作成功，请以服务器的通知为准
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
int type = Constants.TYPE_SHARE_FACEBOOK;
String shareTitle = "";// 分享标题
String shareText = "";// 分享文本
String shareImageFilePath = "";// 分享图片（绝对路径）
String shareLink = "";// 分享链接
boolean isTimelineCb = false;
AGPManager.eglsShare(this, type, shareTitle, shareText, shareImageFilePath, shareLink, isTimelineCb, new AGPShareProcessListenter() {

    @Override
    public void onShareProcess(int type, int action, String message) {
        // 当type为Constants.TYPE_SHARE_FACEBOOK时，表示Facebook分享
	// 当type为Constants.TYPE_SHARE_LINE时，表示LINE分享
        // 当action为0时，表示分享成功
	// 当action为1时，表示分享取消
	// 当action为2时，表示分享失败
    }
});
```
### 11. Firebase云消息推送（选接）
当有需要使用Firebase的云消息推送时，除了按照对接文档中“3.1”、“3.4”和“4.3”的说明进行配置以外，还需要从Google后台下载一个名为“google-services.json”的文件（该文件由我方运营提供），并将该文件放在当前游戏Module工程目录下，如下图所示：<br/>
![image](https://github.com/sonicdjgh/egls-android-game-sdk-release-studio/blob/master/res/S4001.png)<br/>
另外，可在“AndroidManifest.xml”文件中修改推送图标的图案和底色（图案必须为白色，且背景透明）。

### 12. SDK运营活动（根据运营需求）
SDK的“运营活动”接口，主要是为游戏提供了相关操作页面以及SDK功能接口的实现。在这之前，为了实现这些运营活动，都需要游戏来承担相关页面的开发、第三方SDK的功能对接以及奖励发放的逻辑开发等等。而现在，游戏可以通过调用SDK的“运营活动”功能接口就可以轻松地展示相关操作页面，并通过回调方法的响应来处理奖励发放的相关逻辑。

关于“五星评价”、“Facebook运营活动”以及“LINE推广”的运营活动功能接口，在使用前，需要配合我方运营在后台上配置相关展示所需的图片。“五星评价”的图片宽高比为**3:1**，其他则为**5:2**。
```Java
// 五星评价
AGPManager.openFiveStarReview(this, new OnSimpleActionCallback() {

    @Override
    public void onFinish() {
        //评价操作完成，可根据此回调做之后的逻辑处理
    }
});
	
// Facebook运营活动（加入粉丝团、分享）
boolean isEnableJoin = true;
boolean isEnableShare = true;
AGPManager.openFacebookOperation(this, isEnableJoin, isEnableShare, new OnSimpleActionCallback() {

    @Override
    public void onFinish() {
        //加入操作完成，可根据此回调做之后的逻辑处理
    }
}, new OnSimpleActionCallback() {

    @Override
    public void onFinish() {
        //分享操作完成，可根据此回调做之后的逻辑处理
    }
});

// LINE推广
AGPManager.openLINEPromotion(this, new OnSimpleActionCallback() {

    @Override
    public void onFinish() {
        //操作完成，可根据此回调做之后的逻辑处理
    }
});
```
### 13. 其他注意事项
1. 凡是游戏项目工程为Android Studio工程，并且在Gradle里配置了productFlavor来控制打包流程的，请务必在调用“AGPManager.initSDK()”接口前，写上如下逻辑代码：
```Java
AGPManager.addFlavorsBasePackage(BuildConfig.class.getPackage().getName());
```
2. Google推荐的审核中，会对游戏首次运行时所使用的必要“危险权限”的申请和使用进行检查。SDK会主动申请“android.permission.WRITE_EXTERNAL_STORAGE”权限，但如果游戏还另需申请其他的“危险权限”，可以在调用“AGPManager.initSDK()”接口前，使用“addNecessaryPermission()”接口。例如：
```Java
AGPManager.addNecessaryPermission(Manifest.permission.READ_PHONE_STATE);
AGPManager.addNecessaryPermission(Manifest.permission.RECORD_AUDIO);
```
3. 同样也是为了适应Google推荐的审核要求，SDK在游戏第一次安装并启动后，会先弹出一个关于危险权限使用的说明。SDK默认的说明只有关于SD卡权限的使用说明，如果游戏在初始化时有使用到其他的危险权限，那么可以在调用“AGPManager.initSDK()”接口前，使用如下方法来修改提示文本：
```Java
// 需要注意的是，该接口是直接替换原默认文本的，所以还需要加上SD卡权限的使用说明。
String permissionContent = "xxx";
AGPManager.addPermissionContent(permissionContent);
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
Google Play | 2
Mycard | 3
OneStore | 4
Gash | 5
