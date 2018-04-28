# EGLS-Android-Game-SDK-TW-4.X.X(Client-koKR)
### 1. 소개
EGLS Android Game SDK를 사용해 주셔서 진심으로 감사드립니다,이SDK문서는 **대만/홍콩** 퍼블리싱게임에적용합니다<br/><br/>
4.x.x버전부터 새로운 계정 로직을 업데이트 했습니다.이전 버전과는 같이 사용할 수 없습니다.(즉 같은 계정으로 로그인후 다시 돌아가기하면 UID와 3.x.x버전이 같지 않음)만약 게임이 구버전의 SDK를 적용하고 있고 4.x.x버전의 SDK를 사용하려면 게임 apk교체와 기타 필요한 작업이 필요합니다.(자세한건 저희 운영팀한테 문의하시길 바랍니다.)
### 2. Key/ID
#### 2.1 eglsAppId
EGLS 에서 세팅한 앱 아이디입니다
#### 2.2 CHANNEL_GOOGLE_PUBLIC_KEY
Goole Play Console 페이지에서 생성한 PUBLIC KEY입니다.
#### 2.3 CHANNEL_SERVER_CLIENT_ID
Google API Console 페이지 OAuth 2.0 클라이인트 ID리스트중，Web Client에 해당하는 Client ID 수치.
#### 2.4 com.facebook.sdk.ApplicationId
Facebook앱 아이디입니다.
### 3. 개발환경
#### 3.1 gradle버전 및 설정
gradle버전은 4.1이며 Project안의 gradle.properties문서중에서 아래의 세팅이 필요함:
```gradle
android.enableAapt2=false
```
#### 3.2 관계도
![image](https://github.com/sonicdjgh/egls-android-game-sdk-release-studio/blob/master/res/tw/S4TW000.png)<br/>
가이드라인:Demo가 SDK을위한구글게임프로젝트를상정한것을상기하면, Demo는 Module 'AGP'를도입할경우, Demo에삽입되는build.gradle에아래와같이배치되어야합니다.
```gradle
repositories {
    flatDir {
        dirs project(':AGP').file('libs')
        dirs project(':AGS').file('libs')
    }
}

dependencies {
    compile project(':AGP')
}
```
#### 3.3 AGP lib 선택
홍콩대만지역에서발행되는게임은 Module 'AGP'에 'build.gradle'라는폴더에다음과같이배치되었습니다.<br/>
```gradle
repositories {
    flatDir {
        dirs 'libs'
        dirs project(':AGS').file('libs')
    }
}

dependencies {
    // base begin
    compile(name: 'egls-agp-sdk-4.2.3', ext: 'aar')
    compile project(':AGS')
    // base end

    // tw begin
    compile files('libs/tw/AF-Android-SDK-4.6.0.jar')
    // tw end
}
```
#### 3.4 AGS lib 선택
홍콩대만지역에서발행되는게임은Module'AGS'에 'build.gradle'라는폴더에다음과같이배치되었습니다.<br/>
```gradle
repositories {
    flatDir {
        dirs 'libs'
        dirs 'libs/kr'
    }
}

dependencies {
    // base begin
    compile(name: 'egls-ags-sdk-4.2.3', ext: 'aar')
    compile(name: 'egls-android-support-4.2.3', ext: 'aar')
    compile files('libs/openDefault-1.0.0-openDefaultRelease.jar')
    //
    compile 'com.google.android.gms:play-services-auth:11.0.1'
    compile 'com.google.android.gms:play-services-auth-base:11.0.1'
    compile 'com.google.android.gms:play-services-base:11.0.1'
    compile 'com.google.android.gms:play-services-basement:11.0.1'
    compile 'com.google.android.gms:play-services-drive:11.0.1'
    compile 'com.google.android.gms:play-services-games:11.0.1'
    compile 'com.google.android.gms:play-services-gcm:11.0.1'
    compile 'com.google.android.gms:play-services-iid:11.0.1'
    compile 'com.google.android.gms:play-services-tasks:11.0.1'
    //
    compile 'com.facebook.android:facebook-core:4.+'
    compile 'com.facebook.android:facebook-login:4.+'
    compile 'com.facebook.android:facebook-share:4.+'
    // base end

    // tw begin
    // 如果使用 MyCard 支付，请打开下面的配置
    // compile files('libs/tw/MyCardPaySDK.jar')
    // 如果使用 Gash 支付，请打开下面的配置
    // compile files('libs/tw/clientsdk_product_v2.jar')
    // tw end
}
```
#### 3.5 UnitySDK연동
a. 먼저Android Studio를사용하여 안드로이드 프로젝트를 세팅후SDK 연동을 합니다<br/><br/>
b. 주의사항:게임 MainActivity 는 Unity 와 UnityPlayerActivity 를 사용합니다<br/><br/>
c. 구글은위험한권한의사용에대해필수적으로요구하고있으며, 청약권한에가입하는논리가필요하다.하지만 Unity는 'AndroidManifest'파일에 'xml'라는파일을자동으로신청하므로, 논리적인통제가되지않습니다.만약필요하다면'AndroidManifest'파일에 'application'라는파일을넣어주십시오.<br/>
```Xml
<meta-data
    android:name="unityplayer.SkipPermissionsDialog"
    android:value="true" />
```
#### 3.6 기타
minSdkVersion = 16，targetSdkVersion >= 23
### 4. AndroidManifest.xml설정
#### 4.1 AGP Permission 설정
```Xml
<!-- AGP begin -->
<!-- AppsFlyer begin -->
<!-- AppsFlyer홍콩,대만에서 사용하는 통계삽입 기능 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<!-- 현재 연동하는 안드로이드패키지가 Google Play외의 기타 엡스토어에 사용하면,이 권한에 대해 반드시 설명해야 합니다,아니면 이 설명권한을 삭제해야 합니다 -->
<!-- <uses-permission android:name="android.permission.READ_PHONE_STATE" /> -->
<!-- AppsFlyer end -->
<!-- AGP end -->
```
#### 4.2 AGS Permission 설정
```Xml
<!-- AGS begin -->
<!-- Google  begin -->
<!-- Google Play기능 사용하려고싶으면，아래배치를 열어주십시오 -->
<uses-permission android:name="com.android.vending.BILLING" />
<!--
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
<!-- Google  end -->


<!-- Mycard begin -->
<!-- Mycard기능 사용하려고싶으면，아래배치를 열어주십시오-->
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
주의사항：이상 Permission 설정중SDK의 기본기능만 오픈합니다,만약 기타 기능이필요하면,관련된 Permission 설정을 해주세요.
#### 4.3 Application설정
```Xml
<!-- Mycard결제 사용시,application 에 아래 내용을 추가합니다android:name="tw.com.mycard.sdk.libs.PSDKApplication" -->
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
        <!-- DeepLink대만 지역으로 설정하면 LINE 보상형 설치 기능 사용 가능 -->
        <!-- “MY_PACKAGE_NAME”을 정식용 패키지명으로 수정 -->
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
    <!-- 'MY_APP_ID'는SDK초가화 필요한eglsAppId로 변경 -->
    <meta-data
        android:name="EGLS_APP_ID"
        android:value="\0MY_APP_ID" />
	
    <!-- 'MY_SERVER_TYPE'부분에 해당한 서비스 종류 코드를 입력해주세요.자세한 내용은“첨부파일 - serverType”참조해주세요. -->
    <meta-data
        android:name="EGLS_SERVER_TYPE"
        android:value="MY_SERVER_TYPE" />
	
    <!-- 'MY_PAY_CHANNEL'는 대응하는 결제코드로 변경.'첨부-payChannel'참조-->
    <meta-data
        android:name="EGLS_PAY_CHANNEL"
        android:value="MY_PAY_CHANNEL" />
	
    <!--특별한요구사항이없는경우'EGLS_PAY_IS_SANDBOX'의계수값은 'false'로되었습니다.-->
    <meta-data
        android:name="EGLS_PAY_IS_SANDBOX"
        android:value="false" />
	
    <!--특별한요구사항이없는경우'EGLS_PAY_OTHER_PARAM'의계수값은 ''로되어있다-->
    <meta-data
        android:name="EGLS_PAY_OTHER_PARAM"
        android:value="" />

    <!-- 'MY_SERVER_CLIENT_ID'는 Google API Console에 OAuth 2.0 크라이안트ID리스트에서 Web Client에 대응하는Client ID로 변경 -->
    <meta-data
        android:name="CHANNEL_SERVER_CLIENT_ID"
        android:value="MY_SERVER_CLIENT_ID"/>

    <!-- 'MY_APPLICATION_ID'는 Facebook Console에서 배치하는applicationId로 변경 -->
    <provider
        android:name="com.facebook.FacebookContentProvider"
        android:authorities="com.facebook.app.FacebookContentProviderMY_APPLICATION_ID"
        android:exported="true" />
    <meta-data
        android:name="com.facebook.sdk.ApplicationId"
        android:value="\0MY_APPLICATION_ID" />
    <!-- Base end -->
	
        
    <!-- AGP begin -->
    <!-- AppsFlyer begin -->
    <!-- AppsFlyer홍콩,대만지역에서 사용하는 통계 기능 -->
    <!-- 모든Install Referrer이 시스템에서 플레이하는referrer내용을 인지하기 위해서,AndroidManifest.xml에서 AppsFlyer기능이 반드시첫번째 순위에 있어야 하며,receiver tag가application tag에 있어야 합니다 -->
    <receiver
        android:name="com.appsflyer.MultipleInstallBroadcastReceiver"
        android:exported="true" >
        <intent-filter>
            <action android:name="com.android.vending.INSTALL_REFERRER" />
        </intent-filter>
    </receiver>
    <!-- AppsFlyer end -->
    <!-- AGP end -->


    <!-- AGS begin -->
    <activity
        android:name="com.egls.socialization.performance.AGSShareActivity"
        android:screenOrientation="landscape"
        android:theme="@style/AGSTheme.Translucent.NoTitleBar.Fullscreen.NoAnimation" >
    </activity>
	
    <!-- Google Play Game begin -->
    <!-- Google Play Game실행기능을사용하면아래배치를열어주십시오-->
    <!-- 'MY_GAMES_APP_ID'는 "MY_SERVER_CLIENT_ID"의첫번체"-"왼쪽에 숫자부분으로 변경 -->
    <!--
    <meta-data
        android:name="com.google.android.gms.games.APP_ID"
        android:value="\0MY_GAMES_APP_ID" />
    -->
    <!-- Google Play Game end -->
    
        
    <!-- Google Play 결제 begin -->
    <!-- Google Play결제를사용하려고싶으면실행기능을사용하면아래배치를열어주십시오 -->
    <!-- 'MY_PUBLIC_KEY'를Google Play관리자페이지에있는 'publicKey'로수정합니다 -->
    <!-- 4.1.0이전버전name속성:com.egls.socialization.google.play.BillingActivity-->
    <!--
    <activity
        android:name="com.egls.socialization.google.play.GooglePlayActivity"
        android:configChanges="fontScale|orientation|keyboardHidden|locale|navigation|screenSize|uiMode"
        android:screenOrientation="landscape"
        android:theme="@android:style/Theme.Translucent.NoTitleBar" /> 
            
    <meta-data
        android:name="CHANNEL_GOOGLE_PUBLIC_KEY"
        android:value="MY_PUBLIC_KEY" />
    -->
    <!-- Google Play 결제 end -->
    

    <!-- Facebook begin -->
    <!--만약게임에Facebook의'USER_FRIEND'권한을열려면아래의배치를열어주십시오. -->
    <!--
    <meta-data
            android:name="CNANNEL_PERMISSION_USER_FRIEND"
            android:value="true"/>
    -->
    <!-- Facebook end  -->


    <!-- Mycard 결제설정 begin -->
    <!-- Mycard결제를 사용하려고싶으면실행기능을사용하면아래배치를열어주십시오 -->	
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
    <!-- Mycard 결제설정 end -->
    
    
    <!-- Gash begin -->
    <!-- Gash결제를 사용하려고싶으면실행기능을사용하면아래배치를열어주십시오 -->
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
### 5. 기본설정（필수）
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
### 6. SDK초기화（필수）
```Java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String versionCode = "1";
    try {
	versionCode = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode + "";
    } catch (NameNotFoundException e) {
	e.printStackTrace();
    }
    int serverType = 2;// initSDK방법중serverType를유의해 주세요,자세한 설명은 “첨부 파일 - serverType”를 참고해 주세요
    AGPManager.initSDK(this, versionCode, new AGPInitProcessListener() {

	@Override
	public void onInitSDK(int code, String msg) {
	    if (code == 0) {// SDK초기화완료후 계속 진행
	    	AGPManager.setOpenChannelShare(true,new OnAGSShareCallback() {// 공유 기능 오픈 및 오픈 Return key 설정 
								
	            @Override
	            public void onShare(int requestCode,int resultCode, String msg) {
	            }
	    	});
	    } else {// 아니면 게임 종료
	        finish();
	    }
    	}
    });
}
```
### 7. SDK결제（필수）
```Java
boolean isOpenAutoLogin = false;// 한국 퍼블리싱 게임은true로 설정
AGPManager.eglsLogin(isOpenAutoLogin, new AGPLoginProcessListener() {

    @Override
    public void onTokenFailure() {
	// tokenReturn key 인증실패,게임 메인 로그인 화면으로 돌아가는 기능 개발
    }

    @Override
    public void onLoginProcess(int code, String token, String uid, String msg) {
	// 로그인 결과 값 Return，code=0이면 로그인 성공
	// msg = "0"，게스트 계정 등록
	// msg = "1"，EGLS계정 등록
	// msg = "2"，Google계정 등록
	// msg = "3"，Facebook계정 등록
    }

    @Override
    public void onLoginCancel() {
	// 로그인 Return key 실패
    }
});
```
### 8. SDK결제（필수）
```Java
String amount = "1";// 총금액
String productId = "PDT001";// 상품id
String productName = "다이아";// 상품이름
String cpOrderInfo = "2SDF34DF12GH0S23234GAER5";// CP주문번호
AGPManager.eglsPay(amount, productId, productName, cpOrderInfo, new AGPClientPayProcessListener(){
	
    @Override
    public void onClientPayFinish(String eglsOrderId) {
	// 클라이언트 결제 return 성공
	// 지불완료후결제완료가확실시되지않으므로서버의통지를기준으로하십시오.
    }

    @Override
    public void onClientPayError() {
    	// 클라이언트결제 return 실패
    }

    @Override
    public void onClientPayCancel() {
	// 클라이언트결제 return 취소
    }
});
```
### 9. onEnterGame설정（필수）
```Java
//유저가 서버에 로그인후,아래 내용을 반드시 사용해 주세요
AGPManager.onEnterGame();
```
### 10.SDK공유기능（선택）
```Java
String contentTitle = "공유";// 공유제목
String contentText = "텍스트내용";// 텍스트내용
String contentImage = "공유이미지";// 공유 이미지 파일의주소
String contentUrl = null; //공유링크url
Bundle shareBundle = new Bundle();
shareBundle.putString(Key.CONTENT_TITLE, contentTitle);
shareBundle.putString(Key.CONTENT_TEXT, contentText);
shareBundle.putString(Key.CONTENT_IMAGE, contentImage);
shareBundle.putString(Key.CONTENT_URL, contentUrl);
AGPManager.shareInTW(true, true, shareBundle);
```
### 11. 기타
1. 게임프로그램은안드로이드스튜디오에서운영하고있으며패킷을제작프로세스를조절위해 'Gradle'에 'productFlavor'를배치하면인터페이스"AGPManager.initSDK()"를호출하기전에아래의논리코드를써야하십시오.
```Java
AGPManager.addFlavorsBasePackage(BuildConfig.class.getPackage().getName());
```
2. 구글의추천심사에서는게임이처음적용될때사용할필요가있는위험권한의신청과사용을점검한다.SDK는“android.permission.WRITE_EXTERNAL_STORAGE”권한을자동신청하고，게임은다른 '위험한권한'을신청할필요가있는경우，인터페이스“AGPManager.initSDK()”를호출하기전에인터페이스“addNecessaryPermission()”를사용주십시오.예를들면:
```Java
AGPManager.addNecessaryPermission(Manifest.permission.READ_PHONE_STATE);
AGPManager.addNecessaryPermission(Manifest.permission.RECORD_AUDIO);
```
### 첨부 - serverType
serverType | value
---|---
중국 | 1
홍콩,대만 | 2
한국 | 3
일본 | 4
미국 | 5

### 첨부 - payChannel
payChannel | value
---|---
Google Play | 2
Mycard | 3
OneStore | 4
Gash | 5
