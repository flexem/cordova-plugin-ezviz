package ezviz.common;

import android.app.Application;

public class Config {

    // 日志开关
    public static boolean LOGGING = true;

    public static String getRecordsFolder(Application application){
        return application.getExternalCacheDir() + "/0_OpenSDK/Records";
    }

    public static String getCapturesFolder(Application application){
        return application.getExternalCacheDir() + "/0_OpenSDK/Captures";
    }
}
