# egls-android-game-sdk-release-studio

尊敬的开发者您好：<br /><br />
从4.x.x版本起，我们采用了新的账号体系，所以并不兼容旧版（即同一个账号在登录后返回的uid与3.x.x版本的不一致）。如果您的游戏曾经接过旧版本的SDK，并且将要使用4.x.x版本的SDK时，请配合我们做游戏的强更及其他必要的更新操作（详情请咨询我方运营）。<br /><br />
### SDK展示
![image](https://github.com/sonicdjgh/egls-android-game-sdk-release-studio/blob/master/res/demo-kr-login.gif)

### Version：4.8.92
代码内部调整及优化。
### Version：4.8.91
将 sdk 的 aar 文件由 JCenter 迁移至 Maven Central，具体配置修改请见文档。
### Version：4.8.89
修复已知bug。
### Version：4.8.62
修复内部bug。
### Version：4.8.58
1. 集成LINE登录；
2. 台湾开放手机登录及相关功能；
3. 优化内部逻辑。
### Version：4.8.24
优化内部逻辑。
### Version: 4.8.14
1. 根据最新的业务指示，为了适应原生app开发的对接需求，特将SDK内部逻辑做了一次较大的调整；
2. 由于“EglsPlatform”对外接口类中的方法越来越多，特此在这次的版本中进行分化，详细改动请详见对接文档。
### Version: 4.8.6
修复SDK初始化异常问题。
### Version: 4.8.3
1. 优化内部逻辑；
2. 上线轻量级（Lightly）业务功能接口。
### Version: 4.7.70
更新NaverCafe SDK版本。
### Version: 4.7.69
优化内部逻辑。
### Version: 4.7.46
优化内部逻辑。
### Version: 4.7.45
优化内部逻辑。
### Version: 4.7.33
1. 调整SDK内部结构，将第三方支付的部分独立成一个库，需在游戏工程的Module目录下的“build.gradle”文件中，添加对该库的引用：api "com.egls.android:payment:$EGLS_SDK_VERSION@aar"；
2. 修复已知bug。
### Version: 4.7.27
1. 修复已知bug；
2. 更正对接文档中Firebase服务的名称为：“com.egls.support.google.firebase.FirebaseMesgService”。
### Version: 4.7.10
修复已知bug。
### Version: 4.7.9
优化内部逻辑。
### Version: 4.7.2
修复已知bug。
### Version: 4.7.1
修复已知bug。
### Version: 4.7.0
1. 优化内部逻辑；
2. 原“AGPManager”类名正式被修改为“EglsPlatform”，其中大部分的静态方法没有改变，只有初始化以及相关辅助功能接口有少许变动，具体可看对接文档；
3. 新增“EglsTracker”类，用来统一管理SDK中所以集成的所有第三方的统计接口，具体可看对接文档。
