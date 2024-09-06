#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(PosthogPlugin, "Posthog",
           CAP_PLUGIN_METHOD(alias, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(capture, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(flush, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(identify, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(register, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(reset, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(screen, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setup, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(unregister, CAPPluginReturnPromise);
)
