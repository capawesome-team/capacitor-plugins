#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(BackgroundTaskPlugin, "BackgroundTask",
           CAP_PLUGIN_METHOD(beforeExit, CAPPluginReturnCallback);
           CAP_PLUGIN_METHOD(finish, CAPPluginReturnNone);
)
