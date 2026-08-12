export interface SettingsLauncherPlugin {
  /**
   * Open a specific Android system settings screen.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  openAndroidSettings(options: OpenAndroidSettingsOptions): Promise<void>;
  /**
   * Open the settings screen of your app.
   *
   * This is the recommended way to guide users to grant a previously denied
   * permission.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  openAppSettings(): Promise<void>;
  /**
   * Open the notification settings screen of your app.
   *
   * On iOS, this method requires iOS 16 or later and rejects on older versions.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  openNotificationSettings(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface OpenAndroidSettingsOptions {
  /**
   * The Android system settings screen to open.
   *
   * @since 0.1.0
   */
  page: AndroidSettingsPage;
}

/**
 * The Android system settings screen to open.
 *
 * Some screens are not available on every device because manufacturers may
 * remove or replace them. In that case, the method rejects with the
 * `PAGE_NOT_SUPPORTED` error code.
 *
 * @since 0.1.0
 */
export enum AndroidSettingsPage {
  /**
   * Show settings for accessibility modules.
   *
   * @since 0.1.0
   */
  Accessibility = 'ACCESSIBILITY',
  /**
   * Show settings to allow entering/exiting airplane mode.
   *
   * @since 0.1.0
   */
  AirplaneMode = 'AIRPLANE_MODE',
  /**
   * Show settings to allow configuration of APNs.
   *
   * @since 0.1.0
   */
  Apn = 'APN',
  /**
   * Show settings to allow configuration of battery saver mode.
   *
   * @since 0.1.0
   */
  BatterySaver = 'BATTERY_SAVER',
  /**
   * Show settings to allow configuration of Bluetooth.
   *
   * @since 0.1.0
   */
  Bluetooth = 'BLUETOOTH',
  /**
   * Show settings for video captioning.
   *
   * @since 0.1.0
   */
  Captioning = 'CAPTIONING',
  /**
   * Show settings to allow configuration of cast endpoints.
   *
   * @since 0.1.0
   */
  CastSettings = 'CAST_SETTINGS',
  /**
   * Show settings for selection of 2G/3G.
   *
   * @since 0.1.0
   */
  DataRoaming = 'DATA_ROAMING',
  /**
   * Show settings to allow configuration of date and time.
   *
   * @since 0.1.0
   */
  Date = 'DATE',
  /**
   * Show settings to allow configuration of application development settings.
   *
   * @since 0.1.0
   */
  Development = 'DEVELOPMENT',
  /**
   * Show general device information settings (serial number, software version,
   * phone number, etc.).
   *
   * @since 0.1.0
   */
  DeviceInfo = 'DEVICE_INFO',
  /**
   * Show settings to allow configuration of display.
   *
   * @since 0.1.0
   */
  Display = 'DISPLAY',
  /**
   * Show Daydream settings.
   *
   * @since 0.1.0
   */
  Dream = 'DREAM',
  /**
   * Show settings for selecting the default home app.
   *
   * @since 0.1.0
   */
  Home = 'HOME',
  /**
   * Show screen for controlling which apps can ignore battery optimizations.
   *
   * @since 0.1.0
   */
  IgnoreBatteryOptimization = 'IGNORE_BATTERY_OPTIMIZATION',
  /**
   * Show settings to configure input methods, in particular allowing the user
   * to enable input methods.
   *
   * @since 0.1.0
   */
  InputMethod = 'INPUT_METHOD',
  /**
   * Show settings for internal storage.
   *
   * @since 0.1.0
   */
  InternalStorage = 'INTERNAL_STORAGE',
  /**
   * Show settings to allow configuration of locale.
   *
   * @since 0.1.0
   */
  Locale = 'LOCALE',
  /**
   * Show settings to allow configuration of current location sources.
   *
   * @since 0.1.0
   */
  Location = 'LOCATION',
  /**
   * Show settings to manage all applications.
   *
   * @since 0.1.0
   */
  ManageAllApplications = 'MANAGE_ALL_APPLICATIONS',
  /**
   * Show settings to manage installed applications.
   *
   * @since 0.1.0
   */
  ManageApplications = 'MANAGE_APPLICATIONS',
  /**
   * Show settings for memory card storage.
   *
   * @since 0.1.0
   */
  MemoryCard = 'MEMORY_CARD',
  /**
   * Show settings for selecting the network operator.
   *
   * @since 0.1.0
   */
  Network = 'NETWORK',
  /**
   * Show settings to allow configuration of NFC.
   *
   * @since 0.1.0
   */
  Nfc = 'NFC',
  /**
   * Show settings to allow configuration of tap and pay.
   *
   * @since 0.1.0
   */
  NfcPayment = 'NFC_PAYMENT',
  /**
   * Show settings for managing which apps have access to notification listener
   * services.
   *
   * @since 0.1.0
   */
  NotificationListener = 'NOTIFICATION_LISTENER',
  /**
   * Show settings for printing.
   *
   * @since 0.1.0
   */
  Printing = 'PRINTING',
  /**
   * Show settings to allow configuration of privacy options.
   *
   * @since 0.1.0
   */
  Privacy = 'PRIVACY',
  /**
   * Show settings to allow configuration of search.
   *
   * @since 0.1.0
   */
  Search = 'SEARCH',
  /**
   * Show settings to allow configuration of security and location privacy.
   *
   * @since 0.1.0
   */
  Security = 'SECURITY',
  /**
   * Show settings to allow configuration of sound and volume.
   *
   * @since 0.1.0
   */
  Sound = 'SOUND',
  /**
   * Show settings to allow configuration of sync settings.
   *
   * @since 0.1.0
   */
  Sync = 'SYNC',
  /**
   * Show screen for controlling which apps can access usage statistics.
   *
   * @since 0.1.0
   */
  Usage = 'USAGE',
  /**
   * Show settings to manage the user input dictionary.
   *
   * @since 0.1.0
   */
  UserDictionary = 'USER_DICTIONARY',
  /**
   * Show settings to configure input methods, in particular allowing the user
   * to enable voice input services.
   *
   * @since 0.1.0
   */
  VoiceInput = 'VOICE_INPUT',
  /**
   * Show settings to allow configuration of VPN.
   *
   * @since 0.1.0
   */
  Vpn = 'VPN',
  /**
   * Show settings to allow configuration of Wi-Fi.
   *
   * @since 0.1.0
   */
  Wifi = 'WIFI',
  /**
   * Show settings to allow configuration of wireless controls such as Wi-Fi,
   * Bluetooth and mobile networks.
   *
   * @since 0.1.0
   */
  Wireless = 'WIRELESS',
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The settings screen could not be opened.
   *
   * @since 0.1.0
   */
  OpenFailed = 'OPEN_FAILED',
  /**
   * The requested settings screen is not supported on this device.
   *
   * @since 0.1.0
   */
  PageNotSupported = 'PAGE_NOT_SUPPORTED',
}
