import type { PermissionState, PluginListenerHandle } from '@capacitor/core';

export interface FlicPlugin {
  /**
   * Check the status of the permissions that are required to use Flic buttons.
   *
   * On iOS, the Bluetooth permission is requested when `initialize(...)` is called.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Connect a button.
   *
   * The returned promise resolves immediately. The connection is established
   * as soon as the button is available and does not time out. Listen to the
   * `buttonConnected` and `buttonReady` events to know when the button is
   * ready to be used.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  connectButtonById(options: ConnectButtonByIdOptions): Promise<void>;
  /**
   * Disconnect a button or cancel a pending connection.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  disconnectButtonById(options: DisconnectButtonByIdOptions): Promise<void>;
  /**
   * Forget a button.
   *
   * This removes the pairing with the button. To use the button again,
   * it must be paired again using `startScan()`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  forgetButtonById(options: ForgetButtonByIdOptions): Promise<void>;
  /**
   * Get all buttons that are currently paired with the app.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getButtons(): Promise<GetButtonsResult>;
  /**
   * Initialize the plugin.
   *
   * This method must be called before any other method.
   * It is recommended to call this method as soon as possible
   * after the app has launched to minimize the delay of any
   * pending button events.
   *
   * On iOS, this method triggers the Bluetooth permission prompt
   * if the permission has not yet been granted.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  initialize(options?: InitializeOptions): Promise<void>;
  /**
   * Request the permissions that are required to use Flic buttons.
   *
   * On iOS, this method only returns the current permission status since
   * the Bluetooth permission is requested when `initialize(...)` is called.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  requestPermissions(): Promise<PermissionStatus>;
  /**
   * Start scanning for new buttons to pair.
   *
   * To pair a button, press and hold it for at least 6 seconds while scanning.
   * The returned promise resolves with the paired button once the pairing
   * has completed. Listen to the `scanStatusChanged` event to keep the user
   * informed about the scan progress.
   *
   * Only one scan can be running at a time.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  startScan(): Promise<StartScanResult>;
  /**
   * Stop an ongoing scan.
   *
   * This rejects the pending `startScan()` call.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  stopScan(): Promise<void>;
  /**
   * Called when a button establishes a Bluetooth connection.
   *
   * The button is not ready to be used until the `buttonReady` event is emitted.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'buttonConnected',
    listenerFunc: (event: ButtonConnectedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a connection attempt to a button fails.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'buttonConnectionFailed',
    listenerFunc: (event: ButtonConnectionFailedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the Bluetooth connection with a button is lost.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'buttonDisconnected',
    listenerFunc: (event: ButtonDisconnectedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a button is double clicked.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'buttonDoubleClick',
    listenerFunc: (event: ButtonEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a button is pressed down.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'buttonDown',
    listenerFunc: (event: ButtonEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a button is held down.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'buttonHold',
    listenerFunc: (event: ButtonEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a button has been cryptographically verified after a connection
   * and is ready to be used.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'buttonReady',
    listenerFunc: (event: ButtonReadyEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a button is clicked once.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'buttonSingleClick',
    listenerFunc: (event: ButtonEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the pairing with a button is no longer valid, for example
   * because the button has been factory reset. In this case, the button must
   * be forgotten using `forgetButtonById(...)` and then paired again.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'buttonUnpaired',
    listenerFunc: (event: ButtonUnpairedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a button is released.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'buttonUp',
    listenerFunc: (event: ButtonEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the status of an ongoing scan changes.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'scanStatusChanged',
    listenerFunc: (event: ScanStatusChangedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.1.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface Button {
  /**
   * The last known battery voltage of the button in volts.
   *
   * If no battery sample has been taken yet, this property is not available.
   * It is recommended to show a "change the battery soon" hint in your app
   * once the voltage goes below `2.65`.
   *
   * @since 0.1.0
   * @example 2.9
   */
  batteryVoltage?: number;
  /**
   * The connection state of the button.
   *
   * @since 0.1.0
   */
  connectionState: ButtonConnectionState;
  /**
   * The revision of the firmware currently running on the button.
   *
   * @since 0.1.0
   * @example 12
   */
  firmwareVersion: number;
  /**
   * The identifier of the button on this device.
   *
   * On Android, this is the Bluetooth address of the button.
   * On iOS, this is an identifier that is guaranteed to be the same
   * for each button paired to a particular device.
   *
   * @since 0.1.0
   * @example '00:80:e4:da:12:34:56'
   */
  id: string;
  /**
   * Whether or not the button has been cryptographically verified
   * after a connection and is ready to be used.
   *
   * @since 0.1.0
   * @example true
   */
  isReady: boolean;
  /**
   * Whether or not the pairing with the button is no longer valid, for example
   * because the button has been factory reset. In this case, the button must
   * be forgotten using `forgetButtonById(...)` and then paired again.
   *
   * @since 0.1.0
   * @example false
   */
  isUnpaired: boolean;
  /**
   * The human readable name of the button that the user may change
   * in the official Flic app.
   *
   * @since 0.1.0
   * @example 'Kitchen Flic'
   */
  name?: string;
  /**
   * The number of times the button has been clicked since it last booted.
   *
   * @since 0.1.0
   * @example 42
   */
  pressCount: number;
  /**
   * The serial number of the button that is printed on the backside
   * of the button inside the battery hatch.
   *
   * @since 0.1.0
   * @example 'BF40-D22581'
   */
  serialNumber: string;
  /**
   * The unique identifier of the button that is the same across devices and apps.
   *
   * @since 0.1.0
   * @example '4d6d76f7d0d24e6f9e95cf9ff1226b25'
   */
  uuid: string;
}

/**
 * @since 0.1.0
 */
export interface ButtonConnectedEvent {
  /**
   * The identifier of the button.
   *
   * @since 0.1.0
   * @example '00:80:e4:da:12:34:56'
   */
  buttonId: string;
}

/**
 * @since 0.1.0
 */
export interface ButtonConnectionFailedEvent {
  /**
   * The identifier of the button.
   *
   * @since 0.1.0
   * @example '00:80:e4:da:12:34:56'
   */
  buttonId: string;
  /**
   * The message that describes why the connection attempt failed.
   *
   * @since 0.1.0
   */
  message: string;
}

/**
 * @since 0.1.0
 */
export interface ButtonDisconnectedEvent {
  /**
   * The identifier of the button.
   *
   * @since 0.1.0
   * @example '00:80:e4:da:12:34:56'
   */
  buttonId: string;
}

/**
 * @since 0.1.0
 */
export interface ButtonEvent {
  /**
   * The identifier of the button.
   *
   * @since 0.1.0
   * @example '00:80:e4:da:12:34:56'
   */
  buttonId: string;
  /**
   * The timestamp of the event in milliseconds since the Unix epoch.
   *
   * @since 0.1.0
   * @example 1755026117653
   */
  timestamp: number;
  /**
   * Whether or not the event was queued because it occurred
   * before the button was connected.
   *
   * @since 0.1.0
   * @example false
   */
  wasQueued: boolean;
}

/**
 * @since 0.1.0
 */
export interface ButtonReadyEvent {
  /**
   * The identifier of the button.
   *
   * @since 0.1.0
   * @example '00:80:e4:da:12:34:56'
   */
  buttonId: string;
}

/**
 * @since 0.1.0
 */
export interface ButtonUnpairedEvent {
  /**
   * The identifier of the button.
   *
   * @since 0.1.0
   * @example '00:80:e4:da:12:34:56'
   */
  buttonId: string;
}

/**
 * @since 0.1.0
 */
export interface ConnectButtonByIdOptions {
  /**
   * The identifier of the button.
   *
   * @since 0.1.0
   * @example '00:80:e4:da:12:34:56'
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface DisconnectButtonByIdOptions {
  /**
   * The identifier of the button.
   *
   * @since 0.1.0
   * @example '00:80:e4:da:12:34:56'
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface ForgetButtonByIdOptions {
  /**
   * The identifier of the button.
   *
   * @since 0.1.0
   * @example '00:80:e4:da:12:34:56'
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface GetButtonsResult {
  /**
   * The buttons that are currently paired with the app.
   *
   * @since 0.1.0
   */
  buttons: Button[];
}

/**
 * @since 0.1.0
 */
export interface InitializeOptions {
  /**
   * Whether or not you intend to use the buttons while the app
   * is in the background.
   *
   * If set to `true`, the `Uses Bluetooth LE accessories` background mode
   * must be enabled in the app's capabilities.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   * @default false
   * @example true
   */
  iosBackground?: boolean;
}

/**
 * @since 0.1.0
 */
export interface PermissionStatus {
  /**
   * The permission state of the Bluetooth permission.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  bluetooth: PermissionState;
  /**
   * The permission state of the `BLUETOOTH_CONNECT` permission.
   *
   * Only required on Android 12 and later.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  bluetoothConnect: PermissionState;
  /**
   * The permission state of the `BLUETOOTH_SCAN` permission.
   *
   * Only required on Android 12 and later.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  bluetoothScan: PermissionState;
  /**
   * The permission state of the `ACCESS_FINE_LOCATION` permission.
   *
   * Only required on Android 11 and earlier.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  location: PermissionState;
}

/**
 * @since 0.1.0
 */
export interface ScanStatusChangedEvent {
  /**
   * The status of the scan.
   *
   * @since 0.1.0
   */
  status: ScanStatus;
}

/**
 * @since 0.1.0
 */
export interface StartScanResult {
  /**
   * The button that was paired.
   *
   * @since 0.1.0
   */
  button: Button;
}

/**
 * @since 0.1.0
 */
export enum ButtonConnectionState {
  /**
   * The button is connected.
   *
   * @since 0.1.0
   */
  Connected = 'CONNECTED',
  /**
   * The button is disconnected but a pending connection is set.
   * The button will connect as soon as it becomes available.
   *
   * @since 0.1.0
   */
  Connecting = 'CONNECTING',
  /**
   * The button is disconnected and no pending connection is set.
   *
   * @since 0.1.0
   */
  Disconnected = 'DISCONNECTED',
  /**
   * The button is connected but is attempting to disconnect.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  Disconnecting = 'DISCONNECTING',
}

/**
 * @since 0.1.0
 */
export enum ScanStatus {
  /**
   * The user must accept the system pairing dialog to continue.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  AskToAcceptPairRequest = 'ASK_TO_ACCEPT_PAIR_REQUEST',
  /**
   * A button was found and a connection is being established.
   *
   * @since 0.1.0
   */
  Connected = 'CONNECTED',
  /**
   * A button was discovered.
   *
   * @since 0.1.0
   */
  Discovered = 'DISCOVERED',
  /**
   * The button has been cryptographically verified.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  Verified = 'VERIFIED',
}
