cmake_minimum_required(VERSION 3.22.1)

project(capacitor-nodejs)

if(NOT DEFINED LIBNODE_DIR)
    message(FATAL_ERROR "LIBNODE_DIR must be defined.")
endif()

add_library(capacitor-nodejs
            SHARED
            src/main/cpp/native-lib.cpp
            ../ios/PluginNative/bridge.cpp)

include_directories(${LIBNODE_DIR}/include/node)
include_directories(../ios/PluginNative)

add_library(libnode
            SHARED
            IMPORTED)

set_target_properties(libnode
                      PROPERTIES IMPORTED_LOCATION
                      ${LIBNODE_DIR}/bin/${ANDROID_ABI}/libnode.so)

find_library(log-lib log)

# Support devices with a 16 KB page size.
target_link_options(capacitor-nodejs PRIVATE "-Wl,-z,max-page-size=16384")

target_link_libraries(capacitor-nodejs
                      libnode
                      ${log-lib})
