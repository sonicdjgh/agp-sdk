package com.egls.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.egls.platform.components.EglsPlatform;
import com.egls.support.base.Constants;
import com.egls.support.beans.TradeInfo;
import com.egls.support.interfaces.SDKActionHandler;
import com.egls.support.utils.AppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MainH5Activity extends Activity {

    private String jsLoginCallbackMethodName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EglsPlatform.initActivity(this, AppUtil.getVersionName(this), new SDKActionHandler() {

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
            public void onHandleLogin(int state, String token, String uid, String accountType, String nickName, String message) {// SDK登录的結果处理
                switch (state) {
                    case Constants.SDK_STATE_SUCCESS:// 登录成功后的处理
                        try {
                            JSONObject json = new JSONObject();
                            json.put("action", Constants.SDK_STATE_SUCCESS);
                            json.put("token", token);
                            json.put("uid", uid);
                            json.put("accountType", accountType);
                            json.put("nickName", nickName);
                            EglsPlatform.callJSMethodWithJsonStr(jsLoginCallbackMethodName, json.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case Constants.SDK_STATE_CANCEL:// 登录取消后的处理
                        try {
                            JSONObject json = new JSONObject();
                            json.put("action", Constants.SDK_STATE_CANCEL);
                            json.put("token", "");
                            json.put("uid", "");
                            json.put("accountType", "");
                            json.put("nickName", "");
                            EglsPlatform.callJSMethodWithJsonStr(jsLoginCallbackMethodName, json.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case Constants.SDK_STATE_ERROR:// 登录失败后的处理
                        try {
                            JSONObject json = new JSONObject();
                            json.put("action", 3);
                            json.put("token", "");
                            json.put("uid", "");
                            json.put("accountType", "");
                            json.put("nickName", "");
                            EglsPlatform.callJSMethodWithJsonStr(jsLoginCallbackMethodName, json.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void onHandleChannelBind(int state, String accountType, String nickName) {// SDK游客绑定的結果处理
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
            public void onHandlePay(int state, TradeInfo tradeInfo) {// SDK支付的結果处理
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
        EglsPlatform.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EglsPlatform.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EglsPlatform.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EglsPlatform.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EglsPlatform.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        EglsPlatform.onNewIntent(intent);
    }

    /**
     * H5与Android原生交互核心接口
     *
     * @param jsonStr
     */
    @JavascriptInterface
    public void postMessage(String jsonStr) {
        try {
            JSONObject json = new JSONObject(jsonStr);
            if (null != json && json.length() > 0) {
                String method = json.optString("method");
                if (method.equals("login")) {// 登录
                    int loginMode = json.optInt("loginMode", 0);
                    final String callback = json.optString("callback", "");
                    requestLogin(loginMode, callback);
                } else if (method.equals("pay")) {// 支付
                    String amount = json.optString("amount", "0.0");
                    String productId = json.optString("productId", "");
                    String productName = json.optString("productName", "");
                    String cpOrderId = json.optString("cpOrderId", "");
                    requestPurchase(amount, productId, productName, cpOrderId);
                } else if (method.equals("onEnterGame")) {// 进入游戏后需调用该接口
                    onEnterGame();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void requestLogin(final int loginMode, final String callback) {
        jsLoginCallbackMethodName = callback;
        EglsPlatform.eglsLogin(loginMode);
    }

    private void requestPurchase(final String amount, final String productId, final String productName, final String cpOrderId) {
        EglsPlatform.eglsPay(amount, productId, productName, cpOrderId, "");
    }

    private void onEnterGame() {
        EglsPlatform.onEnterGame();
    }

}
