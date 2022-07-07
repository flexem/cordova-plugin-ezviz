package ezviz.common;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * 用于demo的SharedPreference工具类
 */
public class SpTool {

    private final static String TAG = SpTool.class.getSimpleName();
    private static SharedPreferences mSP = null;

    /**
     * 存
     */
    public static String obtainValue(String key){
        if (mSP == null){
            Log.e(TAG, "SpTool is not init!!!");
            return null;
        }
        return mSP.getString(key, null);
    }

    /**
     * 安全Kye取
     */
    public static void storeValue(String key, String value){
        if (mSP == null){
            Log.e(TAG, "SpTool is not init!!!");
            return;
        }
        mSP.edit().putString(key, value).apply();
    }

}
