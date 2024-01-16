import type { PluginListenerHandle } from '@capacitor/core';

export interface FilePickerPlugin {
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
   * Open the file picker that allows the user to select one or more files.
   */
  pickFiles(options?: PickFilesOptions): Promise<PickFilesResult>;
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
   * Called when the file picker is dismissed.
   *
   * Only available on iOS.
   *
   * @since 0.6.2
   */
  addListener(
    eventName: 'pickerDismissed',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
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

export interface PickFilesOptions {
  /**
   * List of accepted file types.
   * Look at [IANA Media Types](https://www.iana.org/assignments/media-types/media-types.xhtml) for a complete list of standard media types.
   *
   * This option cannot be used with `multiple: true` on Android.
   *
   * @example ['image/png', 'application/pdf']
   */
  types?: string[];
  /**
   * Whether multiple files may be selected.
   *
   * @default false
   */
  multiple?: boolean;
  /**
   * Whether to read the file data.
   *
   * @default false
   */
  readData?: boolean;
}

export interface PickFilesResult {
  files: PickedFile[];
}

/**
 * @since 0.5.3
 */
export type PickedFile = File;

/**
 * @deprecated Use `PickedFile` instead.
 */
export interface File {
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
   * Whether multiple files may be selected.
   *
   * @default false
   */
  multiple?: boolean;
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
   * @default false
   * @see https://developer.apple.com/documentation/photokit/phpickerconfiguration/assetrepresentationmode/current
   */
  skipTranscoding?: boolean;
  /**
   * The maximum number of files that the user can select.
   *
   * This option is ignored if `multiple` is set to `false`.
   *
   * Only available on iOS.
   *
   * @default 0 (unlimited)
   *
   * @example 4
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
