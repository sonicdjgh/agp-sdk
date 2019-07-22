# egls-android-game-sdk-release-studio

尊敬的开发者您好：<br /><br />
从4.x.x版本起，我们采用了新的账号体系，所以并不兼容旧版（即同一个账号在登录后返回的uid与3.x.x版本的不一致）。如果您的游戏曾经接过旧版本的SDK，并且将要使用4.x.x版本的SDK时，请配合我们做游戏的强更及其他必要的更新操作（详情请咨询我方运营）。<br /><br />
### SDK展示
![image](https://github.com/sonicdjgh/egls-android-game-sdk-release-studio/blob/master/res/demo-kr-login.gif)
### Version：4.6.24
为迎合Google审核要求，适应新的API版本（28）编译，升级了相关引用的库，并对SDK的内部逻辑进行对应的修改，其中包括：
1. Firebase云推送功能升级，提升google-services版本，即：classpath 'com.google.gms:google-services:4.2.0'；
2. AGS中所引用的google-support库升级，即：api 'com.android.support:appcompat-v7:28.0.0'；
3. AGS中google-gms库升级，即：<br />
    api 'com.google.android.gms:play-services-auth:16.+'<br />
    api 'com.google.android.gms:play-services-base:16.+'<br />
    api 'com.google.android.gms:play-services-basement:16.+'<br />
    api 'com.google.android.gms:play-services-drive:16.+'<br />
    api 'com.google.android.gms:play-services-games:16.+'<br />
    api 'com.google.android.gms:play-services-gcm:16.+'<br />
    api 'com.google.android.gms:play-services-iid:16.+'<br />
    api 'com.google.android.gms:play-services-tasks:16.+'<br />
4. Firebase云推送功能引用库升级，即：<br />
    api 'com.google.firebase:firebase-core:16.0.8'<br />
    api 'com.google.firebase:firebase-messaging:18.0.0'<br />
5. Firebase云推送功能的“AndroidManifest.xml”文件中的service配置修改为：<br />
    <service<br />
        android:name="com.egls.socialization.google.firebase.FirebaseMesgService"<br />
        android:exported="false"><br />
        <intent-filter><br />
            <action android:name="com.google.firebase.MESSAGING_EVENT" /><br />
        </intent-filter><br />
    </service><br />
### Version：4.6.19
1. 优化内部逻辑;
2. 2019年8月后，Google要求新上架的游戏，targetSdkVersion必须为28，则在“AndroidManifest.xml”文件中为“Application”标签内添加以下属性：<br />
   android:networkSecurityConfig="@xml/network_security_config"；
3. SDK的登录监听接口中增加“onAgreement(boolean isAgree)”方法，通过该方法可判断用户在游戏首次启动并点击登录后所弹出的“用户协议确认页”的操作。
### Version：4.6.15
修复已知bug。
### Version：4.6.14
1. 修复AppCompat与ConstraintLayout引起的布局问题；
2. 修复已知bug。
### Version：4.6.9
1. 提升google相关功能支持库版本，需修改“AGS”中“bulid.gradle”文件里的配置，如下：<br /><br />
    api 'com.google.android.gms:play-services-auth:15.+'<br />
    ~~api 'com.google.android.gms:play-services-auth-base:11.0.1'~~<br />
    api 'com.google.android.gms:play-services-base:15.+'<br />
    api 'com.google.android.gms:play-services-basement:15.+'<br />
    api 'com.google.android.gms:play-services-drive:15.+'<br />
    api 'com.google.android.gms:play-services-games:15.+'<br />
    api 'com.google.android.gms:play-services-gcm:15.+'<br />
    api 'com.google.android.gms:play-services-iid:15.+'<br />
    api 'com.google.android.gms:play-services-tasks:15.+'<br /><br />
    
    api 'com.google.firebase:firebase-analytics:15.+'<br />
    api 'com.google.firebase:firebase-analytics-impl:15.+'<br />
    api 'com.google.firebase:firebase-common:15.+'<br />
    api 'com.google.firebase:firebase-core:15.+'<br />
    api 'com.google.firebase:firebase-iid:15.+'<br />
    api 'com.google.firebase:firebase-messaging:15.+'<br />
2. 修改“AGS”中“AndroidManifest.xml”文件里的Google支付Activity的主题样式为：<br /><br />
    “@style/EglsTheme.AppCompat.Translucent.NoActionBar.Fullscreen.NoAnimation”
3. 为“AGS”中“bulid.gradle”文件里的配置增加一个库依赖，如下：<br /><br />
    api "com.android.support:appcompat-v7:27.0.0"
### Version：4.6.1
修复已知bug。
### Version：4.6.0
1. 集成微信支付；
2. 优化内部逻辑。
### Version：4.5.35
1. 优化参数配置方式；
2. minSdkVersion提升至17，targetSdkVersion提升至27。
### Version：4.5.31
优化内部逻辑。
### Version：4.5.16
SDK内部逻辑更新。
### Version：4.5.1
修复SDK的已登录EGLS账号列表的bug。
### Version：4.5.0
SDK内部逻辑更新。
### Version：4.4.47
修复韩国发行的支付统计的bug。
### Version：4.4.45
1. 修复在网络通信不好的状态下请求登录服务失败后，sdk弹出提示时有几率引发崩溃的问题；
2. 优化布局和其他资源文件；
3. 登录回调的接口增加返回参数（详见文档）;
4. 修复切换账号接口在韩国发行的游戏不起作用的问题。
### Version：4.4.31
修复EGLS账号注册成功后无法自动关闭页面的问题。
### Version：4.4.26
修复banner显示异常问题。
### Version：4.4.25
1. 修改banner样式，默认隐藏banner中的切换账号按钮（如果需要可通过接口调用来显示出来，详见文档）；
2. banner显示时长依旧为3s，3s后自动飞出。在banner显示期间，不再阻断除banner以外的点击事件的分发；
3. 增加切换账号接口（详见文档）；
4. 增加内嵌的AppsFlyer数据统计接口（详见文档）。
### Version：4.4.8
增加缺失的英文翻译。
### Version：4.4.6
修复自动登录模式下已知的bug。
### Version：4.4.3
开放台湾方行区运营活动接口。
### Version：4.4.2
韩国发行IGAWorks自定义事件统计接口修改。
### Version：4.4.1
1. 韩国发行增加IGAWorks自定义事件统计接口；
2. 修复韩国发行IGAWorks支付统计接口版本过旧的问题。
### Version：4.4.0
1. 台湾发行增加“五星评价”运营活动接口（具体使用方法请查阅相关对接文档）；
2. 台湾发行增加“Facebook”运营活动接口（具体使用方法请查阅相关对接文档）；
3. 台湾发行增加“LINE推广”运营活动接口（具体使用方法请查阅相关对接文档）；
4. 增加Firebase的云消息推送功能，目前暂时用来为台湾发行提供服务；
5. 重构SDK的分享接口（具体使用方法请查阅相关对接文档）；
6. SDK初始化监听接口中的onInitSDK()方法改为onInitProcess()；
7. 根据**港台发行区**的最新当地**运营法规**要求，需要使用Facebook的“email”权限（详见文档）；
8. 修复已知bug。

### Version：4.3.72
1. constraint库升级到1.1.0版本（com.android.support.constraint:constraint-layout:1.1.0）；
2. 修改“用户协议”页面弹出流程。
### Version：4.3.66
将so打进aar中，便于同步更新，不再需要单独拷贝到工程进行替换（当需要选择so类型时，可在项目工程的build.gradle文件里添加配置，否则默认使用so全部类型）。
### Version：4.3.65
修复游客绑定功能异常问题，需更换同名so文件。
### Version：4.3.62
修复已知bug。
### Version：4.3.60
修复OneStore SDK v5版本的支付bug。
### Version：4.3.59
修复Tablet设备UI的适配问题。
### Version：4.3.58
韩国发行区Google推荐审核新要求适配。
### Version：4.3.56
修复已知bug。
### Version：4.3.52
1. 增加对新加坡发行的适配逻辑；
2. 将原有的“EGLS_SERVER_TYPE”字段修改为“EGLS_PUBLISHMENT_AREA”；
3. 分享接口目前改版中，原有的接口不再推荐使用，等新的分享接口发布后，旧版分享接口将被废弃;
4. 为了兼容Android API 27版本要求，修改“com.egls.socialization.google.play.GooglePlayActivity”的“screenOrientation”属性值为“behind”，修改“theme”属性值为“@style/EglsTheme.Translucent.NoTitleBar.Fullscreen.NoAnimation”。
### Version：4.3.49
修复游客账号绑定功能的bug。
### Version：4.3.45
修复悬浮菜单中“NaverCafe”按钮点击后无响应问题。
### Version：4.3.44
1. 部分bug修复；
2. 韩国发行区所使用的OneStore SDK版本升级到v5，AndroidManifest.xml文件中，去掉名为“com.skplanet.dodo.IapWeb”的Activity配置，增加名为“iap:view_option”的meta元素配置；其库文件引用变更为“iap_plugin_v17.01.00_20180206.jar”。
### Version：4.3.25
修复在部分设备上初始化崩溃的问题。
### Version：4.3.24
修复港台发行区MyCard支付与SDK悬浮窗初始化冲突问题。
### Version：4.3.23
修复港台发行区MyCard支付与Facebook的SDK初始化冲突问题。
### Version：4.3.22
1. 修改韩国发行区所使用的IGAWorks统计SDK的Deep Link配置，并去掉游戏主Activity的launchMode="singleTask"的设置；
2. 修复已知bug。
### Version：4.3.16
优化逻辑代码。
### Version：4.3.9
解决SDK初始化时的bug（需同时更换so库文件）。
### Version：4.3.7
解决SDK初始化成功后的回调bug问题。
### Version：4.3.5
1. 优化SDK初始化逻辑；
2. 增加AGPManager.eglsExit()游戏退出接口（必接接口）；
3. 更新韩国发行区内部统计SDK（IGAWorks）版本至4.6.0（请留意对接文档中，游戏工程及AGP相关的Gradle配置）；
4. 韩国发行区的游戏，其游戏启动Activity的“launchMode”属性必须为“singleTask”。
### Version：4.3.1
1. 解决SDK初始化异常问题；
2. AGS增加库引用：api 'com.android.support.constraint:constraint-layout:1.0.2'。
### Version：4.3.0
优化网络通信接口及其他部分业务逻辑，在更新SDK时请修改版本号后再同步至最新版本，并且需要更换so文件。
### Version: 4.2.18
1. 优化逻辑代码；
2. 使用最新语法修改gradle脚本中的库引用部分。
### Version: 4.2.14
将以下sdk中的基础库上传至Maven仓库，在对接时，请留意文档中相关部分的使用方法修改：
<br />egls-agp-sdk.aar
<br />egls-ags-sdk.aar
<br />egls-android-support.aar
### Version: 4.2.11
完善4.2.10版本。
### Version: 4.2.10
修复韩国当地部分机型在读取大容量文字时引发的系统接口报错问题。
### Version: 4.2.9
部分逻辑代码优化。<br /><br />
### Version: 4.2.8
1. 修复Facebook登录无响应问题；
2. 增加Appsflyer的devKey配置，如果有特殊需求，可以在Manifest文件中添加meta标签，其name为“CHANNEL_AF_DEV_KEY”。<br /><br />
### Version: 4.2.7
1. 优化网络交互机制；
2. 修改接口AGPManager.onDestroy()的接口名拼写错误；
3. 将AGP、AGS中的so文件从aar中独立出来，便于在SDK对接时对so文件类型的选择。<br /><br />
### Version: 4.2.6
1. 修改消息提示面板UI；
2. 在用户中心页面增加SDK版本号的显示。<br /><br />
### Version: 4.2.5
1. 去掉在中国大陆发行的游戏对Google、Facebook的库依赖；
2. 简化Facebook登录逻辑代码；
3. 更新了AppsFlyer的SDK，并请更改引入的配置。<br /><br />
### Version: 4.2.4
修复部分发行区错误引用AppsFlyer库的问题。<br /><br />
### Version: 4.2.3
1. 修复4.2.0版本中在进行自动登录时引起的闪退问题；
2. 优化google支付流程；
3. 增加EGLS账号记忆功能，在切换账号时，点击EGLS的图标，弹出已登录EGLS账号的列表供玩家选择；
4. 完善登录流程；
5. CN发行区增加QQ登录。<br /><br />
### Version: 4.2.2
修复4.2.0版本中在游戏必要权限申请的过程中选择拒绝后闪退的问题。<br /><br />
### Version: 4.2.1
1. 修复Google登录时无法取消的问题；
2. 修复悬浮窗在安卓8.0系统的权限提示错误问题；
3. 修复4.2.0版本中注册账号闪退的问题。<br /><br />
### Version: 4.2.0
1. AGP、AGS的核心库及资源等已都打包成“aar”文件，在升级至4.2.0前，请先删除原有的Module然后重新导入；
2. 增加登录banner信息提示，并在banner中添加Egls账号的切换按钮（banner3秒后自动消失，之后响应登录回调）；
3. 优化部分逻辑代码。<br /><br />
### Version: 4.1.7
1. 将AGS的AndroidManifest.xml文件中的AGSShareActivity配置移除，需在游戏项目中的AndroidManifest.xml文件中添加该配置；
2. 优化部分逻辑代码。<br /><br />
### Version: 4.1.6
1. 删除旧版Facebook相关的资源和代码，引用最新版Facebook功能；
2. minSdkVersion升至为16，compileSdkVersion请用26。<br /><br />
### Version: 4.1.5
1. 修改权限弹窗提示功能，即：在首次运行时，当targetSdkVersion>=23且deviceSdkVersion>=23时，才会弹出；
2. 添加“AGPManager.addPermissionContent()”接口；
3. 修复台湾发行区游戏在支付时引起的缺失jar异常；
4. 修改sdk的后台服务设置。<br /><br />
### Version: 4.1.4
修复Facebook分享异常问题。<br /><br />
### Version: 4.1.3
修复IgawHelper类中的接口被误混淆的问题。<br /><br />
### Version: 4.1.2
1. 创立SDK的Android Studio版本；
2. 修复4.1.1版本中闪退的bug；
3. 优化游戏必要权限检测逻辑，提供addNecessaryPermission()接口；
4. 为适应Android Studio项目工程中Gradle的productFlavor配置，提供addFlavorsBasePackage()接口。<br /><br />
