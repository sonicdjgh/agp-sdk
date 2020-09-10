package com.egls.demo;

import android.app.Activity;
import android.os.Bundle;

import com.egls.platform.components.EglsPlatform;
import com.egls.support.base.Constants;
import com.egls.support.beans.TradeInfo;
import com.egls.support.interfaces.SDKActionHandler;

import java.util.Map;

public class DemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EglsPlatform.onCreate(this);
        EglsPlatform.setSDKActionHandler(new SDKActionHandler() {

            @Override
            public void onHandleInit(int state, String message) {// SDK初始化的結果处理
                if (state == Constants.SDK_STATE_SUCCESS) {
                    // 初始化成功后的处理
                } else {
                    // 初始化失败后的处理
                }
            }

            @Override
            public void onHandleProfile(int state, String message) {

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
            public void onHandleLogin(int state, String token, String uid, String accountType, String nickName, String messsage) {// SDK登录的結果处理
                switch (state) {
                    case Constants.SDK_STATE_SUCCESS:// 登录成功后的处理
                        // accountType = "0" 时，表示游客账号登录
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
            public void onHandleChannelBind(int state, String accountType, String nickName, String message) {// SDK游客绑定的結果处理
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
            public void onHandlePurchase(int state, TradeInfo tradeInfo) {// SDK支付的結果处理
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

            @Override
            public void onHandleAppsFlyerData(int state, int type, Map<String, String> data) {

            }

            @Override
            public void onAdShow() {

            }

            @Override
            public void onAdSkip() {

            }

            @Override
            public void onAdClose() {

            }

            @Override
            public void onAdError() {

            }
        });
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

    private void requestLogin() {
        EglsPlatform.Account.eglsLogin(this, Constants.MODE_LOGIN_COMMON);
    }

    private void requestPurchase() {
        String amount = "0.0";
        String productId = "";
        String productName = "";
        String cpOrderInfo = "";
        EglsPlatform.Payment.eglsPurchase(this, amount, productId, productName, cpOrderInfo, Constants.FLAG_PURCHASE_DEFAULT);
    }

    private void onAccountEnter() {
        EglsPlatform.Account.onAccountEnter(this);
    }
}
