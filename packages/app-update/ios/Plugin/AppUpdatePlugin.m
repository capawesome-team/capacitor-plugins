#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(AppUpdatePlugin, "AppUpdate",
           CAP_PLUGIN_METHOD(getAppUpdateInfo, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(openAppStore, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(performImmediateUpdate, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(startFlexibleUpdate, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(completeFlexibleUpdate, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(removeAllListeners, CAPPluginReturnNone);
)
