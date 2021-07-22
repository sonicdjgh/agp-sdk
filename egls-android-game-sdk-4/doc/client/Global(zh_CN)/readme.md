# EGLS-Android-Game-SDK-Global-4.X.X(Client-zhCN)
### 1. 简介
欢迎使用 Egls Android Game SDK，这篇SDK对接文档说明适用于在**全球**（**但不包含中国大陆及韩国**）地区发行的游戏。<br/><br/>
从4.x.x版本起，我们采用了新的账号体系，所以并不兼容旧版（即同一个账号在登录后返回的uid与3.x.x版本的不一致）。如果您的游戏曾经接过旧版本的SDK，并且将要使用4.x.x版本的SDK时，请配合我们做游戏的强更及其他必要的更新操作（详情请咨询我方运营）。
### 2. 所需参数
#### 2.1 EGLS_APP_ID
由我方给游戏分配的应用id，一个游戏对应一个。
#### 2.2 google_public_key
在Goole Play后台生成的支付公钥。
#### 2.3 google_client_id
在Google API后台“OAuth 2.0 客户端 ID”配置的列表中，关于“Web Client”项对应的“Client ID”参数值。
#### 2.4 com.facebook.sdk.ApplicationId
在Facebook后台生成的应用id。
### 3. 环境搭建
#### 3.1 gradle版本及库引用地址设置
gradle版本为4.4，并且请在当前Project目录下的build.gralde文件中加上如下配置：
```gradle
buildscript {
    repositories {
    	google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.1.2'
	
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
如果使用 Firebase 云消息推送功能，请在当前游戏工程目录下的build.gradle文件中加上如下配置：
```gradle
apply plugin: 'com.google.gms.google-services'
```
如果使用 LINE 登录，请在当前Project目录下的gradle.properties文件中加上如下配置：
```gradle
android.enableD8.desugaring=true
```
另外，还需要在当前Project目录下的gradle.properties文件中加上如下配置：
```gradle
EGLS_SDK_VERSION=4.8.92
```
#### 3.2 lib 选择
针对于在港台地区发行的游戏，请在当前Module目录下的“build.gradle”文件里打开如下图所示的配置：<br/>
```gradle
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    // base begin
    api "io.github.sonicdjgh:platform:$EGLS_SDK_VERSION@aar"
    api "io.github.sonicdjgh:payment:$EGLS_SDK_VERSION@aar"
    api "io.github.sonicdjgh:native:$EGLS_SDK_VERSION@aar"
    api "io.github.sonicdjgh:support:$EGLS_SDK_VERSION@aar"
    api 'com.android.support.constraint:constraint-layout:1.1.0'
    // base 
    
    // appsflyer begin
    api 'com.appsflyer:af-android-sdk:4+@aar'
    api 'com.android.installreferrer:installreferrer:1.0'
    // appsflyer end

    // google begin
    api 'com.google.android.gms:play-services-auth:16.+'
    api 'com.google.android.gms:play-services-base:16.+'
    api 'com.google.android.gms:play-services-basement:16.+'
    api 'com.google.android.gms:play-services-drive:16.+'
    api 'com.google.android.gms:play-services-games:16.+'
    api 'com.google.android.gms:play-services-gcm:16.+'
    api 'com.google.android.gms:play-services-iid:16.+'
    api 'com.google.android.gms:play-services-tasks:16.+'
    
    // googleplay begin
    // 如果使用 GooglePlay 支付，请打开下面的配置
    // api 'com.android.billingclient:billing:3.0.2'
    // googleplay end
    
    // firebase begin
    // 如果使用 Firebase 云消息推送，请打开下面的配置
    // api 'com.google.firebase:firebase-core:16.0.8'
    // api 'com.google.firebase:firebase-messaging:18.0.0'
    // firebase end
    // google end
    
    // facebook begin
    api 'com.facebook.android:facebook-core:5.+'
    api 'com.facebook.android:facebook-login:5.+'
    api 'com.facebook.android:facebook-share:5.+'
    // facebook end
    
    // LINE begin
    // 如果使用 LINE 登录，请打开下面的配置
    // api 'com.linecorp:linesdk:5.0.1'
    // LINE begin
    
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
#### 3.3 关于Unity的SDK接入
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
#### 3.4 其他
minSdkVersion = 17，targetSdkVersion = 29
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
		LINE_CHANNEL_ID          : "",// 用于SDK的LINE登录
		
		// APPS_FLYER_DEV_KEY    : "",// 用于AppsFlyer统计功能初始化，如果运营没有特殊需求，这里无需添加
                // base end
		
		// other begin
		GOOGLE_PLAY_PUBLIC_KEY   : "",// 用于SDK的Google Play支付，若无需求可不填
		GOOGLE_GAME_APP_ID       : "",// 用于SDK的Google Game成就系统，若无需求可不填
                // other end
        ]
```
#### 4.2 Permission 配置
```Xml
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
```
请注意：以上 Permission 配置中只打开了SDK基础功能相关的配置，如果使用到其他功能，请打开对应的 Permission 配置！
#### 4.3 Application相关配置
```Xml
<!-- 请注意Application标签中的“android:networkSecurityConfig”以及“android:requestLegacyExternalStorage”属性的设置 -->
</application
    android:name="com.egls.demo.GameApplication"
    android:allowBackup="false"
    android:icon="@drawable/icon"
    android:label="AGSDK Demo"
    android:networkSecurityConfig="@xml/network_security_config"
    android:requestLegacyExternalStorage="true">
	
    <!-- 游戏Activity -->	
    <activity
        android:name="com.egls.demo.GameActivity"
        android:configChanges="fontScale|orientation|keyboardHidden|locale|navigation|screenSize|uiMode"
        android:screenOrientation="landscape"
        android:theme="@style/EglsTheme.NoTitleBar.Fullscreen.NoAnimation" >
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

    <meta-data
        android:name="EGLS_DOMAIN"
        android:value="passport.elive.com.tw" />
    <!-- Base end -->
        

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
    
    <meta-data
        android:name="appsflyer_enable"
        android:value="true" />
    
    <!-- 如果有特殊需求修改devkey时，请打开以下配置 -->	
    <!--	
    <meta-data
        android:name="appsflyer_dev_key"
        android:value="${APPS_FLYER_DEV_KEY}" />
    -->
    <!-- AppsFlyer end -->


    <!-- Google begin -->
    <meta-data
        android:name="google_client_id"
        android:value="${GOOGLE_WEB_CLIENT_ID}" />
	
    <!-- 如果使用Firebase云消息推送，请打开以下配置 -->
    <!--
    <service
        android:name="com.egls.support.google.firebase.FirebaseMesgService"
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
        android:resource="@color/egls_push_color" />
    -->
	
    <!-- 如果使用Google Play Game成就功能，请打开以下配置 -->
    <!--
    <meta-data
        android:name="com.google.android.gms.games.APP_ID"
        android:value="\0${GOOGLE_GAME_APP_ID}" />
    -->

    <!-- 如果使用Google Play支付功能，请打开以下配置 -->
    <!--
    <meta-data
        android:name="google_public_key"
        android:value="${GOOGLE_PLAY_PUBLIC_KEY}" />
    -->
    <!-- Google end -->
    

    <!-- Facebook begin -->
    <meta-data
        android:name="com.facebook.sdk.ApplicationId"
        android:value="\0${FACEBOOK_APPLICATION_ID}" />

    <!--如果游戏需要开启Facebook的“USER_FRIEND”权限，请打开以下配置 -->
    <!--
    <meta-data
        android:name="facebook_user_friends_enable"
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


    <!-- LINE begin -->
    <!-- 如果使用 LINE 登入，请打开以下配置 -->
    <!--
    <meta-data
        android:name="line_channel_id"
        android:value="${LINE_CHANNEL_ID}" />
    -->
    <!-- LINE end -->


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
</application>
```

### 5. 基础方法实现（必接）
```Java 
@Override
protected void onCreate() {
    super.onCreate();
    EglsPlatform.onCreate(this);
}

@Override
protected void onResume() {
    super.onResume();
    EglsPlatform.onResume(this);
}
    
@Override
protected void onPause() {
    super.onPause();
    EglsPlatform.onPause(this);
}
	
@Override
protected void onDestroy() {
    super.onDestroy();
    EglsPlatform.onDestroy(this);
}
```

### 6. SDK初始化（必接）
```Java
// 请在你的Application类中，按照如下进行接口的调用：
@Override
public void onCreate() {
    super.onCreate();
    EglsTracker.initApplication(this);
    EglsPlatform.initApplication(this);
}
```
```Java
// 请在你的Activity类中，按照如下进行接口的调用：
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EglsPlatform.setSDKActionHandler(new SDKActionHandler() {

        @Override
        public void onHandleInit(int state, String message) {
            if (state == Constants.SDK_STATE_SUCCESS) {// 初始化成功后的处理
                // 如果需要使用LINE登录，请调用如下接口
		// EglsPlatform.Config.setEnableLineSignIn(true);
		// 如果需要使用SDK悬浮窗导航栏中的登出，请调用如下接口
		// EglsPlatform.Config.setEnableFloateMenuLogout(true);
            } else {// 初始化失败后的处理
                
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
        public void onHandleLogin(int state, String token, String uid, String accountType, String nickName, String message) {// SDK登录的結果处理
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
        public void onHandleLogout() {
	    // 只有当点击悬浮窗中登出按钮后才会触发
        }

        @Override
        public void onHandleChannelBind(int state, String accountType, String nickName, String message) {// SDK绑定的結果处理
            switch (state) {
                case Constants.SDK_STATE_SUCCESS:// 绑定成功后的处理
                    break;
                case Constants.SDK_STATE_CANCEL:// 绑定取消后的处理
                    break;
                case Constants.SDK_STATE_ERROR:// 绑定失败后的处理
                    break;
            }
        }

        @Override
        public void onHandleTokenFailure() {// SDK登录token失效的结果处理（需要游戏实现返回到登录页面的逻辑）

        }

        @Override
        public void onHandlePurchase(int state, TradeInfo tradeInfo, String message) {// SDK支付的結果处理
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
        public void onHandleExit(boolean isExit) {// SDK退出游戏的結果处理（该方法只针对游戏调用SDK的"EglsPlatform.Support.exit()"接口的响应）
            if (isExit) {
                // 玩家选择退出后的处理
            } else {
                // 玩家选择继续游戏后的处理
            }
        }
    });
}
```

### 7. Account模块接口
“Account”模块中包含了与账号相关的功能接口。
#### 7.1 SDK UI Interface （主要适用于游戏）
在“Account”模块里所包含的接口名称中，带有“egls”词缀的接口，在调用时，会根据业务功能自身需求，来展示所需要的UI。
#### 7.1.1 egls登录
```Java
EglsPlatform.Account.eglsLogin(this, Constants.MODE_LOGIN_AUTO);
```
#### 7.1.2 egls切换账号
```Java
EglsPlatform.Account.eglsSwitch(this);
```
#### 7.1.3 egls用户中心
```Java
EglsPlatform.Account.eglsUserCenter(this);
```
<details>
<summary>7.2 SDK Lightly Interface （主要适用于应用）</summary><br />
	
在“Account”模块里所包含的接口名称中，带有“Lightly”词缀的接口，在调用时，不会显示SDK自身集成的相关UI。
#### 7.2.1 手机登录 
```Java
// 即传入手机号、密码后进行登录
// 响应登录回调，账号类型为：Constants.TYPE_USER_ACCOUNT_EGLS
EglsPlatform.Account.mobileLoginLightly(Activity activity, String mobile, String password)
```
#### 7.2.2 邮箱登录
```Java
// 即传入电子邮箱、密码后进行登录
// 响应登录回调，账号类型为：Constants.TYPE_USER_ACCOUNT_EGLS
EglsPlatform.Account.mailLoginLightly(Activity activity, String mail, String password)
```
#### 7.2.3 渠道登录
```Java
// 即根据传入的账号类型来调用对应的渠道登录，这里支持谷歌、Facebook登录
// 响应登录回调，返回登录的账号类型
// 另外，当accountType为空时，将采取默认登录，如果没有最近一次的登录记录，则进行游客登录；否则选择最近一次的登录账号进行登录
EglsPlatform.Account.channelLoginLightly(Activity activity, String accountType)
```
#### 7.2.4 手机注册
```Java
// 手机注册第一步为“手机注册验证”，即传入手机号后，发送验证码到手机上
// 响应接口里传入的回调，根据state状态来识别是否发送成功，message可用于消息提示
EglsPlatform.Account.mobileRegisterVerifyLightly(String mobile, OnSimpleActionCallback callback)

// 手机注册第二步为“手机注册请求”，即传入手机号、验证码及密码后，请求注册
// 响应登录回调，账号类型为：Constants.TYPE_USER_ACCOUNT_EGLS
EglsPlatform.Account.mobileRegisterRequestLightly(String mobile, String verificationCode, String password)
```
#### 7.2.5 邮箱注册
```Java
// 邮箱注册第一步为“邮箱注册验证”，即传入电子邮箱后，发送验证码到电子邮箱上
// 响应接口里传入的回调，根据state状态来识别是否发送成功，message可用于消息提示
EglsPlatform.Account.mailRegisterVerifyLightly(String mail, OnSimpleActionCallback callback)

// 邮箱注册第二步为“邮箱注册请求”，即传入电子邮箱、验证码及密码后，请求注册
// 响应登录回调，账号类型为：Constants.TYPE_USER_ACCOUNT_EGLS
EglsPlatform.Account.mailRegisterRequestLightly(String mail, String verificationCode, String password)
```
#### 7.2.6 渠道注销
```Java
// 即根据传入的账号类型来调用对应的渠道注销，当再次请求该渠道登录时，用户可以重新选择账号
// 需要注意的是，有些渠道SDK是不提供主动注销的逻辑接口的（比如Facebook的app登录，如果此时手机上装有Facebook应用，那么需要先在应用里切换账号）
// 另外，当accountType为空时，将采取默认注销，即注销当前所有的渠道登录
EglsPlatform.Account.channelLogoutLightly(String accountType) 
```
#### 7.2.7 手机绑定
```Java
// 手机绑定第一步为“手机绑定验证”，即传入手机号后，发送验证码到手机上
// 响应接口里传入的回调，根据state状态来识别是否发送成功，message可用于消息提示
EglsPlatform.Account.mobileBindVerifyLightly(String mobile, OnSimpleActionCallback callback)

// 手机绑定第二步为“手机绑定请求”，即传入手机号、验证码及密码后，请求绑定
// 响应绑定回调
// 需要注意的是，若为游客账号请求的绑定，在绑定成功后，游客账号变为手机账号（uid、token不变）；否则即添加了一个手机登录方式，当前登录的账号类型不变
// 目前，传入的密码对于非游客账号进行的绑定，是无效的
EglsPlatform.Account.mobileBindRequestLightly(String mobile, String verificationCode, String password) 
```
#### 7.2.8 邮箱绑定
```Java
// 邮箱绑定第一步为“邮箱绑定验证”，即传入电子邮箱后，发送验证码到电子邮箱上
// 响应接口里传入的回调，根据state状态来识别是否发送成功，message可用于消息提示
EglsPlatform.Account.mailBindVerifyLightly(String mail, OnSimpleActionCallback callback) 

// 邮箱绑定第二步为“邮箱绑定请求”，即传入电子邮箱、验证码及密码后，请求绑定
// 响应绑定回调
// 需要注意的是，若为游客账号请求的绑定，在绑定成功后，游客账号变为邮箱账号（uid、token不变）；否则即添加了一个邮箱登录方式，当前登录的账号类型不变
// 目前，传入的密码对于非游客账号进行的绑定，是无效的
EglsPlatform.Account.mailBindRequestLightLy(String mail, String verificationCode, String password)
```
#### 7.2.9 渠道绑定
```Java
// 即根据传入的账号类型来调用对应的渠道绑定，这里支持谷歌、Facebook登录
// 响应绑定回调
// 需要注意的是，若为游客账号请求的绑定，在绑定成功后，游客账号变为渠道账号（uid、token不变）；否则即添加了一个渠道登录方式，当前登录的账号类型不变
EglsPlatform.Account.channelBindLightly(Activity activity, String accountType)
```
#### 7.2.10 密码修改
```Java
// 即修改当前通过手机或邮箱登录的账号的登录密码
// 响应接口里传入的回调，根据state状态来识别是否修改成功，message可用于消息提示
EglsPlatform.Account.pwdModifyLightly(String password, OnSimpleActionCallback callback)
```
#### 7.2.11 密码重置
```Java
// 密码重置第一步为“密码重置鉴权”，即传入手机号或电子邮箱后，发送验证码到手机或电子邮箱上
// 响应接口里传入的回调，根据state状态来识别是否发送成功，message可用于消息提示
EglsPlatform.Account.pwdResetCaptchaLightly(String userAccount, OnSimpleActionCallback callback) 

// 密码重置第二步为“密码重置请求”，即传入手机号或电子邮箱、鉴权码后，请求密码重置
// 响应接口里传入的回调，根据state状态来识别是否重置成功，message可用于消息提示
EglsPlatform.Account.pwdResetRequestLightly(String userAccount, String captcha, OnSimpleActionCallback callback)
```
</details>


### 7.3 Other Interface
#### 7.3.1 账号进入
```Java
//在完成登录后，当玩家角色进入到服务器或是应用用户进入到主页面时，需要调用该方法
EglsPlatform.Account.onAccountEnter(this);
```

### 8 Payment模块接口
“Payment”模块中包含了与支付相关的功能接口。
#### 8.1 SDK UI Interface （主要适用于游戏）
在“Payment模块接口”模块里所包含的接口名称中，带有“egls”词缀的接口，在调用时，会根据业务功能自身需求，来展示所需要的UI。
#### 8.1.1 egls支付
```Java
String amount = "1.0";// 总金额
String productId = "PDT001";// 档位id
String productName = "钻石";// 档位名称
String cpOrderInfo = "2SDF34DF12GH0S23234GAER5";// CP订单信息，由接入方生成
EglsPlatform.Payment.eglsPurchase(amount, productId, productName, cpOrderInfo, Constants.FLAG_PURCHASE_DEFAULT);
```
#### 8.1.2 egls订阅（仅支持Google订阅）
```Java
String amount = "1.0";// 总金额
String productId = "PDT002";// 档位id
String productName = "月卡";// 档位名称
String cpOrderInfo = "2SDF34DF12GH0S23234GAER6";// CP订单信息，由接入方生成
EglsPlatform.Payment.eglsSubscribe(amount, productId, productName, cpOrderInfo);
```
<details>
<summary>8.2 SDK Lightly Interface （主要适用于应用）</summary><br />
	
在“Payment模块接口”模块里所包含的接口名称中，带有“Lightly”词缀的接口，在调用时，不会显示SDK自身集成的相关UI。
#### 8.2.1 渠道支付
```Java
String amount = "1.0";// 总金额
String productId = "PDT001";// 档位id
String productName = "钻石";// 档位名称
String cpOrderInfo = "2SDF34DF12GH0S23234GAER5";// CP订单信息，由接入方生成
EglsPlatform.Payment.channelPurchaseLightly(this, amount, productId, productName, cpOrderInfo, Constants.FLAG_PURCHASE_DEFAULT);
```
#### 8.2.2 渠道订阅（仅支持Google订阅）
```Java
String amount = "1.0";// 总金额
String productId = "PDT002";// 档位id
String productName = "月卡";// 档位名称
String cpOrderInfo = "2SDF34DF12GH0S23234GAER6";// CP订单信息，由接入方生成
EglsPlatform.Payment.channelPurchaseLightly(this， amount, productId, productName, cpOrderInfo);
```
</details>

### 9. Social模块接口
“Social”模块中包含了与社交相关的功能接口。
#### 9.1 渠道分享
```Java
int type = Constants.TYPE_SHARE_FACEBOOK;
String shareTitle = "";// 分享标题
String shareText = "";// 分享文本
String shareImageFilePath = "";// 分享图片（绝对路径）
String shareLink = "";// 分享链接
boolean isTimelineCb = false;
EglsPlatform.Social.channelShare(this, type, shareTitle, shareText, shareImageFilePath, shareLink, isTimelineCb);
```

### 10. Support模块接口
“Support”模块中包含了辅助相关的功能接口。
#### 10.1 游戏退出
```Java
EglsPlatform.Support.exit();
```
#### 10.2 Facebook游戏邀请
```Java
String title = "Let's go!";
String text = "Yeah!";
EglsPlatform.Support.getFacebookHelper().requestGameInvitation(this, title, text, new FacebookHelper.FacebookGameInvitationCallback() {

    @Override
    public void onSuccess(List<FacebookInvitedFriend> facebookInvitedFriends) {
	for (FacebookInvitedFriend friend : facebookInvitedFriends) {
            // friend.getNickName() 邀请好友的昵称
	    // friend.getUid() 邀请好友的uid
     	    // friend.getPicUrl() 邀请好友的头像地址
	}
    }

    @Override
    public void onCancel() {
    	
    }

    @Override
    public void onError(String message) {
	
    }
});
```
#### 10.3 Facebook用户好友信息获取
所谓“Facebook用户好友”，就是指使用相同app的Facebook好友，并不只是Facebook好友。目前，如果游戏里没有相关功能的需求，则不建议使用该接口（该接口的使用，需要通过Facebook的登录审核）。
```Java
EglsPlatform.Support.getFacebookHelper().getUserFriends(this, new FacebookHelper.FacebookGetUserFriendsCallback() {

    @Override
    public void onResponse(List<FacebookUserFriend> facebookUserFriends) {
	for (FacebookUserFriend friend : facebookUserFriends) {
	    // friend.getNickName() 用户好友的昵称
	    // friend.getUid() 用户好友的uid
     	    // friend.getPicUrl() 用户好友的头像地址
	}
    }
});
```

### 11. Firebase云消息推送（选接）
当有需要使用Firebase的云消息推送时，首先请在游戏项目的“/res/drawable”目录下，添加一张名为“egls_push_icon”的图片。然后，除了按照对接文档中“3.1”、“3.4”和“4.4”的说明进行配置以外，还需要从Google后台下载一个名为“google-services.json”的文件（该文件由我方运营提供），并将该文件放在当前游戏Module工程目录下，如下图所示：<br/>
![image](https://github.com/sonicdjgh/egls-android-game-sdk-release-studio/blob/master/res/S4001.png)<br/>


### 12. AppsFlyer数据统计（根据运营需求对接）
AppsFlyer主要用于Global业务的数据统计，启用该功能的做法，首先要按照上面所提到的，在AndroidManifest.xml文件中打开对应的配置。对于AppsFlyer统计功能的相关接口调用，其相关初始化部分的逻辑已经嵌入进SDK当中，因此开发者无需关心较为复杂的初始化步骤，只需根据需求，调用对应的接口即可。<br /><br />
#### 12.1 闪屏动画首次启动事件追踪（必接）
```Java
EglsTracker.trackEventCustom(EglsTracker.EVENT_ONE_SPLASH_IMAGE, null);
```
#### 12.2 新手任务开始事件追踪（必接）
```Java
EglsTracker.trackEventCustom(EglsTracker.EVENT_TUTORIAL_START, null);
```
#### 12.3 新手任务完成事件追踪（必接）
```Java
EglsTracker.trackEventCustom(EglsTracker.EVENT_TUTORIAL_COMPLETE, null);
```
#### 12.4 创建新角色事件追踪（必接）
```Java
EglsTracker.trackEventCustom(EglsTracker.EVENT_NEW_CHARACTER, null);
```
#### 12.5 游戏资源首次更新开始事件追踪（必接）
```Java
EglsTracker.trackEventCustom(EglsTracker.EVENT_ONE_UPDATE_START, null);
```
#### 12.6 游戏资源首次更新完成事件追踪（必接）
```Java
EglsTracker.trackEventCustom(EglsTracker.EVENT_ONE_UPDATE_COMPLETE, null);
```
#### 12.7 游戏资源首次加载开始事件追踪（必接）
```Java
EglsTracker.trackEventCustom(EglsTracker.EVENT_ONE_LOAD_START, null);
```
#### 12.8 游戏资源首次加载完成事件追踪（必接）
```Java
EglsTracker.trackEventCustom(EglsTracker.EVENT_ONE_LOAD_COMPLETE, null);
```
#### 12.9 自定义事件追踪()（根据需求接入）
```Java
// 有时候运营会针对具体的数据分析增加特定的事件统计，那么请调用该接口，传入特定的事件名称
// trackData的格式为json字符串，形如：{key:value,key:value,key:value...}
EglsTracker.trackEventCustom(trackEvent, trackData);
```

### 13. 其他注意事项
1. Google推荐的审核中，会对游戏首次运行时所使用的必要“危险权限”的申请和使用进行检查。SDK会主动申请“android.permission.WRITE_EXTERNAL_STORAGE”权限，但如果游戏还另需申请其他的“危险权限”，可以在调用“EglsPlatform.initActivity()”接口前，使用“addNecessaryPermission()”接口。例如：
```Java
EglsPlatform.Config.addNecessaryPermission(Manifest.permission.READ_PHONE_STATE);
EglsPlatform.Config.addNecessaryPermission(Manifest.permission.RECORD_AUDIO);
```
2. 同样也是为了适应Google推荐的审核要求，SDK在游戏第一次安装并启动后，会先弹出一个关于危险权限使用的说明。SDK默认的说明只有关于SD卡权限的使用说明，如果游戏在初始化时有使用到其他的危险权限，那么可以在调用“EglsPlatform.initActivity()”接口前，使用如下方法来修改提示文本：
```Java
// 需要注意的是，该接口是直接替换原默认文本的，所以还需要加上SD卡权限的使用说明。
String permissionContent = "xxx";
EglsPlatform.Config.setPermissionContent(permissionContent);
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
