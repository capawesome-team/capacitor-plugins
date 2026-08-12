export interface ExifPlugin {
  /**
   * Read the EXIF metadata of an image file.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  readExif(options: ReadExifOptions): Promise<ReadExifResult>;
  /**
   * Remove the EXIF metadata from an image file in place.
   *
   * The pixel data is never re-encoded, so the image quality
   * is not affected.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  removeExif(options: RemoveExifOptions): Promise<void>;
  /**
   * Write EXIF metadata to an image file in place.
   *
   * Only the provided tags are updated. All other tags remain unchanged.
   * The pixel data is never re-encoded, so the image quality
   * is not affected.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  writeExif(options: WriteExifOptions): Promise<void>;
}

/**
 * The EXIF tags of an image file.
 *
 * Every property is optional and only present if the tag
 * exists in the file.
 *
 * @since 0.1.0
 */
export interface ExifTags {
  /**
   * The date and time when the image was originally captured
   * in the EXIF date format `YYYY:MM:DD HH:MM:SS`.
   *
   * @since 0.1.0
   * @example '2026:01:15 10:30:00'
   */
  dateTimeOriginal?: string;
  /**
   * The exposure time in seconds.
   *
   * @since 0.1.0
   * @example 0.008
   */
  exposureTime?: number;
  /**
   * The F number (aperture) of the lens.
   *
   * @since 0.1.0
   * @example 1.8
   */
  fNumber?: number;
  /**
   * Whether or not the flash fired when the image was captured.
   *
   * @since 0.1.0
   * @example true
   */
  flash?: boolean;
  /**
   * The focal length of the lens in millimeters.
   *
   * @since 0.1.0
   * @example 26
   */
  focalLength?: number;
  /**
   * The altitude in meters above (positive) or below (negative) sea level.
   *
   * @since 0.1.0
   * @example 11.5
   */
  gpsAltitude?: number;
  /**
   * The latitude in signed decimal degrees.
   *
   * Positive values are north of the equator, negative values are south.
   *
   * @since 0.1.0
   * @example 51.503364
   */
  gpsLatitude?: number;
  /**
   * The longitude in signed decimal degrees.
   *
   * Positive values are east of the prime meridian, negative values are west.
   *
   * @since 0.1.0
   * @example -0.127625
   */
  gpsLongitude?: number;
  /**
   * The ISO speed rating (photographic sensitivity).
   *
   * @since 0.1.0
   * @example 125
   */
  iso?: number;
  /**
   * The model of the lens.
   *
   * @since 0.1.0
   * @example 'iPhone 15 Pro back triple camera 6.765mm f/1.78'
   */
  lensModel?: string;
  /**
   * The manufacturer of the camera.
   *
   * @since 0.1.0
   * @example 'Apple'
   */
  make?: string;
  /**
   * The model of the camera.
   *
   * @since 0.1.0
   * @example 'iPhone 15 Pro'
   */
  model?: string;
  /**
   * The orientation of the image as defined by the EXIF specification (1-8).
   *
   * @since 0.1.0
   * @example 6
   */
  orientation?: number;
  /**
   * The height of the image in pixels.
   *
   * @since 0.1.0
   * @example 480
   */
  pixelHeight?: number;
  /**
   * The width of the image in pixels.
   *
   * @since 0.1.0
   * @example 640
   */
  pixelWidth?: number;
  /**
   * The name of the software that was used to create or edit the image.
   *
   * @since 0.1.0
   * @example 'Adobe Photoshop'
   */
  software?: string;
}

/**
 * @since 0.1.0
 */
export interface ReadExifOptions {
  /**
   * The path to the image file to read the EXIF metadata from.
   *
   * Local file paths and `file://` URIs are supported.
   *
   * @since 0.1.0
   * @example '/data/user/0/dev.robingenz.capacitor.plugindemo/cache/photo.jpg'
   */
  path: string;
}

/**
 * @since 0.1.0
 */
export interface ReadExifResult {
  /**
   * The EXIF tags that were read from the image file.
   *
   * @since 0.1.0
   */
  tags: ExifTags;
}

/**
 * @since 0.1.0
 */
export interface RemoveExifOptions {
  /**
   * Whether or not to keep the orientation tag when removing
   * the EXIF metadata.
   *
   * This is enabled by default so that images are not suddenly
   * displayed rotated after the metadata has been removed.
   *
   * @since 0.1.0
   * @default true
   * @example false
   */
  keepOrientation?: boolean;
  /**
   * The path to the image file to remove the EXIF metadata from.
   *
   * Local file paths and `file://` URIs are supported.
   *
   * @since 0.1.0
   * @example '/data/user/0/dev.robingenz.capacitor.plugindemo/cache/photo.jpg'
   */
  path: string;
}

/**
 * The writable subset of the EXIF tags.
 *
 * @since 0.1.0
 */
export interface WritableExifTags {
  /**
   * The date and time when the image was originally captured
   * in the EXIF date format `YYYY:MM:DD HH:MM:SS`.
   *
   * @since 0.1.0
   * @example '2026:01:15 10:30:00'
   */
  dateTimeOriginal?: string;
  /**
   * The exposure time in seconds.
   *
   * @since 0.1.0
   * @example 0.008
   */
  exposureTime?: number;
  /**
   * The F number (aperture) of the lens.
   *
   * @since 0.1.0
   * @example 1.8
   */
  fNumber?: number;
  /**
   * Whether or not the flash fired when the image was captured.
   *
   * @since 0.1.0
   * @example true
   */
  flash?: boolean;
  /**
   * The focal length of the lens in millimeters.
   *
   * @since 0.1.0
   * @example 26
   */
  focalLength?: number;
  /**
   * The altitude in meters above (positive) or below (negative) sea level.
   *
   * @since 0.1.0
   * @example 11.5
   */
  gpsAltitude?: number;
  /**
   * The latitude in signed decimal degrees.
   *
   * Positive values are north of the equator, negative values are south.
   * Must be provided together with `gpsLongitude`.
   *
   * @since 0.1.0
   * @example 51.503364
   */
  gpsLatitude?: number;
  /**
   * The longitude in signed decimal degrees.
   *
   * Positive values are east of the prime meridian, negative values are west.
   * Must be provided together with `gpsLatitude`.
   *
   * @since 0.1.0
   * @example -0.127625
   */
  gpsLongitude?: number;
  /**
   * The ISO speed rating (photographic sensitivity).
   *
   * @since 0.1.0
   * @example 125
   */
  iso?: number;
  /**
   * The model of the lens.
   *
   * @since 0.1.0
   * @example 'iPhone 15 Pro back triple camera 6.765mm f/1.78'
   */
  lensModel?: string;
  /**
   * The manufacturer of the camera.
   *
   * @since 0.1.0
   * @example 'Apple'
   */
  make?: string;
  /**
   * The model of the camera.
   *
   * @since 0.1.0
   * @example 'iPhone 15 Pro'
   */
  model?: string;
  /**
   * The orientation of the image as defined by the EXIF specification (1-8).
   *
   * @since 0.1.0
   * @example 6
   */
  orientation?: number;
  /**
   * The name of the software that was used to create or edit the image.
   *
   * @since 0.1.0
   * @example 'Adobe Photoshop'
   */
  software?: string;
}

/**
 * @since 0.1.0
 */
export interface WriteExifOptions {
  /**
   * The path to the image file to write the EXIF metadata to.
   *
   * Local file paths and `file://` URIs are supported.
   *
   * @since 0.1.0
   * @example '/data/user/0/dev.robingenz.capacitor.plugindemo/cache/photo.jpg'
   */
  path: string;
  /**
   * The EXIF tags to write to the image file.
   *
   * Only the provided tags are updated. All other tags remain unchanged.
   *
   * @since 0.1.0
   */
  tags: WritableExifTags;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The file was not found at the provided path.
   *
   * @since 0.1.0
   */
  FileNotFound = 'FILE_NOT_FOUND',
  /**
   * The EXIF metadata could not be read from the file.
   *
   * @since 0.1.0
   */
  ReadFailed = 'READ_FAILED',
  /**
   * The file format does not support writing or removing EXIF metadata
   * on this platform.
   *
   * @since 0.1.0
   */
  UnsupportedFormat = 'UNSUPPORTED_FORMAT',
  /**
   * The EXIF metadata could not be written to the file.
   *
   * @since 0.1.0
   */
  WriteFailed = 'WRITE_FAILED',
}
