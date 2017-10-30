# 海康萤石的phonegap/cordova 插件


## Using

Install the plugin
    $ cordova plugin add https://github.com/yoli799480165/cordova-plugin-ezviz.git --variable APP_KEY="Your APPKEY"


Ionic2 usage

Create a typing file: ezviz.d.ts

```js
declare var ezviz: any;
```

In your page file:
```js
ezviz.preview('your token', 'your camera sn', 0, '', '', '', () => {
        console.log('success');
    }, () => {
        console.log('error');
    });
```

Install iOS or Android platform

    cordova platform add android

Run the code

    cordova run android --device
