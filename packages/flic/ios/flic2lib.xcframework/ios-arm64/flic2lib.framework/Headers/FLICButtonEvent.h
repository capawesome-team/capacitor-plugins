//
//  FLICButtonEvent.h
//  flic2lib
//
//  Created by Oskar Öberg on 2025-06-09.
//  Copyright © 2025 Shortcut Labs. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(uint8_t, FLICButtonEventClass) {
	FLICButtonEventClassUpOrDown,
	FLICButtonEventClassClickOrHold,
	FLICButtonEventClassSingleOrDoubleClick,
	FLICButtonEventClassSingleOrDoubleClickOrHold
};

typedef NS_ENUM(uint8_t, FLICButtonEventType) {
	FLICButtonEventTypeUp,
	FLICButtonEventTypeDown,
	FLICButtonEventTypeClick,
	FLICButtonEventTypeSingleClick,
	FLICButtonEventTypeDoubleClick,
	FLICButtonEventTypeHold
};

typedef NS_ENUM(int8_t, FLICButtonEventGesture) {
	FLICButtonEventGestureNoGesture = -1,
	FLICButtonEventGestureUnrecognizedGesture = 0,
	FLICButtonEventGestureLeft = 1,
	FLICButtonEventGestureRight = 2,
	FLICButtonEventGestureUp = 3,
	FLICButtonEventGestureDown = 4
};

/// Button event class.
///
/// Each time the button is interacted with, one or more events will be sent.
///
/// Usually an application only needs to listen to one event class.
///
/// Since distinguishing between single and double click needs some waiting time after the first
/// click to detect if a second press will occur or not, single click events will be delayed for the
/// last two event classes but not for the first two, it is important to pick the right event class
/// for the use case.
///
/// For a particular event class, only the specified button event types may be emitted.
///
/// See convenience methods to easy filter out the event class and types that are relevant.
@interface FLICButtonEvent : NSObject

///Button event class.
///
///Each time the button is interacted with, one or more events will be sent.
///
///Usually an application only needs to listen to one event class.
///
///Since distinguishing between single and double click needs some waiting time after the first
///click to detect if a second press will occur or not, single click events will be delayed for the
///last two event classes but not for the first two, it is important to pick the right event class
///for the use case.
///
///For a particular event class, only the specified button event types may be emitted.
@property (nonatomic, readonly) FLICButtonEventClass eventClass;

///Button event type within the specified event class.
///
///For FLICButtonEventClassUpOrDown, event type will be either FLICButtonEventTypeUp or FLICButtonEventTypeDown.
///
///For FLICButtonEventClassClickOrHold, event type will be either FLICButtonEventTypeUpClick or FLICButtonEventTypeHold.
///
///For FLICButtonEventClassSingleOrDoubleClick, event type will be either FLICButtonEventTypeUpSingleClick or FLICButtonEventTypeDoubleClick.
///
///For FLICButtonEventClassSingleOrDoubleClickOrHold, event type will be either FLICButtonEventTypeSingleClick, FLICButtonEventTypeDoubleClick or FLICButtonEventTypeHold.
@property (nonatomic, readonly) FLICButtonEventType type;

/// An event counter that starts at zero when the Flic 2 boots and always increases.
///
/// This value divided by four indicates roughly how many times the button has been pressed and released.
///
/// More specific, event_count mod 4 should be 1: down, 2: hold, 3: up, 0: single click timeout.
@property (nonatomic, readonly) uint32_t eventCount;

/// Which physical button was pressed.
///
/// For Flic Duo, it can be 0 or 1. For Flic 2, always 0.
@property (nonatomic, readonly) uint8_t buttonNumber;

/// Indicates if this button event was queued, i.e. it was pressed some time ago before connection setup completed.
@property (nonatomic, readonly) BOOL wasQueued;

/// If this event was queued, this value contains the age of the event, in seconds. If this event was not queued, the value will be zero.
@property (nonatomic, readonly) double age;

/// The X-axis accelerometer value at the time of the event.
@property (nonatomic, readonly) float x;

/// The Y-axis accelerometer value at the time of the event.
@property (nonatomic, readonly) float y;

/// The Z-axis accelerometer value at the time of the event.
@property (nonatomic, readonly) float z;

/// Gesture, -1 if no gesture.
@property (nonatomic, readonly) FLICButtonEventGesture gesture;

/// Button was held down at least 0.5 seconds.
@property (nonatomic, readonly) BOOL downAtLeastHalfASecond;

/// Convenience method.
/// The handler is called if the event is a ButtonDown event.
/// Use this for raw, low-latency interactions.
///
/// - Parameter handler: The block to execute, containing the button number.
- (void)isButtonDown:(void (NS_NOESCAPE ^)(uint8_t buttonNumber))handler
	NS_SWIFT_NAME(isButtonDown(_:));

/// Convenience method.
/// The handler is called if the event is a ButtonUp event.
/// Use this for raw, low-latency interactions.
///
/// - Parameter handler: The block to execute, containing the button number.
- (void)isButtonUp:(void (NS_NOESCAPE ^)(uint8_t buttonNumber))handler
	NS_SWIFT_NAME(isButtonUp(_:));

- (void)isButtonUpOrDown:(void (NS_NOESCAPE ^)(FLICButtonEventType type, uint8_t buttonNumber))handler NS_SWIFT_NAME(isButtonUpOrDown(_:));

/// Convenience method.
/// The handler is called if the event is a swipe gesture (excluding NoGesture or Unrecognized).
/// This is evaluated upon the button release (Up) event.
///
/// - Parameter handler: The block to execute, containing the specific gesture type and button number.
- (void)isGesture:(void (NS_NOESCAPE ^)(FLICButtonEventGesture gesture, uint8_t buttonNumber))handler
	NS_SWIFT_NAME(isGesture(_:));

/// Convenience method.
/// The handler is called if the event is a Click or Hold event.
///
/// Use this method if you do not need to support Double Clicks.
/// This is faster than `isSingleOrDoubleClick`. Because it does does not need
/// to wait/listen for a potential second click, "Click" events are fired
/// immediately upon release.
///
/// - Parameter handler: The block to execute, containing the event type (Click or Hold) and button number.
- (void)isClickOrHold:(void (NS_NOESCAPE ^)(FLICButtonEventType eventType, uint8_t buttonNumber))handler
	NS_SWIFT_NAME(isClickOrHold(_:));

/// Convenience method.
/// The handler is called if the event is a Click or Double Click event.
///
/// Use this method if your application requires distinguishing between one and two clicks.
/// "Single Click" events will be slightly delayed compared to a standard "Click" since
/// the system must wait for a short timeout period after the first release to ensure
/// the user is not attempting a Double Click.
///
/// - Parameter handler: The block to execute, containing the event type (SingleClick or DoubleClick) and button number.
- (void)isSingleOrDoubleClick:(void (NS_NOESCAPE ^)(FLICButtonEventType eventType, uint8_t buttonNumber))handler
	NS_SWIFT_NAME(isSingleOrDoubleClick(_:));

/// Convenience method.
/// The handler is called if the event is a Click or Double Click event.
///
/// This handles the widest variety of user inputs but incurs the latency trade-offs
/// associated with double-click detection.
/// Like `isSingleOrDoubleClick`, reporting a "Single Click" requires waiting for a
/// timeout to rule out a Double Click. "Hold" events are generally reported as soon
/// as the hold threshold is met.
///
/// - Parameter handler: The block to execute, containing the event type and button number.
- (void)isSingleOrDoubleClickOrHold:(void (NS_NOESCAPE ^)(FLICButtonEventType eventType, uint8_t buttonNumber))handler
	NS_SWIFT_NAME(isSingleOrDoubleClickOrHold(_:));
@end


NS_ASSUME_NONNULL_END
