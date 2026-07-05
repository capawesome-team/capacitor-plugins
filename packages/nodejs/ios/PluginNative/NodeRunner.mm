/*
  Runs the Node.js engine and handles the system channel messages.

  Based on the `NodeRunner` of the `nodejs-mobile-react-native` project
  (MIT licensed, https://github.com/nodejs-mobile/nodejs-mobile-react-native).
*/
#import <UIKit/UIKit.h>
#import "NodeRunner.h"
#include <NodeMobile/NodeMobile.h>
#include <string>
#include "bridge.h"

static NSString *const SYSTEM_CHANNEL = @"_SYSTEM_";

static NodeRunnerMessageHandler _messageHandler = nil;

// Flag to indicate if the Node.js engine is ready to receive app events.
static bool nodeIsReadyForAppEvents = false;

// Condition to wait on pause event handling on the Node.js side.
static NSCondition *appEventBeingProcessedCondition = nil;

// Set to keep ids for called pause events, so they can be unlocked later.
static NSMutableSet *appPauseEventsManagerSet = nil;

// Lock to manipulate the app pause events manager set.
static id appPauseEventsManagerSetLock = nil;

@interface NodeRunner ()

+ (void)handleSystemChannelMessage:(NSString *)msg;
+ (void)onPause;
+ (void)onResume;
+ (void)releasePauseEvent:(NSString *)eventId;
+ (void)sendPauseEventAndWaitForRelease:(NSDate *)expectedFinishTime;

@end

static void rcv_message(const char *channelName, const char *msg) {
    @autoreleasepool {
        NSString *objectiveCChannelName = [NSString stringWithUTF8String:channelName];
        NSString *objectiveCMessage = [NSString stringWithUTF8String:msg];

        if ([objectiveCChannelName isEqualToString:SYSTEM_CHANNEL]) {
            [NodeRunner handleSystemChannelMessage:objectiveCMessage];
        }
        if (_messageHandler) {
            _messageHandler(objectiveCChannelName, objectiveCMessage);
        }
    }
}

@implementation NodeRunner

+ (void)registerDataDirPath:(NSString *)path {
    capacitor_register_node_data_dir_path([path UTF8String]);
}

+ (void)sendMessage:(NSString *)channelName message:(NSString *)message {
    capacitor_bridge_notify([channelName UTF8String], [message UTF8String]);
}

+ (void)setMessageHandler:(NodeRunnerMessageHandler)handler {
    _messageHandler = handler;
}

+ (void)handleSystemChannelMessage:(NSString *)msg {
    if ([msg hasPrefix:@"release-pause-event"]) {
        // The Node.js engine has signaled it has finished handling a pause event.
        // The expected format for this message is "release-pause-event|{eventId}".
        NSArray *eventArguments = [msg componentsSeparatedByString:@"|"];
        if (eventArguments.count >= 2) {
            [NodeRunner releasePauseEvent:eventArguments[1]];
        }
    } else if ([msg isEqualToString:@"ready-for-app-events"]) {
        nodeIsReadyForAppEvents = true;
    }
}

+ (void)onPause {
    if (!nodeIsReadyForAppEvents) {
        return;
    }
    UIApplication *application = [UIApplication sharedApplication];
    // Inform the app intends to run something in the background. In this
    // case we'll try to wait for the pause event to be properly taken
    // care of by the Node.js engine.
    __block UIBackgroundTaskIdentifier backgroundWaitForPauseHandlerTask = [application beginBackgroundTaskWithExpirationHandler:^{
        // Expiration handler to avoid app crashes if the task doesn't end
        // in the iOS allowed background duration time.
        [application endBackgroundTask:backgroundWaitForPauseHandlerTask];
        backgroundWaitForPauseHandlerTask = UIBackgroundTaskInvalid;
    }];

    NSTimeInterval intendedMaxDuration = [application backgroundTimeRemaining] + 1;
    // Calls the event in a background thread, to let the
    // UIApplicationDidEnterBackgroundNotification return as soon as possible.
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        NSDate *targetMaximumFinishTime = [[NSDate date] dateByAddingTimeInterval:intendedMaxDuration];
        [NodeRunner sendPauseEventAndWaitForRelease:targetMaximumFinishTime];
        [application endBackgroundTask:backgroundWaitForPauseHandlerTask];
        backgroundWaitForPauseHandlerTask = UIBackgroundTaskInvalid;
    });
}

+ (void)onResume {
    if (!nodeIsReadyForAppEvents) {
        return;
    }
    [NodeRunner sendMessage:SYSTEM_CHANNEL message:@"resume"];
}

// Sends the pause event to the Node.js engine and returns only after the
// engine signals the event has been handled explicitly or the background
// time is running out.
+ (void)sendPauseEventAndWaitForRelease:(NSDate *)expectedFinishTime {
    NSString *eventId = [[NSUUID UUID] UUIDString];
    NSString *event = [NSString stringWithFormat:@"pause|%@", eventId];

    [appEventBeingProcessedCondition lock];

    @synchronized(appPauseEventsManagerSetLock) {
        [appPauseEventsManagerSet addObject:eventId];
    }

    [NodeRunner sendMessage:SYSTEM_CHANNEL message:event];

    while (YES) {
        // Looping to avoid unintended spurious wake ups.
        @synchronized(appPauseEventsManagerSetLock) {
            if (![appPauseEventsManagerSet containsObject:eventId]) {
                // The id for this event has been released.
                break;
            }
        }
        if ([expectedFinishTime timeIntervalSinceNow] <= 0) {
            // We blocked the background thread long enough.
            break;
        }
        [appEventBeingProcessedCondition waitUntilDate:expectedFinishTime];
    }
    [appEventBeingProcessedCondition unlock];

    @synchronized(appPauseEventsManagerSetLock) {
        [appPauseEventsManagerSet removeObject:eventId];
    }
}

// Signals the pause event has been handled by the Node.js side.
+ (void)releasePauseEvent:(NSString *)eventId {
    [appEventBeingProcessedCondition lock];
    @synchronized(appPauseEventsManagerSetLock) {
        [appPauseEventsManagerSet removeObject:eventId];
    }
    [appEventBeingProcessedCondition broadcast];
    [appEventBeingProcessedCondition unlock];
}

// Node's libuv requires all arguments being on contiguous memory.
+ (void)startEngineWithArguments:(NSArray<NSString *> *)arguments nodePath:(NSString *)nodePath {
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        appEventBeingProcessedCondition = [[NSCondition alloc] init];
        appPauseEventsManagerSet = [[NSMutableSet alloc] init];
        appPauseEventsManagerSetLock = [[NSObject alloc] init];
        dispatch_async(dispatch_get_main_queue(), ^{
            [[NSNotificationCenter defaultCenter] addObserver:[NodeRunner class]
                                                     selector:@selector(onPause)
                                                         name:UIApplicationDidEnterBackgroundNotification
                                                       object:nil];
            [[NSNotificationCenter defaultCenter] addObserver:[NodeRunner class]
                                                     selector:@selector(onResume)
                                                         name:UIApplicationWillEnterForegroundNotification
                                                       object:nil];
        });
    });

    setenv("NODE_PATH", [nodePath UTF8String], 1);

    int c_arguments_size = 0;

    // Compute the byte size needed for all arguments in contiguous memory.
    for (id argElement in arguments) {
        c_arguments_size += strlen([argElement UTF8String]);
        c_arguments_size++; // for '\0'
    }

    // Store the arguments in contiguous memory.
    char *args_buffer = (char *)calloc(c_arguments_size, sizeof(char));

    // The argv to pass into Node.js.
    char *argv[[arguments count]];

    // Iterate through the expected start position of each argument in args_buffer.
    char *current_args_position = args_buffer;

    int argument_count = 0;

    // Populate the args_buffer and argv.
    for (id argElement in arguments) {
        const char *current_argument = [argElement UTF8String];

        // Copy the current argument to its expected position in args_buffer.
        strncpy(current_args_position, current_argument, strlen(current_argument));

        // Save the current argument start position in argv and increment argc.
        argv[argument_count] = current_args_position;
        argument_count++;

        // Increment to the next argument's expected position.
        current_args_position += strlen(current_args_position) + 1;
    }

    capacitor_register_bridge_cb(rcv_message);

    // Start Node.js, with argc and argv. This call blocks until the
    // Node.js event loop exits.
    node_start(argument_count, argv);
}

@end
