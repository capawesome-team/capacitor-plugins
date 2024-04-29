#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(LiveUpdatePlugin, "LiveUpdate",
           CAP_PLUGIN_METHOD(deleteBundle, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(downloadBundle, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getBundle, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getBundles, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getChannel, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getDeviceId, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getCustomId, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getVersionCode, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getVersionName, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(ready, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(reload, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(reset, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setBundle, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setChannel, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setCustomId, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(sync, CAPPluginReturnPromise);
)
