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
   * @default false
   */
  readData?: boolean;
}

export interface PickFilesResult {
  /**
   * The list of picked files.
   */
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
  /**
   * The list of filters to apply to the picker.
   * 
   * Only available on iOS (14+).
   * 
   * @since 5.4.0
   * @see https://developer.apple.com/documentation/photokit/phpickerfilter/3952798-all
   */
  includeFilters?: Filter[];
  /**
   * The list of filters to exclude from the picker.
   * 
   * Only available on iOS (14+).
   * 
   * @since 5.4.0
   * @see https://developer.apple.com/documentation/photokit/phpickerfilter/3952802-not
   */
  excludeFilters?: Filter[];
}

/**
 * @since 0.5.3
 */
export type PickMediaResult = PickFilesResult;

/**
 * @since 0.5.3
 */
export type PickImagesOptions = Omit<PickMediaOptions, 'includeFilters' | 'excludeFilters'>;

/**
 * @since 0.5.3
 */
export type PickImagesResult = PickMediaResult;

/**
 * @since 0.5.3
 */
export type PickVideosOptions = Omit<PickMediaOptions, 'includeFilters' | 'excludeFilters'>;

/**
 * @since 0.5.3
 */
export type PickVideosResult = PickMediaResult;

/**
 * @since 5.4.0
 * @see https://developer.apple.com/documentation/photokit/phpickerfilter
 */
export enum Filter {
  /**
   * A filter that represents assets with multiple high-speed photos.
   * 
   * @since 5.4.0
   * @see https://developer.apple.com/documentation/photokit/phpickerfilter/3952799-bursts
   */
  Bursts = 'bursts',
  /**
   * A filter that represents videos with a shallow depth of field and focus transitions.
   * 
   * @since 5.4.0
   * @see https://developer.apple.com/documentation/photokit/phpickerfilter/3952800-cinematicvideos
   */
  CinematicVideos = 'cinematicVideos',
  /**
   * A filter that represents photos with depth information.
   * 
   * @since 5.4.0
   * @see https://developer.apple.com/documentation/photokit/phpickerfilter/3952801-deptheffectphotos
   */
  DepthEffectPhotos = 'depthEffectPhotos',
  /**
   * A filter that represents images and includes live photos.
   * 
   * @since 5.4.0
   * @see https://developer.apple.com/documentation/photokit/phpickerfilter/3606595-images
   */
  Images = 'images',
  /**
   * A filter that represents live photos.
   * 
   * @since 5.4.0
   * @see https://developer.apple.com/documentation/photokit/phpickerfilter/3606596-livephotos
   */
  LivePhotos = 'livePhotos',
  /**
   * A filter that represents panorama photos.
   * 
   * @since 5.4.0
   * @see https://developer.apple.com/documentation/photokit/phpickerfilter/3952803-panoramas
   */
  Panoramas = 'panoramas',
  /**
   * A filter that represents screen recordings.
   * 
   * @since 5.4.0
   * @see https://developer.apple.com/documentation/photokit/phpickerfilter/3952805-screenrecordings
   */
  ScreenRecordings = 'screenRecordings',
  /**
   * A filter that represents screenshots.
   * 
   * @since 5.4.0
   * @see https://developer.apple.com/documentation/photokit/phpickerfilter/3952806-screenshots
   */
  Screenshots = 'screenshots',
  /**
   * A filter that represents slow-motion videos.
   * 
   * @since 5.4.0
   * @see https://developer.apple.com/documentation/photokit/phpickerfilter/3952807-slomovideos
   */
  SlomoVideos = 'slomoVideos',
  /**
   * A filter that represents time-lapse videos.
   * 
   * @since 5.4.0
   * @see https://developer.apple.com/documentation/photokit/phpickerfilter/3952808-timelapsevideos
   */
  TimelapseVideos = 'timelapseVideos',
  /**
   * A filter that represents video assets.
   * 
   * @since 5.4.0
   * @see https://developer.apple.com/documentation/photokit/phpickerfilter/3606597-videos
   */
  Videos = 'videos',
}
