package ezviz.common;

import com.google.gson.Gson;

public class SdkInitParams {

    public String appKey;
    public String accessToken;
    public int serverAreaId;
    public String openApiServer;
    public String openAuthApiServer;
    public String specifiedDevice;
    public boolean usingGlobalSDK;

    public  int cameraNo;
    public  String verifyCode;

    private SdkInitParams(){}

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static SdkInitParams createBy(){
        SdkInitParams sdkInitParams = new SdkInitParams();
        sdkInitParams.appKey = "02f602dad2084840b081af22394d6aac";
        sdkInitParams.serverAreaId = 0;
        sdkInitParams.openApiServer = "https://open.ys7.com";
        sdkInitParams.openAuthApiServer = "https://openauth.ys7.com";
        sdkInitParams.usingGlobalSDK = false;
        sdkInitParams.accessToken = "at.cw95ou4f9zn0zajg5alf99go078vzxp8-5tq3qx9y7n-0836hlz-bfhrcy1cy";
        sdkInitParams.specifiedDevice = "591200260";
        return sdkInitParams;
    }
    public static SdkInitParams createBy(String appKey,String accessToken,String deviceNo,int cameraNo,String verifyCode){
        SdkInitParams sdkInitParams = new SdkInitParams();
        sdkInitParams.appKey = appKey;
        sdkInitParams.serverAreaId = 0;
        sdkInitParams.openApiServer = "https://open.ys7.com";
        sdkInitParams.openAuthApiServer = "https://openauth.ys7.com";
        sdkInitParams.usingGlobalSDK = false;
        sdkInitParams.accessToken = accessToken;
        sdkInitParams.specifiedDevice = deviceNo;
        sdkInitParams.cameraNo = cameraNo;
        sdkInitParams.verifyCode = verifyCode;
        return sdkInitParams;
    }

}
