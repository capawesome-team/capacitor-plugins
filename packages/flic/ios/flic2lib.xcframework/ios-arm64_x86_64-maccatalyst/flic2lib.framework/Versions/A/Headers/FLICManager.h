//
//  FLICManager.h
//  flic2lib
//
//  Created by Anton Meier on 2019-04-11.
//  Copyright © 2020 Shortcut Labs. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreBluetooth/CoreBluetooth.h>
#import <flic2lib/FLICButton.h>

NS_ASSUME_NONNULL_BEGIN

@protocol FLICManagerDelegate;

/// This interface is intended to be used as a singleton. It keeps track of all the buttons that have been paired with this particular app and restores them on each
/// application launch.
@interface FLICManager : NSObject

/// The delegate that all the FLICManagerDelegate events will be sent to. Usually this is configured only once when the application is first launched, using the
/// configureWithDelegate:buttonDelegate:background: method.
@property(weak, nonatomic, nullable) id<FLICManagerDelegate> delegate;

/// Once set, this delegate will automatically be set to every button instance on each application launch. This will also be assigned to new buttons that are discovered
/// using the scanForButtonsWithStateChangeHandler:completionHandler method. Using this delegate also ensures that the click events are delivered as fast as possible
/// when an application is restored in the background.
@property(weak, nonatomic, nullable) id<FLICButtonDelegate> buttonDelegate;

/// This is the state of the Flic manager. This state closely resembles the CBManager state of Apple's CoreBluetooth framework. You will only be able to communicate with
/// Flic buttons while the manager is in the FlicManagerStatePoweredOn state.
@property(readonly) FLICManagerState state;

/// Let's you know if a scan is currently running or not. Reading this value will not affect a scan.
@property(nonatomic, readonly) BOOL isScanning;

/// This class method return the singleton manager, assuming that it has been configured first using the configureWithDelegate:buttonDelegate:background: method first,
/// otherwise nil is returned.
+ (instancetype _Nullable)sharedManager;

/// This configuration method must be called before the manager can be used. It is recommended that this is done as soon as possible after your application has launched
/// in order to minimize the delay of any pending click events. The flic2lib officially only support iOS 12 and up, however, to make it easier for the developer, the framework
/// is built with a target of iOS 9 and contains slices for both arm64 and armv7. This means that you will be able to include, and load, the framework in apps that support iOS 9.
/// However, if you try to configure the manager on a device running iOS 11, or below, then the manager state will switch to FLICManagerStateUnsupported. A good place
/// to handle this would be in the manager:didUpdateSate: method.
///
/// - Parameter delegate: The delegate to be used with the manager singleton.
/// - Parameter buttonDelegate: The delegate to be automatically assigned to each FLICButton instance.
/// - Parameter background: Whether or not you intend to use this application in the background.
+ (instancetype _Nullable)configureWithDelegate:(id<FLICManagerDelegate> _Nullable)delegate
								 buttonDelegate:(id<FLICButtonDelegate> _Nullable)buttonDelegate
									 background:(BOOL)background;

/// This array will contain every button that is currently paired with this application. Each button will be added to the list after a call to
/// scanForButtonsWithStateChangeHandler:completionHandler: has completed without error. It is important to know that you may not try to access this list until after the
/// managerDidRestoreState: method has been sent to the manager delegate.
- (NSArray<FLICButton *> *)buttons;

/// This will delete this button from the manager. After a successful call to this method you will no longer be able to communicate with the associated Flic button unless you
/// pair it again using the scanForButtonsWithStateChangeHandler:completionHandler:. On completion, the button will no longer be included in the manager's buttons array.
/// After a successful call to this method, you should discard of any references to that particular Flic button object. If you try to forget a button that is already forgotten, then
/// you will get an error with the FLICErrorAlreadyForgotten code.
///
/// - Parameter button: The button that you wish to delete from the manager.
/// - Parameter completion: This returns the identifier of the button instance that was just removed along with an error, if needed.
- (void)forgetButton:(FLICButton *)button completion:(void (^)(NSUUID *uuid, NSError * _Nullable error))completion;

/// This method lets you add new Flic buttons to the manager. The scan flow is automated and the stateHandler will let you know what information you should provide to
/// your application end user. If you try to scan for buttons while running on an iOS version below the mimimun requirement, then you will get an error with the
/// FLICErrorUnsupportedOSVersion error code.
///
/// - Parameter stateHandler: This handler returns status events that lets you know what the scanner is currently doing. The purpose of this handler is to provide a predefined states where you may update your application UI.
/// - Parameter completion: The completion handler will always run and if successful it will return a new FLICButton instance, otherwise it will provide you with an error.
- (void)scanForButtonsWithStateChangeHandler:(void (^)(FLICButtonScannerStatusEvent event))stateHandler
								  completion:(void (^)(FLICButton * _Nullable button, NSError * _Nullable error))completion;

/// Cancel an ongoing button scan. This will result in a scan completion with an error.
- (void)stopScan;

@end

/// The delegate of a FLICManager instance must adopt the FLICManagerDelegate protocol. All calls to the delegate methods will be on the main dispatch queue.
@protocol FLICManagerDelegate <NSObject>

/// This is called once the manager has been restored. This means that all the FLICButton instances from your previous session are restored as well. After this method
/// has been called you may start using the manager and communicate with the Flic buttons. This method will only be called once on each application launch.
///
/// - Parameter manager: The manager instance that the event originates from. Since this is intended to be used as a singleton, there should only ever be one manager instance.
- (void)managerDidRestoreState:(FLICManager *)manager;

/// Each time the state of the Flic manager changes, you will get this callback. Usually this is related to bluetooth state changes on the iOS device. It is also guaranteed to
/// be called at least once after the manager has been configured.
///
/// - Parameter manager: The manager instance that the event originates from. Since this is intended to be used as a singleton, there should only ever be one manager instance.
/// - Parameter state: The state of the Flic manager singleton.
- (void)manager:(FLICManager *)manager didUpdateState:(FLICManagerState)state;

@end

NS_ASSUME_NONNULL_END
