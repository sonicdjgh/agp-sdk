# EGLS-Android-Game-SDK-KR-4.X.X(Client-zhCN)
### 1. 简介
欢迎使用 EGLS Android Game SDK，这篇SDK对接文档说明适用于在**韩国**发行的游戏。<br/><br/>
从4.x.x版本起，我们采用了新的账号体系，所以并不兼容旧版（即同一个账号在登录后返回的uid与3.x.x版本的不一致）。如果您的游戏曾经接过旧版本的SDK，并且将要使用4.x.x版本的SDK时，请配合我们做游戏的强更及其他必要的更新操作（详情请咨询我方运营）。
### 2. 所需参数
#### 2.1 eglsAppId
由我方给游戏分配的应用id，一个游戏对应一个
#### 2.2 CHANNEL_GOOGLE_PUBLIC_KEY
在Goole Play后台生成的支付公钥。
#### 2.3 CHANNEL_GOOGLE_CLIENT_ID
在Google API后台“OAuth 2.0 客户端 ID”配置的列表中，关于“Web Client”项对应的“Client ID”参数值。
#### 2.4 com.facebook.sdk.ApplicationId
在Facebook后台生成的应用id。
#### 2.5 CHANNEL_ONESTORE_APP_ID
在OneStore后台生成的应用id。
### 3. 环境搭建
#### 3.1 gradle版本及设置
gradle版本为4.1，并且需要在当前Project根目录下的build.gralde文件中加上如下配置：
```gradle
buildscript {
    repositories {
    	google()
        jcenter()
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
EGLS_AGP_VERSION=4.5.35
EGLS_AGS_VERSION=4.5.35
EGLS_SUPPORT_VERSION=4.5.35
android.enableAapt2=false
```
#### 3.2 依赖关系
![image](https://github.com/sonicdjgh/egls-android-game-sdk-release-studio/blob/master/res/kr/S4KR000.png)<br/>
如上图所示：假设Demo为SDK对接完毕的安卓游戏工程，那么Demo引入Module“AGP”，则需要在Demo中的“build.gradle”里添加如下配置：
```gradle
repositories {
    flatDir {
        dirs project(':AGP').file('libs')
	dirs project(':AGP').file('libs/kr')
        dirs project(':AGS').file('libs')
        dirs project(':AGS').file('libs/kr')
    }
}

dependencies {
    implementation project(':AGP')
}
```
#### 3.3 AGP lib 选择
针对于在韩国地区发行的游戏，请在Module“AGP”的“build.gradle”文件里打开如下图所示的配置：<br/>
```gradle
repositories {
    flatDir {
        dirs 'libs'
	dirs 'libs/kr'
        dirs project(':AGS').file('libs')
        dirs project(':AGS').file('libs/kr')
    }
}

dependencies {
    // base begin
    api "com.egls.android:egls-agp-sdk:$EGLS_AGP_VERSION@aar"
    api project(':AGS')
    // base end

    // kr begin
    api files('libs/kr/IgawAdbrix_v4.6.0.jar')
    api files('libs/kr/IgawCommon_v4.6.0.jar')
    api(name: 'IgawLiveOps_v2.1.0', ext: 'aar')
    // kr end
}
```
#### 3.4 AGS lib 选择
针对于在韩国地区发行的游戏，请在Module“AGS”的“build.gradle”文件里打开如下图所示的配置：<br/>
```gradle
repositories {
    flatDir {
        dirs 'libs'
        dirs 'libs/kr'
    }
}

dependencies {
    // base begin
    api "com.egls.android:egls-ags-sdk:$EGLS_AGS_VERSION@aar"
    api "com.egls.android:egls-android-support:$EGLS_SUPPORT_VERSION@aar"
    api 'com.android.support.constraint:constraint-layout:1.1.0'
    // base end

    // kr begin
    api 'com.google.android.gms:play-services-auth:11.0.1'
    api 'com.google.android.gms:play-services-auth-base:11.0.1'
    api 'com.google.android.gms:play-services-base:11.0.1'
    api 'com.google.android.gms:play-services-basement:11.0.1'
    api 'com.google.android.gms:play-services-drive:11.0.1'
    api 'com.google.android.gms:play-services-games:11.0.1'
    api 'com.google.android.gms:play-services-gcm:11.0.1'
    api 'com.google.android.gms:play-services-iid:11.0.1'
    api 'com.google.android.gms:play-services-tasks:11.0.1'
    
    api 'com.facebook.android:facebook-core:4.+'
    api 'com.facebook.android:facebook-login:4.+'
    api 'com.facebook.android:facebook-share:4.+'
    
    api files('libs/kr/gson-2.8.0.jar');
    api files('libs/kr/3rdparty_login_library_android_4.1.4.jar')
    api files('libs/kr/api-gateway-hmac-2.3.1.jar')
    api files('libs/kr/library-1.0.0.jar')
    api 'com.github.bumptech.glide:glide:3.7.0'
    api 'com.squareup:otto:1.3.8'
    api 'com.navercorp.volleyextensions:volleyer:2.0.1', {
        exclude group: 'com.mcxiaoke.volley', module: 'library'
    }
    api(name: 'cafeSdk-4.2.1', ext: 'aar')
    
    // 如果使用 OneStore 支付，请打开下面的配置：
    // api files('libs/kr/iap_plugin_v17.01.00_20180206.jar');
    // kr end
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
minSdkVersion = 17，targetSdkVersion >= 27
### 4. AndroidManifest.xml文件配置
#### 4.1 AndroidManifest.xml中的参数配置
```gradle
// 在游戏Module的“build.gradle”中的“defaultConfig”里添加如下配置：
manifestPlaceholders = [
                // base begin
                EGLS_APP_ID              : "",// 用于SDK初始化 
                EGLS_PUBLISHMENT_AREA    : "",// 用于SDK识别发行区，可详见文档附录
                EGLS_PAY_CHANNEL         : "",// 用于SDK识别支付方式，可详见文档附录
                EGLS_PAY_IS_SANDBOX      : "false",// 港澳台发行区设为false即可
		
		GOOGLE_WEB_CLIENT_ID     : "",// 用于SDK的Google登录
		FACEBOOK_APPLICATION_ID  : "",// 用于SDK的Faceb登录
		
		IGAW_APP_KEY             : "",// 用于IgaWorks统计功能初始化
                IGAW_HASH_KEY            : "",// 用于IgaWorks统计功能初始化
		GAME_ACTIVITY_NAME       : "",// 用于IgaWorks统计功能的DeepLink定位，为游戏Activity的“name”全称
		
		NAVER_LOGIN_CLIENT_ID    : "",// 用于Naver Cafe论坛功能初始化
                NAVER_LOGIN_CLIENT_SECRET: "",// 用于Naver Cafe论坛功能初始化
                NAVER_CAFE_ID            : "",// 用于Naver Cafe论坛功能初始化
                // base end
		
		// other begin
		GOOGLE_PLAY_PUBLIC_KEY   : "",// 用于SDK的Google Play支付，若无需求可不填
		GOOGLE_GAME_APP_ID       : "",// 用于SDK的Google Game成就系统，若无需求可不填
		ONE_STORE_APP_ID         : "",// 用于SDK的One商店支付，若无需求可不填
                // other end
        ]
```
#### 4.2 AGP Permission 配置
```Xml
<!-- AGP begin -->
<!-- IGAW begin -->
<!-- IGAW为韩国地区所使用的统计功能，其他地区发行的游戏请不要使用 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.GET_TASKS" />
<permission
    android:name="${applicationId}.permission.C2D_MESSAGE"
    android:protectionLevel="signature" />
<uses-permission android:name="${applicationId}.permission.C2D_MESSAGE" />
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
<!-- IGAW end -->
<!-- AGP end -->
```
#### 4.3 AGS Permission 配置
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


<!-- OneStore begin -->
<!-- 如果使用OneStore支付，请打开以下配置 -->
<!--
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.RECEIVE_SMS" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
-->
<!-- OneStore end -->
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
	
    <!-- 游戏Activity -->
    <activity
        android:name="com.egls.sdk.demo.GameActivity"
        android:configChanges="fontScale|orientation|keyboardHidden|locale|navigation|screenSize|uiMode"
        android:screenOrientation="landscape"
        android:theme="@android:style/Theme.NoTitleBar.Fullscreen" >
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
	
        
    <!-- AGP begin -->
    <!-- IGAW begin -->
    <!-- IGAW为韩国地区所使用的统计功能，其他地区发行的游戏请不要使用 -->
    <activity
    	android:name="com.igaworks.IgawDefaultDeeplinkActivity"
        android:label="@string/app_name"
        android:launchMode="singleTask"
        android:noHistory="true"
        android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen" >
	<!-- DeepLink begin -->
    	<intent-filter android:label="@string/app_name">
            <action android:name="android.intent.action.VIEW" />

            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />

            <data
                android:host="${applicationId}.dplk"
                android:path="fb${FACEBOOK_APPLICATION_ID}"
                android:scheme="egls" />
        </intent-filter>

        <meta-data
            android:name="IgawRedirectActivity"
            android:value="${GAME_ACTIVITY_NAME}" />
	<!-- DeepLink end -->
    </activity>

    <receiver
        android:name="com.igaworks.IgawReceiver"
        android:exported="true" >
        <intent-filter>
            <action android:name="com.android.vending.INSTALL_REFERRER" />
        </intent-filter>
    </receiver>
    <receiver
        android:name="com.igaworks.liveops.pushservice.LiveOpsGCMBroadcastReceiver"
        android:permission="com.google.android.c2dm.permission.SEND" >
        <intent-filter>
            <action android:name="com.google.android.c2dm.intent.RECEIVE" />

            <category android:name="${applicationId}" />
        </intent-filter>
    </receiver>
    <service
        android:name="com.igaworks.liveops.pushservice.GCMIntentService"
        android:enabled="true" />
    <receiver
        android:name="com.igaworks.liveops.pushservice.LiveOpsReceiver"
        android:permission="${applicationId}.permission.C2D_MESSAGE" >
        <intent-filter>
            <action android:name="com.igaworks.liveops.pushservice.CLIENT_PUSH_RECEIVE" />
        </intent-filter>
    </receiver>
    <activity
        android:name="com.igaworks.liveops.pushservice.IgawLiveOpsPushMessageLauncherActivity"
        android:noHistory="true"
        android:permission="${applicationId}.permission.C2D_MESSAGE" />
	
    <meta-data
        android:name="igaworks_app_key"
        android:value="${IGAW_APP_KEY}" />
	
    <meta-data
        android:name="igaworks_hash_key"
        android:value="${IGAW_HASH_KEY}" />
    -->
    <!-- IGAW end -->
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
        android:theme="@style/EglsTheme.Translucent.NoTitleBar.Fullscreen.NoAnimation" />

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
    

    <!-- OneStore begin -->
    <!-- 如果使用OneStore支付功能，请打开以下配置 -->	
    <!--
    <meta-data
        android:name="iap:api_version"
        android:value="5" />
			 
    <meta-data
            android:name="iap:view_option"
            android:value="popup" />
				 
    <meta-data
        android:name="CHANNEL_ONESTORE_APP_ID"
        android:value="${ONE_STORE_APP_ID}"/>
    -->
    <!-- OneStore end -->
	
	
    <!-- NaverCafe begin -->
    <meta-data
        android:name="naver_login_client_id"
        android:value="${NAVER_LOGIN_CLIENT_ID}" />
				    
    <meta-data
        android:name="naver_login_client_secret"
        android:value="${NAVER_LOGIN_CLIENT_SECRET}" />
					
    <meta-data
        android:name="naver_cafe_id"
        android:value="${NAVER_CAFE_ID}" /> 
    <!-- NaverCafe end -->
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
    AGPManager.onDestroy();
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
    String versionCode = "1";
    AGPManager.initSDK(this, AppUtil.getVersionName(this), new AGPInitProcessListener() {// SDK初始化回调

        @Override
        public void onInitProcess(int action, String msg) {
            if (action == 0) {// 当SDK初始化成功后再做后续的事情

            }
        }
    });
    onNewIntent(getIntent());// 在韩国发行的游戏必须加入此代码
}
```
### 7. SDK登录（必接）
```Java
int loginMode = Constants.MODE_LOGIN_AUTO;
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
### 8. SDK支付（必接）
```Java
String amount = "1";// 总金额
String productId = "PDT001";// 档位id
String productName = "钻石";// 档位名称
String cpOrderInfo = "2SDF34DF12GH0S23234GAER5";// CP订单号
String flag = "";// 额外标记，一般传空字符串即可
AGPManager.eglsPay(amount, productId, productName, cpOrderInfo, flag, new AGPClientPayProcessListener(){
	
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
### 9. SDK游戏退出接口（必接）
```Java
//当需要退出游戏时，请务必调用该方法
AGPManager.eglsExit(new AGPExitProcessListener() {

    @Override
    public void onExitProcess(boolean isExit) {
  	if (isExit) {
	    //点击确认
	} else {
	    //点击取消
	}
    }
});
```
### 10. onEnterGame接口调用（必接）
```Java
//当玩家登录进入到游戏服务器之后，请务必调用该方法
AGPManager.onEnterGame();
```
### 11. SDK分享功能（选接）
```Java
int type = Constants.TYPE_SHARE_NAVER;
String shareTitle = "";// 分享标题
String shareText = "";// 分享文本
String shareImageFilePath = "";// 分享图片（绝对路径）
String shareLink = "";// 分享链接
boolean isTimelineCb = false;
AGPManager.eglsShare(this, type, shareTitle, shareText, shareImageFilePath, shareLink, isTimelineCb, new AGPShareProcessListenter() {

    @Override
    public void onShareProcess(int type, int action, String message) {
        // 当type为Constants.TYPE_SHARE_NAVER时，表示Naver分享
        // 当action为0时，表示分享成功
	// 当action为1时，表示分享取消
	// 当action为2时，表示分享失败
    }
});
```
### 12. IGAW数据统计（必接）
IGAW主要用于韩服地区发行的游戏的数据统计，启用该功能的做法，首先要按照上面所提到的，在AndroidManifest.xml文件中打开对应的配置。对于IGAW统计功能的相关接口调用，其相关初始化部分的逻辑已经嵌入进SDK当中，因此开发者无需关心较为复杂的初始化步骤，只需根据需求，调用对应的接口即可。<br /><br />
**注：通过调用AGPManager.getIgawHelper()来获取接口对象**。
#### 12.1 eventIgawSplashImage()（根据情况接入）
    如果游戏有闪屏动画（或首次启动的游戏动画），请在开始播放动画时调用该方法
#### 12.2 eventIgawCharacterSelect()（必接）
    请在玩家选择游戏角色后调用该方法（对于首次进入游戏的情况，请在创建角色后调用）
#### 12.3 eventIgawCharacterName()（必接）
    请在玩家创建角色并完成角色命名后调用该方法
#### 12.4 eventIgawTutorialStart()（根据情况接入）
    如果游戏有新手教学阶段，请在新手教学开始时调用该接口
#### 12.5 eventIgawtutorialComplete()（根据情况接入）
    如果游戏有新手教学阶段，请在新手教学结束时调用该接口
#### 12.6 eventIgawRoleLevelUp(int level)（必接）
    角色升级时调用该接口（当创建角色后，不必调用该接口）
#### 12.7 eventIgawVIPLevelUp(int level)（必接）
    玩家VIP等级提升时调用该接口
#### 12.8 eventIgawVisitShop()（必接）
    玩家打开游戏内的商店（指需要玩家真实付费购买的商店）页面时，请调用该接口
#### 12.9 eventIgawFansite()（根据情况接入）
    如果在游戏中有加入对Naver论坛的访问链接，请在打开Naver论坛时调用该接口（目前SDK已集成NaverCafeSDK，可以选择不自行添加Naver论坛的访问链接）
#### 12.10 eventIgawCustom(String eventName, String param)（根据情况介入）
    如果以上接口都无法满足事件统计需求，可以使用该接口进行自定义事件统计
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
