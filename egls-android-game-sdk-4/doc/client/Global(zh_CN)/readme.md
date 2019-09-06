# EGLS-Android-Game-SDK-Global-4.X.X(Client-zhCN)
### 1. 简介
欢迎使用 ELive Android Game SDK，这篇SDK对接文档说明适用于在**全球**（**但不包含中国大陆及韩国**）地区发行的游戏。<br/><br/>
从4.x.x版本起，我们采用了新的账号体系，所以并不兼容旧版（即同一个账号在登录后返回的uid与3.x.x版本的不一致）。如果您的游戏曾经接过旧版本的SDK，并且将要使用4.x.x版本的SDK时，请配合我们做游戏的强更及其他必要的更新操作（详情请咨询我方运营）。
### 2. 所需参数
#### 2.1 eglsAppId
由我方给游戏分配的应用id，一个游戏对应一个。
#### 2.2 CHANNEL_GOOGLE_PUBLIC_KEY
在Goole Play后台生成的支付公钥。
#### 2.3 CHANNEL_GOOGLE_CLIENT_ID
在Google API后台“OAuth 2.0 客户端 ID”配置的列表中，关于“Web Client”项对应的“Client ID”参数值。
#### 2.4 com.facebook.sdk.ApplicationId
在Facebook后台生成的应用id。
### 3. 环境搭建
#### 3.1 gradle版本及设置
gradle版本为4.1，并且需要在当前Project根目录下的build.gralde文件中加上如下配置：
```gradle
buildscript {
    repositories {
    	google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.1'
	
        // 如果使用Firebase云消息推送功能，请打开以下配置
    	// classpath 'com.google.gms:google-services:4.2.0'
    }
}

allprojects {
    repositories {
        google()
        jcenter()
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
EGLS_AGP_VERSION=4.6.65
EGLS_AGS_VERSION=4.6.65
EGLS_SUPPORT_VERSION=4.6.65
android.enableAapt2=false
```
#### 3.2 依赖关系
![image](https://github.com/sonicdjgh/egls-android-game-sdk-release-studio/blob/master/res/tw/S4TW000.png)<br/>
如上图所示：假设Demo为SDK对接完毕的安卓游戏工程，那么Demo引入Module“AGP”，则需要在Demo中的“build.gradle”里添加如下配置：
```gradle
android {
    buildToolsVersion "28.0.3"
    compileSdkVersion 28
}

repositories {
    flatDir {
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
    api 'com.android.support:appcompat-v7:27.0.0'
    
    // appsflyer begin
    api 'com.appsflyer:af-android-sdk:4+@aar'
    api 'com.android.installreferrer:installreferrer:1.0'
    // appsflyer end

    api 'com.google.android.gms:play-services-auth:16.+'
    api 'com.google.android.gms:play-services-base:16.+'
    api 'com.google.android.gms:play-services-basement:16.+'
    api 'com.google.android.gms:play-services-drive:16.+'
    api 'com.google.android.gms:play-services-games:16.+'
    api 'com.google.android.gms:play-services-gcm:16.+'
    api 'com.google.android.gms:play-services-iid:16.+'
    api 'com.google.android.gms:play-services-tasks:16.+'
    
    // 如果使用 Firebase 云消息推送，请打开下面的配置
    // api 'com.google.firebase:firebase-core:16.0.8'
    // api 'com.google.firebase:firebase-messaging:18.0.0'
    
    api 'com.facebook.android:facebook-core:5.+'
    api 'com.facebook.android:facebook-login:5.+'
    api 'com.facebook.android:facebook-share:5.+'
    // base end
    
    // googleplay begin
    // 如果使用 GooglePlay 支付，请打开下面的配置
    // api 'com.android.billingclient:billing:2.0.3'
    // googleplay end
    
    // mycard begin
    // 如果使用 MyCard 支付，请打开下面的配置
    // api files('libs/tw/MyCardPaySDK.jar')
    // mycard end
    
    // gash begin
    // 如果使用 Gash 支付，请打开下面的配置
    // api files('libs/tw/clientsdk_product_v2.jar')
    // gash end
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
d. 如果发现SDK的悬浮窗无法响应手势动作，请在“AndroidManifest.xml”文件中的“application”标签内加入如下配置：
```Xml
<meta-data 
    android:name="unityplayer.ForwardNativeEventsToDalvik" 
    android:value="true"/>
```
#### 3.6 其他
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
                EGLS_PAY_IS_SANDBOX      : "false",// 设为false即可
		
		GOOGLE_WEB_CLIENT_ID     : "",// 用于SDK的Google登录
		FACEBOOK_APPLICATION_ID  : "",// 用于SDK的Facebook登录
		
		// APPS_FLYER_DEV_KEY    : "",// 用于AppsFlyer统计功能初始化，如果运营没有特殊需求，这里无需添加
                // base end
		
		// other begin
		GOOGLE_PLAY_PUBLIC_KEY   : "",// 用于SDK的Google Play支付，若无需求可不填
		GOOGLE_GAME_APP_ID       : "",// 用于SDK的Google Game成就系统，若无需求可不填
                // other end
        ]
```
#### 4.2 AGP Permission 配置
```Xml
<!-- AGP begin -->
<!-- 暂没有可添加的配置 -->
<!-- AGP end -->
```
#### 4.3 AGS Permission 配置
```Xml
<!-- AGS begin -->
<!-- AppsFlyer begin -->
<!-- 如果现在接入的安卓包是针对除Google Play以外的其他应用商店，那么此权限一定需要声明，否则要删除该权限声明 -->
<!-- <uses-permission android:name="android.permission.READ_PHONE_STATE" /> -->
<!-- AppsFlyer end -->


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
#### 4.4 Application相关配置
```Xml
<!-- 如果用Mycard支付功能，请在application标签内添加属性 android:name="tw.com.mycard.sdk.libs.PSDKApplication" -->
    android:allowBackup="false"
    android:icon="@drawable/icon"
    android:label="AGSDK Demo"
    android:networkSecurityConfig="@xml/network_security_config"
    android:theme="@style/AppTheme" >
	
    <!-- 游戏Activity -->	
    <activity
        android:name="com.egls.sdk.demo.MainActivity"
        android:configChanges="fontScale|orientation|keyboardHidden|locale|navigation|screenSize|uiMode"
        android:screenOrientation="landscape"
        android:theme="@style/EglsTheme.AppCompat.Translucent.NoActionBar.Fullscreen.NoAnimation" >
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />

            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
        <!-- DeepLink begin -->
        <intent-filter>
            <data
                android:host="${applicationId}"
                android:scheme="egls${EGLS_APP_ID}" />

            <action android:name="android.intent.action.VIEW" />

            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
        </intent-filter>
        <!-- DeepLink end -->
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
        

    <!-- AGS begin -->
    <!-- AppsFlyer begin -->
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
    <!--	
    <meta-data
        android:name="CHANNEL_AF_DEV_KEY"
        android:value="${APPS_FLYER_DEV_KEY}" />
    -->
    <!-- AppsFlyer end -->


    <!-- Google begin -->
    <meta-data
        android:name="CHANNEL_GOOGLE_CLIENT_ID"
        android:value="${GOOGLE_WEB_CLIENT_ID}" />
	
    <!-- 如果使用Firebase云消息推送，请打开以下配置 -->
    <!--
    <service
        android:name="com.egls.socialization.google.firebase.FirebaseMesgService"
        android:exported="false">
        <intent-filter>
            <action android:name="com.google.firebase.MESSAGING_EVENT" />
        </intent-filter>
    </service>
    -->
	
    <!-- 如果使用Firebase云消息推送，请打开以下配置 -->
    <!-- Firebase云消息推送所使用的icon图案 -->
    <!--
    <meta-data
        android:name="com.google.firebase.messaging.default_notification_icon"
        android:resource="@drawable/egls_push_icon" />
    -->
	
    <!-- 如果使用Firebase云消息推送，请打开以下配置 -->
    <!-- Firebase云消息推送所使用的icon底色 -->
    <!--
    <meta-data
        android:name="com.google.firebase.messaging.default_notification_color"
        android:resource="@color/support_egls" />
    -->
	
    <!-- 如果使用Google Play Game成就功能，请打开以下配置 -->
    <!--
    <meta-data
        android:name="com.google.android.gms.games.APP_ID"
        android:value="\0${GOOGLE_GAME_APP_ID}" />
    -->

    <!-- 如果使用Google Play支付功能，请打开以下配置 -->
    <meta-data
        android:name="CHANNEL_GOOGLE_PUBLIC_KEY"
        android:value="${GOOGLE_PLAY_PUBLIC_KEY}" />
    -->
    <!-- Google end -->
    

    <!-- Facebook begin -->
    <meta-data
        android:name="com.facebook.sdk.ApplicationId"
        android:value="\0${FACEBOOK_APPLICATION_ID}" />
						    
    <meta-data
        android:name="CNANNEL_PERMISSION_EMAIL"
        android:value="true" />

    <!--如果游戏需要开启Facebook的“USER_FRIEND”权限，请打开以下配置 -->
    <!--
    <meta-data
        android:name="CNANNEL_PERMISSION_USER_FRIEND"
        android:value="true" />
    -->
						    
    <!-- 如果游戏需要使用Facebook分享功能，请打开以下配置 -->
    <!--
    <provider
        android:name="com.facebook.FacebookContentProvider"
        android:authorities="com.facebook.app.FacebookContentProvider${FACEBOOK_APPLICATION_ID}"
        android:exported="true" />
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
        android:screenOrientation="portrait" >
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
    AGPManager.initSDK(this, AppUtil.getVersionName(this), new SDKActionHandler() {

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
                    // accountType = "2"时，表示Google账号登录
                    // accountType = "3"时，表示Facebook账号登录
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
                    // 当type为Constants.TYPE_SHARE_FACEBOOK时，表示Facebook分享
                    // 当type为Constants.TYPE_SHARE_LINE时，表示LINE分享
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
AGPManager.eglsLogin(Constants.MODE_LOGIN_AUTO);
```
### 8. SDK切换账号（必接）
```Java
AGPManager.eglsSwitch();
```
### 9. SDK支付（必接）
```Java
String amount = "1.0";// 总金额
String productId = "PDT001";// 档位id
String productName = "钻石";// 档位名称
String cpOrderInfo = "2SDF34DF12GH0S23234GAER5";// CP订单信息，由接入方生成
String flag = "";// 额外标记，一般传空字符串即可
AGPManager.eglsPay(amount, productId, productName, cpOrderInfo, flag);
```
### 10. onEnterGame接口调用（必接）
```Java
//当玩家登录进入到游戏服务器之后，请务必调用该方法
AGPManager.onEnterGame();
```
### 11. SDK分享功能（选接）
```Java
int type = Constants.TYPE_SHARE_FACEBOOK;
String shareTitle = "";// 分享标题
String shareText = "";// 分享文本
String shareImageFilePath = "";// 分享图片（绝对路径）
String shareLink = "";// 分享链接
boolean isTimelineCb = false;
AGPManager.eglsShare(this, type, shareTitle, shareText, shareImageFilePath, shareLink, isTimelineCb);
```
### 12. Firebase云消息推送（选接）
当有需要使用Firebase的云消息推送时，首先请在游戏项目的“/res/drawable”目录下，添加一张名为“egls_push_icon”的图片。然后，除了按照对接文档中“3.1”、“3.4”和“4.4”的说明进行配置以外，还需要从Google后台下载一个名为“google-services.json”的文件（该文件由我方运营提供），并将该文件放在当前游戏Module工程目录下，如下图所示：<br/>
![image](https://github.com/sonicdjgh/egls-android-game-sdk-release-studio/blob/master/res/S4001.png)<br/>

### 13. SDK运营活动（根据运营需求）
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
### 14. AppsFlyer数据统计（根据运营需求对接）
AppsFlyer主要用于Global业务的数据统计，启用该功能的做法，首先要按照上面所提到的，在AndroidManifest.xml文件中打开对应的配置。对于AppsFlyer统计功能的相关接口调用，其相关初始化部分的逻辑已经嵌入进SDK当中，因此开发者无需关心较为复杂的初始化步骤，只需根据需求，调用对应的接口即可。<br /><br />
**注**：通过调用AGPManager.getAppsFlyerHelper()来获取接口对象。
#### 14.1 trackEventOneSplashImage()（必接）
    用于统计首次播放游戏闪屏动画的次数，请在开始播放动画时调用该方法
#### 14.2 trackEventTutorialStart()（必接）
    用于统计新手任务开始的次数，请在新手任务开始时调用该方法
#### 14.3 trackEventTutorialComplete()（必接）
    用于统计新手任务结束的次数，请在新手任务结束时调用该方法
#### 14.4 trackEventNewCharacter()（必接）
    用于统计创建角色的次数，请在创建角色成功后调用该方法
#### 14.5 trackEventOneUpdateStart()（必接）
    用于统计首次下载游戏资源开始的次数，请在下载游戏资源开始时调用该方法
#### 14.6 trackEventOneUpdateComplete()（必接）
    用于统计首次下载游戏资源结束的次数，请在下载游戏资源结束时调用该方法
#### 14.7 trackEventOneLoadStart()（必接）
    用于统计首次加载游戏资源开始的次数，请在加载游戏资源开始时调用该方法
#### 14.8 trackEventOneLoadComplete()（必接）
    用于统计首次加载游戏资源结束的次数，请在加载游戏资源结束时调用该方法
#### 14.9 trackEventCustom()（根据需求接入）
    有时候运营会针对具体的数据分析增加特定的事件统计，那么请调用该接口，传入特定的事件名称
### 15. 其他注意事项
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
