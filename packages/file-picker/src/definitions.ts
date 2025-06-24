import type { PluginListenerHandle } from '@capacitor/core';

export interface FilePickerPlugin {
  /**
   * Check permissions to access files.
   *
   * Only available on Android.
   *
   * @since 6.1.0
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Convert a HEIC image to JPEG.
   *
   * Only available on iOS.
   *
   * @since 0.6.0
   */
  convertHeicToJpeg(
    options: ConvertHeicToJpegOptions,
  ): Promise<ConvertHeicToJpegResult>;
  /**
   * Copy a file to a new location.
   *
   * @since 7.1.0
   */
  copyFile(options: CopyFileOptions): Promise<void>;
  /**
   * Open the file picker that allows the user to select one or more files.
   */
  pickFiles(options?: PickFilesOptions): Promise<PickFilesResult>;
  /**
   * Open a picker dialog that allows the user to select a directory.
   *
   * Only available on Android and iOS.
   *
   * @since 6.2.0
   */
  pickDirectory(): Promise<PickDirectoryResult>;
  /**
   * Pick one or more images from the gallery.
   *
   * On iOS 13 and older it only allows to pick one image.
   *
   * Only available on Android and iOS.
   *
   * @since 0.5.3
   */
  pickImages(options?: PickImagesOptions): Promise<PickImagesResult>;
  /**
   * Pick one or more images or videos from the gallery.
   *
   * On iOS 13 and older it only allows to pick one image or video.
   *
   * Only available on Android and iOS.
   *
   * @since 0.5.3
   */
  pickMedia(options?: PickMediaOptions): Promise<PickMediaResult>;
  /**
   * Pick one or more videos from the gallery.
   *
   * On iOS 13 and older it only allows to pick one video.
   *
   * Only available on Android and iOS.
   *
   * @since 0.5.3
   */
  pickVideos(options?: PickVideosOptions): Promise<PickVideosResult>;
  /**
   * Request permissions to access files.
   *
   * Only available on Android.
   *
   * @since 6.1.0
   */
  requestPermissions(
    options?: RequestPermissionsOptions,
  ): Promise<PermissionStatus>;
  /**
   * Called when the file picker is dismissed.
   *
   * Only available on iOS.
   *
   * @since 0.6.2
   */
  addListener(
    eventName: 'pickerDismissed',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.6.2
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.6.0
 */
export interface ConvertHeicToJpegOptions {
  /**
   * The path of the HEIC image.
   *
   * @example '/path/to/image.heic'
   * @since 0.6.0
   */
  path: string;
}

/**
 * @since 0.6.0
 */
export interface ConvertHeicToJpegResult {
  /**
   * The path of the converted JPEG image.
   *
   * @example '/path/to/image.jpeg'
   * @since 0.6.0
   */
  path: string;
}

/**
 * @since 7.1.0
 */
export interface CopyFileOptions {
  /**
   * The path of the file to copy.
   *
   * @example '/path/to/file.txt'
   * @since 7.1.0
   */
  from: string;
  /**
   * Whether to overwrite if the file at destination already exists.
   *
   * @default true
   * @example false
   * @since 7.2.0
   */
  overwrite: boolean;
  /**
   * The path to copy the file to.
   *
   * @example '/path/to/new-file.txt'
   * @since 7.1.0
   */
  to: string;
}

/**
 * @since 6.1.0
 */
export interface PermissionStatus {
  /**
   * Permission state for accessing media location.
   *
   * On Android, this requests/checks the `ACCESS_MEDIA_LOCATION` permission.
   *
   * @since 6.1.0
   */
  accessMediaLocation: PermissionState;
  /**
   * Permission state for reading external storage.
   *
   * On Android, this requests/checks the `READ_EXTERNAL_STORAGE` permission.
   *
   * @since 6.1.0
   */
  readExternalStorage: PermissionState;
}

export interface PickFilesOptions {
  /**
   * List of accepted file types.
   * Look at [IANA Media Types](https://www.iana.org/assignments/media-types/media-types.xhtml) for a complete list of standard media types.
   *
   * This option is ignored if `limit` is set.
   *
   * @example ['image/png', 'application/pdf']
   */
  types?: string[];
  /**
   * The maximum number of files that the user can select.
   * Setting this to `0` sets the selection limit to unlimited.
   *
   * Currently, only `0` and `1` are supported.
   *
   * @default 0
   * @example 1
   * @since 6.0.0
   */
  limit?: number;
  /**
   * Whether to read the file data.
   *
   * **Attention**: Reading large files can lead to app crashes.
   * It's therefore not recommended to use this option.
   * Instead, use the [fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch)
   * to load the file as a blob, see [this example](https://capawesome.io/blog/the-file-handling-guide-for-capacitor/#read-a-file).
   *
   * @default false
   */
  readData?: boolean;
}

export interface PickFilesResult {
  files: PickedFile[];
}

export interface PickedFile {
  /**
   * The Blob instance of the file.
   *
   * Only available on Web.
   */
  blob?: Blob;
  /**
   * The Base64 string representation of the data contained in the file.
   *
   * Is only provided if `readData` is set to `true`.
   */
  data?: string;
  /**
   * The duration of the video in seconds.
   *
   * Only available on Android and iOS.
   *
   * @since 0.5.3
   */
  duration?: number;
  /**
   * The height of the image or video in pixels.
   *
   * Only available on Android and iOS.
   *
   * @since 0.5.3
   */
  height?: number;
  /**
   * The mime type of the file.
   */
  mimeType: string;
  /**
   * The last modified timestamp of the file in milliseconds.
   *
   * @since 0.5.9
   */
  modifiedAt?: number;
  /**
   * The name of the file.
   */
  name: string;
  /**
   * The path of the file.
   *
   * Only available on Android and iOS.
   */
  path?: string;
  /**
   * The size of the file in bytes.
   */
  size: number;
  /**
   * The width of the image or video in pixels.
   *
   * Only available on Android and iOS.
   *
   * @since 0.5.3
   */
  width?: number;
}

/**
 * @since 0.5.3
 */
export interface PickMediaOptions {
  /**
   * Whether to read the file data.
   *
   * @default false
   */
  readData?: boolean;
  /**
   * Whether to avoid transcoding, if possible.
   *
   * On iOS, for example, HEIC images are automatically transcoded to JPEG.
   *
   * Only available on iOS.
   *
   * @default true
   * @see https://developer.apple.com/documentation/photokit/phpickerconfiguration/assetrepresentationmode/current
   */
  skipTranscoding?: boolean;
  /**
   * The maximum number of files that the user can select.
   * Setting this to `0` sets the selection limit to unlimited.
   *
   * On Android and Web, only `0` and `1` are supported.
   *
   * @default 0
   * @example 1
   * @since 5.2.0
   */
  limit?: number;
  /**
   * Whether an ordered number is displayed instead of a check mark in the selection badge.
   *
   * Only available on iOS (15+).
   *
   * @default false
   * @since 5.3.0
   */
  ordered?: boolean;
}

export interface PickDirectoryResult {
  /**
   * The path to the selected directory.
   *
   * @since 6.2.0
   */
  path: string;
}

/**
 * @since 0.5.3
 */
export type PickMediaResult = PickFilesResult;

/**
 * @since 0.5.3
 */
export type PickImagesOptions = PickMediaOptions;

/**
 * @since 0.5.3
 */
export type PickImagesResult = PickMediaResult;

/**
 * @since 0.5.3
 */
export type PickVideosOptions = PickMediaOptions;

/**
 * @since 0.5.3
 */
export type PickVideosResult = PickMediaResult;

/**
 * @since 6.1.0
 */
export interface RequestPermissionsOptions {
  /**
   * The permissions to request.
   *
   * @since 6.1.0
   * @default ["accessMediaLocation", "readExternalStorage"]
   */
  permissions?: PermissionType[];
}

/**
 * @since 6.1.0
 */
export type PermissionType = 'accessMediaLocation' | 'readExternalStorage';
