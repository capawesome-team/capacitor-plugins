import type { PluginListenerHandle } from '@capacitor/core';

/**
 * @since 0.0.1
 */
export interface FerrostarNavigationPlugin {
  /**
   * Start a navigation session for the first route of the given routing response.
   *
   * A running session is stopped first.
   * The plugin requests the location permission automatically if the location is not simulated.
   * On **Android**, it also requests it if `continueInBackground` is `true`, because the foreground service needs it.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  startNavigation(options: StartNavigationOptions): Promise<void>;
  /**
   * Replace the route of the running navigation session, e.g. after the app fetched a new route
   * because the user left the route.
   *
   * All other options of the session stay as they are.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  replaceRoute(options: ReplaceRouteOptions): Promise<void>;
  /**
   * Stop the running navigation session.
   *
   * Does nothing if no session is running.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  stopNavigation(): Promise<void>;
  /**
   * Mute the voice guidance of the running navigation session.
   *
   * The mute state lasts until the session is stopped.
   * Does nothing if no session is running.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  muteVoice(): Promise<void>;
  /**
   * Unmute the voice guidance of the running navigation session.
   *
   * The mute state lasts until the session is stopped.
   * Does nothing if no session is running.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  unmuteVoice(): Promise<void>;
  /**
   * Called when the navigation state changes, e.g. on every location update.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'navigationStateChanged',
    listenerFunc: (event: NavigationStateChangedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the user leaves the route.
   *
   * The plugin does not fetch a new route. Fetch one and call `replaceRoute(...)`.
   * The event is emitted once per deviation and again after the user returned to the route and left it again.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'routeDeviationDetected',
    listenerFunc: (event: RouteDeviationDetectedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the user arrived at the destination.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'navigationCompleted',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.0.1
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.0.1
 */
export interface StartNavigationOptions {
  /**
   * The routing response as JSON string in the OSRM format, which is also used by
   * Mapbox Directions, the GraphHopper `/navigate` endpoint and Valhalla with `format=osrm`.
   *
   * The first route of the response is used.
   *
   * @since 0.0.1
   * @example '{"code":"Ok","routes":[...],"waypoints":[...]}'
   */
  osrmResponseJson: string;
  /**
   * The precision of the encoded polyline in the `geometry` of the route.
   *
   * Use `6` for Valhalla, Stadia Maps, GraphHopper and Mapbox (`polyline6`)
   * and `5` for the classic OSRM `polyline` format.
   *
   * @since 0.0.1
   * @default 6
   * @example 6
   */
  polylinePrecision?: number;
  /**
   * Whether to simulate the location along the route instead of using the device location.
   *
   * Useful for development and testing.
   *
   * @since 0.0.1
   * @default false
   * @example true
   */
  simulateLocation?: boolean;
  /**
   * The integer factor by which the simulated location moves faster than in real time.
   *
   * Only used if `simulateLocation` is `true`.
   *
   * @since 0.0.1
   * @default 1
   * @example 4
   */
  simulationSpeedMultiplier?: number;
  /**
   * The distance to a waypoint in meters at which the waypoint counts as reached.
   *
   * @since 0.0.1
   * @default 100
   * @example 100
   */
  waypointAdvanceDistanceMeters?: number;
  /**
   * The distance to the end of the current step in meters that the user must reach
   * before the next step can become the current step.
   *
   * @since 0.0.1
   * @default 30
   * @example 30
   */
  stepAdvanceEntryDistanceMeters?: number;
  /**
   * The distance in meters that the user must move away from the current step
   * after reaching its end before the next step becomes the current step.
   *
   * This makes sure the user completes the maneuver before the next instruction is shown.
   *
   * @since 0.0.1
   * @default 5
   * @example 5
   */
  stepAdvanceExitDistanceMeters?: number;
  /**
   * The distance to the destination in meters at which the user counts as arrived.
   *
   * @since 0.0.1
   * @default 10
   * @example 10
   */
  arrivalStepAdvanceDistanceMeters?: number;
  /**
   * The distance from the route line in meters above which the user counts as off route.
   *
   * Set to `0` to disable route deviation tracking.
   *
   * @since 0.0.1
   * @default 50
   * @example 50
   */
  routeDeviationThresholdMeters?: number;
  /**
   * How the course of the snapped location is determined.
   *
   * @since 0.0.1
   * @default CourseFiltering.SnapToRoute
   * @example CourseFiltering.SnapToRoute
   */
  courseFiltering?: CourseFiltering;
  /**
   * Whether to speak the instructions with the text-to-speech engine of the device.
   *
   * @since 0.0.1
   * @default true
   * @example true
   */
  speakInstructions?: boolean;
  /**
   * Whether to start the session with muted voice guidance.
   *
   * Only used if `speakInstructions` is `true`.
   *
   * @since 0.0.1
   * @default false
   * @example false
   */
  muteVoice?: boolean;
  /**
   * The BCP 47 language tag of the voice.
   *
   * If not set, the default voice of the device is used.
   *
   * Only used if `speakInstructions` is `true`.
   *
   * @since 0.0.1
   * @example 'en-US'
   */
  voiceLanguage?: string;
  /**
   * Whether to continue the navigation while the app is in the background.
   *
   * On **Android**, this starts a foreground service with a persistent notification (see `androidNotification`).
   * The session must be started while the app is in the foreground, otherwise the call is rejected.
   *
   * On **iOS**, this requires the `location` background mode (see the installation instructions).
   *
   * @since 0.0.1
   * @default false
   * @example true
   */
  continueInBackground?: boolean;
  /**
   * The notification that is shown while the navigation continues in the background.
   *
   * Only used if `continueInBackground` is `true`.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  androidNotification?: AndroidNotification;
}

/**
 * @since 0.0.1
 */
export interface AndroidNotification {
  /**
   * The name of the notification channel.
   *
   * @since 0.0.1
   * @default 'Navigation'
   * @example 'Navigation'
   */
  channelName?: string;
  /**
   * The accent color of the notification as hex string.
   *
   * @since 0.0.1
   * @example '#ff0000'
   */
  color?: string;
  /**
   * The name of the drawable resource to use as the small icon of the notification.
   *
   * If not set, the launcher icon of the app is used.
   *
   * @since 0.0.1
   * @example 'ic_navigation'
   */
  icon?: string;
  /**
   * The text of the notification.
   *
   * If not set, the text of the current instruction is shown.
   *
   * @since 0.0.1
   * @example 'Navigation is running.'
   */
  text?: string;
  /**
   * The title of the notification.
   *
   * If not set, the name of the app is shown.
   *
   * @since 0.0.1
   * @example 'Navigation'
   */
  title?: string;
}

/**
 * @since 0.0.1
 */
export interface ReplaceRouteOptions {
  /**
   * The routing response as JSON string in the OSRM format.
   *
   * The first route of the response is used.
   *
   * @since 0.0.1
   * @example '{"code":"Ok","routes":[...],"waypoints":[...]}'
   */
  osrmResponseJson: string;
  /**
   * The precision of the encoded polyline in the `geometry` of the route.
   *
   * @since 0.0.1
   * @default 6
   * @example 6
   */
  polylinePrecision?: number;
}

/**
 * @since 0.0.1
 */
export interface NavigationStateChangedEvent {
  /**
   * The status of the navigation session.
   *
   * @since 0.0.1
   * @example NavigationStatus.Navigating
   */
  status: NavigationStatus;
  /**
   * The current instruction.
   *
   * `null` if no session is running or the current step has no instruction.
   *
   * @since 0.0.1
   */
  instruction: Instruction | null;
  /**
   * The distance to the next maneuver in meters.
   *
   * `null` if no session is running.
   *
   * @since 0.0.1
   * @example 120.5
   */
  distanceToNextManeuverMeters: number | null;
  /**
   * The remaining distance to the destination in meters.
   *
   * `null` if no session is running.
   *
   * @since 0.0.1
   * @example 4321.0
   */
  distanceRemainingMeters: number | null;
  /**
   * The remaining travel time to the destination in seconds.
   *
   * `null` if no session is running.
   *
   * @since 0.0.1
   * @example 600.0
   */
  durationRemainingSeconds: number | null;
  /**
   * The distance traveled along the route in meters.
   *
   * `null` if no session is running.
   *
   * @since 0.0.1
   * @example 1234.0
   */
  distanceTraveledMeters: number | null;
  /**
   * The location of the user snapped to the route.
   *
   * `null` if no location is known yet.
   *
   * @since 0.0.1
   */
  location: Location | null;
  /**
   * Whether the user is off route.
   *
   * @since 0.0.1
   * @example false
   */
  isOffRoute: boolean;
  /**
   * The text of the instruction that is spoken at the current location.
   *
   * `null` if nothing is spoken at the current location.
   *
   * @since 0.0.1
   * @example 'In 200 meters, turn right onto Main Street.'
   */
  spokenInstructionText: string | null;
}

/**
 * @since 0.0.1
 */
export interface Instruction {
  /**
   * The main text of the instruction.
   *
   * @since 0.0.1
   * @example 'Turn right onto Main Street'
   */
  text: string;
  /**
   * The secondary text of the instruction, e.g. the next maneuver.
   *
   * @since 0.0.1
   * @example 'Then turn left'
   */
  secondaryText: string | null;
  /**
   * The type of the maneuver.
   *
   * @since 0.0.1
   * @example ManeuverType.Turn
   */
  maneuverType: ManeuverType | null;
  /**
   * The direction of the maneuver.
   *
   * @since 0.0.1
   * @example ManeuverModifier.Right
   */
  maneuverModifier: ManeuverModifier | null;
}

/**
 * @since 0.0.1
 */
export interface Location {
  /**
   * The latitude in degrees.
   *
   * @since 0.0.1
   * @example 48.1351
   */
  latitude: number;
  /**
   * The longitude in degrees.
   *
   * @since 0.0.1
   * @example 11.582
   */
  longitude: number;
  /**
   * The course over ground in degrees clockwise from true north.
   *
   * `null` if the course is unknown.
   *
   * @since 0.0.1
   * @example 90
   */
  courseDegrees: number | null;
  /**
   * The horizontal accuracy in meters.
   *
   * @since 0.0.1
   * @example 5.0
   */
  horizontalAccuracyMeters: number;
}

/**
 * @since 0.0.1
 */
export interface RouteDeviationDetectedEvent {
  /**
   * The distance from the route line in meters.
   *
   * @since 0.0.1
   * @example 75.0
   */
  deviationMeters: number;
  /**
   * The raw location of the user.
   *
   * @since 0.0.1
   */
  location: Location;
}

/**
 * @since 0.0.1
 */
export enum CourseFiltering {
  /**
   * Use the raw course as reported by the location provider.
   *
   * @since 0.0.1
   */
  Raw = 'RAW',
  /**
   * Snap the course to the direction of the current step.
   *
   * @since 0.0.1
   */
  SnapToRoute = 'SNAP_TO_ROUTE',
}

/**
 * @since 0.0.1
 */
export enum NavigationStatus {
  /**
   * No session is running.
   *
   * @since 0.0.1
   */
  Idle = 'IDLE',
  /**
   * A session is running.
   *
   * @since 0.0.1
   */
  Navigating = 'NAVIGATING',
  /**
   * The user arrived at the destination.
   *
   * @since 0.0.1
   */
  Complete = 'COMPLETE',
}

/**
 * @since 0.0.1
 */
export enum ManeuverType {
  /**
   * @since 0.0.1
   */
  Turn = 'TURN',
  /**
   * @since 0.0.1
   */
  NewName = 'NEW_NAME',
  /**
   * @since 0.0.1
   */
  Depart = 'DEPART',
  /**
   * @since 0.0.1
   */
  Arrive = 'ARRIVE',
  /**
   * @since 0.0.1
   */
  Merge = 'MERGE',
  /**
   * @since 0.0.1
   */
  OnRamp = 'ON_RAMP',
  /**
   * @since 0.0.1
   */
  OffRamp = 'OFF_RAMP',
  /**
   * @since 0.0.1
   */
  Fork = 'FORK',
  /**
   * @since 0.0.1
   */
  EndOfRoad = 'END_OF_ROAD',
  /**
   * @since 0.0.1
   */
  Continue = 'CONTINUE',
  /**
   * @since 0.0.1
   */
  Roundabout = 'ROUNDABOUT',
  /**
   * @since 0.0.1
   */
  Rotary = 'ROTARY',
  /**
   * @since 0.0.1
   */
  RoundaboutTurn = 'ROUNDABOUT_TURN',
  /**
   * @since 0.0.1
   */
  Notification = 'NOTIFICATION',
  /**
   * @since 0.0.1
   */
  ExitRoundabout = 'EXIT_ROUNDABOUT',
  /**
   * @since 0.0.1
   */
  ExitRotary = 'EXIT_ROTARY',
}

/**
 * @since 0.0.1
 */
export enum ManeuverModifier {
  /**
   * @since 0.0.1
   */
  UTurn = 'U_TURN',
  /**
   * @since 0.0.1
   */
  SharpRight = 'SHARP_RIGHT',
  /**
   * @since 0.0.1
   */
  Right = 'RIGHT',
  /**
   * @since 0.0.1
   */
  SlightRight = 'SLIGHT_RIGHT',
  /**
   * @since 0.0.1
   */
  Straight = 'STRAIGHT',
  /**
   * @since 0.0.1
   */
  SlightLeft = 'SLIGHT_LEFT',
  /**
   * @since 0.0.1
   */
  Left = 'LEFT',
  /**
   * @since 0.0.1
   */
  SharpLeft = 'SHARP_LEFT',
}

/**
 * @since 0.0.1
 */
export enum ErrorCode {
  /**
   * The user denied the location permission.
   *
   * @since 0.0.1
   */
  LocationPermissionDenied = 'LOCATION_PERMISSION_DENIED',
  /**
   * No navigation session is running.
   *
   * @since 0.0.1
   */
  NotNavigating = 'NOT_NAVIGATING',
}
