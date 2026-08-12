/*
  Bridge APIs between the plugin's native layer and the Node.js engine.

  Based on the bridge of the `nodejs-mobile-react-native` project
  (MIT licensed, https://github.com/nodejs-mobile/nodejs-mobile-react-native).
*/
#ifndef CAPACITOR_NODEJS_BRIDGE_H_
#define CAPACITOR_NODEJS_BRIDGE_H_

typedef void (*capacitor_bridge_cb)(const char* channelName, const char* message);
void capacitor_register_bridge_cb(capacitor_bridge_cb cb);
void capacitor_bridge_notify(const char* channelName, const char* message);
void capacitor_register_node_data_dir_path(const char* path);

#endif // CAPACITOR_NODEJS_BRIDGE_H_
