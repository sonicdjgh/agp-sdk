package com.egls.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.egls.platform.components.AGPManager;
import com.egls.platform.interfaces.AGPClientPayProcessListener;
import com.egls.platform.interfaces.AGPInitProcessListener;
import com.egls.platform.interfaces.AGPLoginProcessListener;
import com.egls.support.utils.AppUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class MainH5Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AGPManager.initSDK(this, AppUtil.getVersionName(this), true, new AGPInitProcessListener() {// SDK初始化回调

            @Override
            public void onInitProcess(int code, String msg) {
                if (code == 0) {// 当SDK初始化成功后再做后续的事情

                }
            }
        });
    }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AGPManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AGPManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        AGPManager.onNewIntent(intent);
    }

    /**
     * H5与Android原生交互核心接口
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
                }else if (method.equals("onEnterGame")) {// 进入游戏后需调用该接口
                    onEnterGame();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void requestLogin(final int loginMode, final String callback) {
        AGPManager.eglsLogin(loginMode, new AGPLoginProcessListener() {

            @Override
            public void onLoginProcess(int action, String token, String uid, String accountType, String nickName) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("action", action);
                    json.put("token", token);
                    json.put("uid", uid);
                    json.put("accountType", accountType);
                    json.put("nickName", nickName);
                    AGPManager.callJSMethodWithJsonStr(callback, json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoginCancel() {
                try {
                    JSONObject json = new JSONObject();
                    json.put("action", 1);
                    json.put("token", "");
                    json.put("uid", "");
                    json.put("accountType", "");
                    json.put("nickName", "");
                    AGPManager.callJSMethodWithJsonStr(callback, json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTokenFailure() {
                try {
                    JSONObject json = new JSONObject();
                    json.put("action", 3);
                    json.put("token", "");
                    json.put("uid", "");
                    json.put("accountType", "");
                    json.put("nickName", "");
                    AGPManager.callJSMethodWithJsonStr(callback, json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void requestPurchase(final String amount, final String productId, final String productName, final String cpOrderId) {
        AGPManager.eglsPay(amount, productId, productName, cpOrderId, "", new AGPClientPayProcessListener() {

            @Override
            public void onClientPayFinish(String eglsOrderId) {

            }

            @Override
            public void onClientPayError() {

            }

            @Override
            public void onClientPayCancel() {

            }
        });
    }

    private void onEnterGame() {
        AGPManager.onEnterGame();
    }

}
