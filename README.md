# 海康萤石的cordova 插件


## Using

Add the plugin
```bash
cordova plugin add cordova-plugin-ezviz --variable APP_KEY="Your APPKEY"
```

Ionic2 usage

Create a typing file: ezviz.d.ts

```js
declare var ezviz: any;
```

In your page file:
```js
ezviz.openCamera('appkey','accesstoken', 'sn', 0).then((ok) => {
    console.log(ok);
}, (error) => {
    console.log(error); 
});
```

Install iOS or Android platform
```
cordova platform add android
```

Run the code
```
cordova run android --device
```