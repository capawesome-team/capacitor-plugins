export interface AndroidIntentLauncherPlugin {
  /**
   * Check whether an activity exists that can handle the given intent.
   *
   * This is a wrapper around the `PackageManager.resolveActivity(...)` API.
   *
   * On Android 11 (API level 30) and higher, the result is affected by
   * package visibility. Your app may need to declare matching `<queries>`
   * entries in its `AndroidManifest.xml` for the intent to be resolved.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  canResolveActivity(
    options: CanResolveActivityOptions,
  ): Promise<CanResolveActivityResult>;
  /**
   * Launch an activity for the given intent.
   *
   * The intent is started via the `startActivityForResult(...)` API so the
   * result code and result data of the launched activity are returned once it
   * finishes.
   *
   * This is the power-user escape hatch for system screens and app
   * integrations that no dedicated plugin covers. Prefer a typed plugin (such
   * as [Settings Launcher](https://capawesome.io/docs/sdks/capacitor/settings-launcher/)
   * or [App Launcher](https://capawesome.io/docs/sdks/capacitor/app-launcher/))
   * where one exists.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  startActivity(options: StartActivityOptions): Promise<StartActivityResult>;
}

/**
 * @since 0.1.0
 */
export interface StartActivityOptions {
  /**
   * The action of the intent.
   *
   * @example 'android.intent.action.VIEW'
   * @since 0.1.0
   */
  action: string;
  /**
   * The categories to add to the intent.
   *
   * @example ['android.intent.category.DEFAULT']
   * @since 0.1.0
   */
  categories?: string[];
  /**
   * The fully qualified class name of the component to launch.
   *
   * Must be used together with the `packageName` property to create an
   * explicit intent that targets a specific component.
   *
   * @example 'com.example.app.MainActivity'
   * @since 0.1.0
   */
  className?: string;
  /**
   * The data URI of the intent.
   *
   * @example 'https://capawesome.io'
   * @since 0.1.0
   */
  dataUri?: string;
  /**
   * The extras to add to the intent.
   *
   * Only primitive values (string, number and boolean) are supported.
   *
   * @example { 'android.intent.extra.TEXT': 'Hello world!' }
   * @since 0.1.0
   */
  extras?: { [key: string]: string | number | boolean };
  /**
   * The flags to add to the intent.
   *
   * Multiple flags can be combined using the bitwise OR operator.
   *
   * @example 268435456
   * @since 0.1.0
   */
  flags?: number;
  /**
   * The package name of the component to launch.
   *
   * If used without the `className` property, the intent is restricted to the
   * given package. If used together with the `className` property, an explicit
   * intent that targets a specific component is created.
   *
   * @example 'com.example.app'
   * @since 0.1.0
   */
  packageName?: string;
  /**
   * The MIME type of the intent data.
   *
   * @example 'text/plain'
   * @since 0.1.0
   */
  type?: string;
}

/**
 * @since 0.1.0
 */
export interface StartActivityResult {
  /**
   * The data URI returned by the launched activity.
   *
   * @example 'content://com.example.app/1'
   * @since 0.1.0
   */
  dataUri: string | null;
  /**
   * The result code returned by the launched activity.
   *
   * The value is `-1` if the activity finished successfully (`RESULT_OK`), `0`
   * if it was canceled (`RESULT_CANCELED`) or any other custom result code set
   * by the launched activity.
   *
   * @example -1
   * @since 0.1.0
   */
  resultCode: number;
}

/**
 * @since 0.1.0
 */
export type CanResolveActivityOptions = StartActivityOptions;

/**
 * @since 0.1.0
 */
export interface CanResolveActivityResult {
  /**
   * Whether or not an activity exists that can handle the intent.
   *
   * @example true
   * @since 0.1.0
   */
  canResolve: boolean;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * No activity was found that can handle the intent.
   *
   * @since 0.1.0
   */
  ActivityNotFound = 'ACTIVITY_NOT_FOUND',
  /**
   * The activity could not be started.
   *
   * @since 0.1.0
   */
  StartFailed = 'START_FAILED',
}
