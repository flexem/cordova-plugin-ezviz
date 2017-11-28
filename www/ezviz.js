/*global cordova, module*/

module.exports = {
    openCamera: function (appkey, accessToken, deviceSerial, cameraIndex) {
        var promise = new Promise(function (resolve, reject) {
            cordova.exec(function (resp) {
                    resolve(resp);
                },
                function (error) {
                    reject(error);
                },
                "ezviz",
                "openCamera", [appkey || "", accessToken || "", deviceSerial, cameraIndex || 0]);
        });
        return promise;
    }
};