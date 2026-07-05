#import <Foundation/Foundation.h>

typedef void (^NodeRunnerMessageHandler)(NSString *_Nonnull channelName, NSString *_Nonnull message);

@interface NodeRunner : NSObject

+ (void)registerDataDirPath:(NSString *_Nonnull)path;
+ (void)sendMessage:(NSString *_Nonnull)channelName message:(NSString *_Nonnull)message;
+ (void)setMessageHandler:(NodeRunnerMessageHandler _Nonnull)handler;
+ (void)startEngineWithArguments:(NSArray<NSString *> *_Nonnull)arguments nodePath:(NSString *_Nonnull)nodePath;

@end
