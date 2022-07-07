package org.apache.cordova.ezviz;

import android.widget.Toast;

import com.videogo.exception.BaseException;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.ui.realplay.EZRealPlayActivity;
import com.videogo.ui.util.EZUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import ezviz.common.SdkInitParams;
import ezviz.common.SdkInitTool;
import flexem.fbox.assistant.R;

/**
 * This class echoes a string called from JavaScript.
 */
public class Ezviz extends CordovaPlugin {

    public SdkInitParams mInitParams;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("openCamera")) {
            String appkey = args.getString(0);
            String accessToken = args.getString(1);
            String deviceSerial = args.getString(2);
            int cameraNo = args.getInt(3);
            String verifyCode = args.getString(4);
            this.openPlayer(appkey, accessToken, deviceSerial, cameraNo, verifyCode, callbackContext);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closePlayer();
    }

    private void openPlayer(String appkey, String accessToken, String deviceSerial, int cameraNo, String verifyCode,
            CallbackContext callbackContext) {
        try {
            try {
//                Intent intent = new Intent(cordova.getActivity(), MainActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("appkey", appkey);
//                bundle.putString("accessToken", accessToken);
//                bundle.putString("deviceSerial", deviceSerial);
//                bundle.putInt("cameraNo", cameraNo);
//                bundle.putString("verifyCode", verifyCode);
//                intent.putExtras(bundle);
//                cordova.startActivityForResult(Ezviz.this, intent, 100);
                startPlayer(appkey,accessToken,deviceSerial,cameraNo,verifyCode);
                callbackContext.success("");
            } catch (Exception e) {
                e.printStackTrace();
                ToastShow("open player run failed: " + e.toString());
                callbackContext.error("open player run failed: " + e.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            callbackContext.error("open player failed: " + e.toString());
            ToastShow("open player failed");
        }
    }

    public void startPlayer(String appKey,String accessToken,String deviceNo,int cameraNo,String verifyCode) {
        mInitParams = SdkInitParams.createBy(appKey,accessToken,deviceNo,cameraNo,verifyCode);
        SdkInitTool.initSdk(cordova.getActivity().getApplication(), mInitParams);

        jumpToCameraActivity();
    }
    private void jumpToCameraActivity() {
        try {
            EZDeviceInfo deviceInfo = SdkInitTool.getOpenSDK().getDeviceInfo(mInitParams.specifiedDevice);
            EZCameraInfo cameraInfo = EZUtils.getCameraInfoFromDevice(deviceInfo, 0);
            EZRealPlayActivity.launch(cordova.getContext(),deviceInfo,cameraInfo);
        } catch (BaseException e) {
            e.printStackTrace();
            int errCode = e.getErrorCode();
            String errMsg;
            switch (errCode) {
                case 400031:
                    errMsg = cordova.getActivity().getApplicationContext().getString(R.string.tip_of_bad_net);
                    break;
                default:
                    errMsg = cordova.getActivity().getApplicationContext().getString(R.string.login_expire);
                    break;
            }
            ToastShow(errMsg);
        }
    }

    private void closePlayer() {
        ToastShow("open player closed");
    }

    private void ToastShow(String msg) {
        Toast.makeText(this.cordova.getActivity(), msg, Toast.LENGTH_LONG).show();
    }

}
