#!/usr/bin/env node

const path = require('path');
const fs = require('fs');
const semver = require('semver');
const util = require('util');
const stat = util.promisify(fs.stat);
const pluginName = 'cordova-plugin-ezviz';

//正则表达式替换文本
function replace_string_in_file(filename, to_replace, replace_with) {
    var data = fs.readFileSync(filename, 'utf8');
    var result = data.replace(to_replace, replace_with);
    fs.writeFileSync(filename, result, 'utf8');
}

module.exports = function (context) {
    // Make sure android platform is part of build
    if (!context.opts.platforms.includes('android')) return;
    
    const projectRoot = context.opts.projectRoot;
    const platformRoot = path.join(projectRoot, 'platforms/android');
    const plugins = context.opts.plugins || [];

    // The plugins array will be empty during platform add
    if (plugins.length > 0 && plugins.indexOf(pluginName) === -1) {
        return ;
    }
    
    let ConfigParser = null;
    try {
        ConfigParser = context.requireCordovaModule('cordova-common').ConfigParser;
    } catch(e) {
        // fallback
        ConfigParser = context.requireCordovaModule('cordova-lib/src/configparser/ConfigParser');
    }

    const config = new ConfigParser(path.join(projectRoot, "config.xml"));
    let packageName = config.android_packageName() || config.packageName();

    // replace dash (-) with underscore (_)
    packageName = packageName.replace(/-/g , "_");
    
    console.info("Running android-install.Hook: " + context.hook + ", Package: " + packageName + ", Path: " + projectRoot + ".");

    if (!packageName) {
        console.error("Package name could not be found!");
        return ;
    }

    // android platform available?
    if (context.opts.cordova.platforms.indexOf("android") === -1) {
        console.info("Android platform has not been added.");
        return ;
    }

    var targetDir  = path.join(platformRoot, "app", "src", "main", "java");

 	// if (!fs.existsSync(targetDir)) {
	// 	targetDir  = path.join(platformRoot, "app", "src", "main", "java", packageName.replace(/\./g, path.sep), "ezviz");
	// } 

    // var engines =  config.getEngines();

    console.log(targetDir);

    var targetFiles = [
        path.join(targetDir, 'org', 'apache', 'cordova', "ezviz", 'Ezviz.java'), 
        path.join(targetDir, 'ezviz', 'common', 'RootActivity.java'), 
        path.join(targetDir, 'com', 'videogo', 'ui', 'realplay', 'EZRealPlayActivity.java'), 
        path.join(targetDir, 'com', 'videogo', 'ui', 'util', 'AudioPlayUtil.java'), 
        path.join(targetDir, 'com', 'videogo', 'ui', 'util', 'EZUtils.java'), 
        path.join(targetDir, 'com', 'videogo', 'ui', 'util', 'VerifyCodeInput.java'), 
        path.join(targetDir, 'com', 'videogo', 'widget', 'loading', 'LoadingTextView.java'), 
        path.join(targetDir, 'com', 'videogo', 'widget', 'loading', 'LoadingView.java'), 
        path.join(targetDir, 'com', 'videogo', 'widget', 'PtzControlAngleView.java'), 
        path.join(targetDir, 'com', 'videogo', 'widget', 'WaitDialog.java')
    ];

    if (['before_prepare', 'before_compile'].indexOf(context.hook) > -1) {
        targetFiles.forEach(function (f) {
            fs.readFile(f, {encoding: 'utf-8'}, function (err, data) {
                if (err) {
                    throw err;
                }

                data = data.replace(/^import __PACKAGE_NAME__/m, 'import ' + packageName);
                fs.writeFileSync(f, data);
            });
        });
    }
};
