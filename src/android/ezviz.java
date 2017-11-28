package com.laitron.ezviz;

import android.content.Intent;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import com.videogo.constant.IntentConsts;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.ui.realplay.EZRealPlayActivity;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.ui.util.EZUtils;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.util.LogUtil;
import android.os.Bundle;

public class ezviz extends CordovaPlugin {
    public String eventName = "";

    //第一个参数为请求码，即调用startActivityForResult()传递过去的值
    //第二个参数为结果码，结果码用于标识返回数据来自哪个新Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("result");//得到新Activity关闭后返回的数据
        if (requestCode == 100) {
            //前面的 Activity退出了
        }
    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("openCamera")) {
            String appkey = data.getString(0);
            String accessToken = data.getString(1);
            String deviceSerial = data.getString(2);
            int camera_index = data.getInt(3);
            try {
                initSDK(appkey);
                EZOpenSDK.getInstance().setAccessToken(accessToken);
                EZDeviceInfo deviceInfo = EZOpenSDK.getInstance().getDeviceInfo(deviceSerial);
                Intent toIntent = new Intent(cordova.getActivity(), EZRealPlayActivity.class);
                EZCameraInfo cameraInfo = null;
                cameraInfo = EZUtils.getCameraInfoFromDevice(deviceInfo, camera_index);

                if (cameraInfo == null) {
                    callbackContext.error("没有找到摄像头信息");
                    return false;
                } else {
                    toIntent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
                    toIntent.putExtra(IntentConsts.EXTRA_DEVICE_INFO, deviceInfo);
                    Bundle extraInfo = new Bundle();
                    extraInfo.putParcelable(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
                    extraInfo.putParcelable(IntentConsts.EXTRA_DEVICE_INFO, deviceInfo);
                    extraInfo.putString("com.laitron.ezviz.action_on_preview", "");
                    extraInfo.putString("com.laitron.ezviz.evt_on_preview", eventName);
                    extraInfo.putString("com.laitron.ezviz.light_on_preview", "");
                    toIntent.putExtras(extraInfo);
                    cordova.getActivity().startActivityForResult(toIntent, 100);
                    callbackContext.success("");
                    return true;
                }
            } catch (BaseException e) {
                e.printStackTrace();
                ErrorInfo errorInfo = (ErrorInfo) e.getObject();
                LogUtil.debugLog("ezviz", errorInfo.toString());
                callbackContext.error(errorInfo.toString());
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    private void initSDK(String appkey) {
        EZOpenSDK.showSDKLog(true);
        EZOpenSDK.enableP2P(true);
        EZOpenSDK.initLib(this.cordova.getActivity().getApplication(), appkey, "");
    }
}
