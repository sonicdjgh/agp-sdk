# EGLS-Android-Game-SDK-SEA-4.X.X(Client-zhCN)
### 1. 简介
欢迎使用 EGLS Android Game SDK，这篇SDK对接文档说明适用于在**东南亚**地区发行的游戏。<br/><br/>
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
另外，还需要在当前Project根目录下的gradle.properties文件中加上如下配置：
```gradle
EGLS_AGP_VERSION=4.6.9
EGLS_AGS_VERSION=4.6.9
EGLS_SUPPORT_VERSION=4.6.9
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
针对于在新加坡发行的游戏，请在Module“AGP”的“build.gradle”文件里打开如下图所示的配置：<br/>
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


    // sea begin
    api 'com.appsflyer:af-android-sdk:4+@aar'
    api 'com.android.installreferrer:installreferrer:1.0'
    // sea end
}
```
#### 3.4 AGS lib 选择
针对于在新加坡发行的游戏，请在Module“AGS”的“build.gradle”文件里打开如下图所示的配置：<br/>
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
    api "com.android.support:appcompat-v7:27.0.0"
    // base end

    // sea begin
    api 'com.google.android.gms:play-services-auth:15.+'
    api 'com.google.android.gms:play-services-base:15.+'
    api 'com.google.android.gms:play-services-basement:15.+'
    api 'com.google.android.gms:play-services-drive:15.+'
    api 'com.google.android.gms:play-services-games:15.+'
    api 'com.google.android.gms:play-services-gcm:15.+'
    api 'com.google.android.gms:play-services-iid:15.+'
    api 'com.google.android.gms:play-services-tasks:15.+'
    
    api 'com.facebook.android:facebook-core:4.+'
    api 'com.facebook.android:facebook-login:4.+'
    api 'com.facebook.android:facebook-share:4.+'
    // sea end
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
minSdkVersion = 17，targetSdkVersion = 27
### 4. AndroidManifest.xml文件配置
#### 4.1 AndroidManifest.xml中的参数配置
```gradle
// 在游戏Module的“build.gradle”中的“defaultConfig”里添加如下配置：
manifestPlaceholders = [
                // base begin
                EGLS_APP_ID              : "",// 用于SDK初始化 
                EGLS_PUBLISHMENT_AREA    : "",// 用于SDK识别发行区，可详见文档附录
                EGLS_PAY_CHANNEL         : "",// 用于SDK识别支付方式，可详见文档附录
                EGLS_PAY_IS_SANDBOX      : "false",// 新加坡发行区设为false即可
		
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
<!-- 暂无配置要求 -->
```
#### 4.3 AGS Permission 配置
```Xml
<!-- AGS begin -->
<!-- Google Play begin -->
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
<!-- Google Play end -->
<!-- AGS end -->
```
请注意：以上 Permission 配置中只打开了SDK基础功能相关的配置，如果使用到其他功能，请打开对应的 Permission 配置！
#### 4.4 Application相关配置
```Xml
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
        <intent-filter>
            <data
                android:host="${applicationId}"
                android:scheme="egls" />

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
        
    
    <!-- AGP begin -->
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
    <!-- 替换“MY_AF_DEV_KEY”字样为AppsFlyer后台配置的devkey -->
    <!--	
    <meta-data
        android:name="CHANNEL_AF_DEV_KEY"
        android:value="${APPS_FLYER_DEV_KEY}" />
    -->
    <!-- AppsFlyer end -->
    <!-- AGP end -->


    <!-- AGS begin -->
    <!-- Google begin -->
    <meta-data
        android:name="CHANNEL_GOOGLE_CLIENT_ID"
        android:value="${GOOGLE_WEB_CLIENT_ID}" />
	
    <!-- 如果使用Google Play Game成就功能，请打开以下配置 -->
    <!--
    <meta-data
        android:name="com.google.android.gms.games.APP_ID"
        android:value="\0${GOOGLE_GAME_APP_ID}" />
    -->

    <!-- 如果使用Google Play支付功能，请打开以下配置 -->
    <!-- 4.1.0版本以前name属性为“com.egls.socialization.google.play.BillingActivity” -->
    <!--
    <activity
        android:name="com.egls.socialization.google.play.GooglePlayActivity"
        android:configChanges="fontScale|orientation|keyboardHidden|locale|navigation|screenSize|uiMode"
        android:screenOrientation="behind"
        android:theme="@style/EglsTheme.AppCompat.Translucent.NoActionBar.Fullscreen.NoAnimation" />

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
    public void onLoginProcess(int action, String token, String uid, String accountType, String nickName) {
	// 登录结果回调，只有当action为0时，示为登录成功
	// accountType = "0"时，表示游客账号登录
	// accountType = "1"时，表示EGLS账号登录
	// accountType = "2"时，表示Google账号登录
	// accountType = "3"时，表示Facebook账号登录
    }

    @Override
    public void onLoginCancel() {
	// 登录取消回调
    }
});
```
### 8. SDK账号切换（必接）
```Java
AGPManager.eglsSwitch();
```
### 9. SDK支付（必接）
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
### 10. onEnterGame接口调用（必接）
```Java
//当玩家登录进入到游戏服务器之后，请务必调用该方法
AGPManager.onEnterGame();
```
### 11. 其他注意事项
1. 凡是游戏项目工程为Android Studio工程，并且在Gradle里配置了productFlavor来控制打包流程的，请务必在调用“AGPManager.initSDK()”接口前，写上如下逻辑代码：
```Java
AGPManager.addFlavorsBasePackage(BuildConfig.class.getPackage().getName());
```
2. Google推荐的审核中，会对游戏首次运行时所使用的必要“危险权限”的申请和使用进行检查。SDK会主动申请“android.permission.WRITE_EXTERNAL_STORAGE”权限，但如果游戏还另需申请其他的“危险权限”，可以在调用“AGPManager.initSDK()”接口前，使用“addNecessaryPermission()”接口。例如：
```Java
AGPManager.addNecessaryPermission(Manifest.permission.READ_PHONE_STATE);
AGPManager.addNecessaryPermission(Manifest.permission.RECORD_AUDIO);
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
