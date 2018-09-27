package com.egls.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.egls.platform.components.AGPManager;
import com.egls.platform.interfaces.AGPClientPayProcessListener;
import com.egls.platform.interfaces.AGPInitProcessListener;
import com.egls.platform.interfaces.AGPLoginProcessListener;
import com.egls.platform.utils.AGPDebugUtil;
import com.egls.socialization.interfaces.OnAGSShareCallback;
import com.egls.support.utils.AppUtil;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AGPManager.initSDK(this, AppUtil.getVersionName(this) + "", new AGPInitProcessListener() {// SDK初始化回调

            @Override
            public void onInitSDK(int code, String msg) {
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

    private void reuestLogin() {
        AGPManager.eglsLogin(false, new AGPLoginProcessListener() {

            @Override
            public void onLoginProcess(int code, String token, String uid, String msg) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLoginCancel() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTokenFailure() {
                // TODO Auto-generated method stub

            }
        });
    }

    private void requestPurchase() {
        String amount = "0.0";
        String productId = "";
        String productName = "";
        String cpOrderId = "";
        AGPManager.eglsPay(amount, productId, productName, cpOrderId, new AGPClientPayProcessListener() {

            @Override
            public void onClientPayFinish(String eglsOrderId) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onClientPayError() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onClientPayCancel() {
                // TODO Auto-generated method stub

            }
        });
    }

}
