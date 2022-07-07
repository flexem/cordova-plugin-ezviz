var exec = require('cordova/exec');

exports.openCamera = function (appkey, accessToken, deviceSerial, cameraNo,verifyCode, success, error) {
    exec(success, error, 'Ezviz', 'openCamera', [appkey, accessToken, deviceSerial, cameraNo,verifyCode]);
};
