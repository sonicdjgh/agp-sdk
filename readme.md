# egls-android-game-sdk-release-studio
注：从4.x.x版本起，我们采用了新的账号体系，所以并不兼容旧版（即同一个账号在登录后返回的uid与3.x.x版本的不一致）。如果您的游戏曾经接过旧版本的SDK，并且将要使用4.x.x版本的SDK时，请配合我们做游戏的强更及其他必要的更新操作（详情请咨询我方运营）。<br /><br />
### Version: 4.2.2
修复在游戏必要权限申请的过程中选择拒绝后闪退的问题。<br /><br />
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
