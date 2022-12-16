package ezviz.common;

import android.app.Application;
import androidx.annotation.NonNull;

import com.videogo.openapi.EZGlobalSDK;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.ui.util.DataManager;

public class SdkInitTool {

    public static void initSdk(@NonNull Application application, @NonNull SdkInitParams sdkInitParams) {
        // sdk日志开关，正式发布需要去掉
        EZOpenSDK.showSDKLog(false);
        // 设置是否支持P2P取流,详见api
        EZOpenSDK.enableP2P(true);
        // APP_KEY请替换成自己申请的
        EZOpenSDK.initLib(application, sdkInitParams.appKey);
        
        DataManager.getInstance().setDeviceSerialVerifyCode(sdkInitParams.specifiedDevice, sdkInitParams.verifyCode);
        EZOpenSDK ezvizSDK = getOpenSDK();
        if (sdkInitParams.accessToken != null) {
            ezvizSDK.setAccessToken(sdkInitParams.accessToken);
        }
        if (sdkInitParams.openApiServer != null && sdkInitParams.openAuthApiServer != null) {
            ezvizSDK.setServerUrl(sdkInitParams.openApiServer, sdkInitParams.openAuthApiServer);
        }
    }

    public static EZOpenSDK getOpenSDK(){
        EZOpenSDK ezOpenSDK = EZOpenSDK.getInstance();
        return ezOpenSDK;
    }
}
