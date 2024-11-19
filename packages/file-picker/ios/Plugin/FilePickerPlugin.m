#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(FilePickerPlugin, "FilePicker",
           CAP_PLUGIN_METHOD(convertHeicToJpeg, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(pickFiles, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(pickImages, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(pickMedia, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(pickVideos, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(pickDirectory, CAPPluginReturnPromise);
)
