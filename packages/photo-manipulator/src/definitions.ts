export interface PhotoManipulatorPlugin {
  /**
   * Get the dimensions and format of an image without decoding
   * the pixel data where possible.
   *
   * @since 0.1.0
   */
  getInfo(options: GetInfoOptions): Promise<GetInfoResult>;
  /**
   * Apply one or more transformations to an image and write the
   * result to a new file.
   *
   * The operations are always applied in the following fixed order:
   * crop → resize → rotate → flip. Chain multiple calls if you need
   * a different order.
   *
   * The EXIF orientation of the source image is applied during decoding
   * so that the output is always upright. All other metadata (e.g. EXIF,
   * GPS) is stripped by re-encoding.
   *
   * @since 0.1.0
   */
  transform(options: TransformOptions): Promise<TransformResult>;
}

/**
 * @since 0.1.0
 */
export interface CropOptions {
  /**
   * The height of the crop region in pixels.
   *
   * @since 0.1.0
   * @example 1080
   */
  height: number;
  /**
   * The width of the crop region in pixels.
   *
   * @since 0.1.0
   * @example 1080
   */
  width: number;
  /**
   * The horizontal offset of the crop region in pixels,
   * measured from the left edge of the source image.
   *
   * @since 0.1.0
   * @example 100
   */
  x: number;
  /**
   * The vertical offset of the crop region in pixels,
   * measured from the top edge of the source image.
   *
   * @since 0.1.0
   * @example 100
   */
  y: number;
}

/**
 * @since 0.1.0
 */
export interface GetInfoOptions {
  /**
   * The path of the image file.
   *
   * On Android and iOS, only local file paths and `file://` URIs are supported.
   * On the web, any fetchable URL (e.g. `https://`, `blob:`, `data:`) is supported.
   *
   * @since 0.1.0
   * @example 'file:///data/user/0/dev.example.app/cache/photo.heic'
   */
  path: string;
}

/**
 * @since 0.1.0
 */
export interface GetInfoResult {
  /**
   * The format of the image or `null` if the format could not be determined.
   *
   * The value is provided by the platform decoder and may differ slightly
   * between platforms for the same file (e.g. `heic` on iOS and the web
   * but `heif` on Android).
   *
   * @since 0.1.0
   * @example 'jpeg'
   */
  format: string | null;
  /**
   * The height of the image in pixels after the EXIF orientation
   * has been applied.
   *
   * @since 0.1.0
   * @example 3024
   */
  height: number;
  /**
   * The width of the image in pixels after the EXIF orientation
   * has been applied.
   *
   * @since 0.1.0
   * @example 4032
   */
  width: number;
}

/**
 * @since 0.1.0
 */
export interface ResizeOptions {
  /**
   * The target height in pixels.
   *
   * If only one of `width` and `height` is provided,
   * the aspect ratio is preserved.
   *
   * @since 0.1.0
   * @example 1080
   */
  height?: number;
  /**
   * The target width in pixels.
   *
   * If only one of `width` and `height` is provided,
   * the aspect ratio is preserved.
   *
   * @since 0.1.0
   * @example 1920
   */
  width?: number;
}

/**
 * @since 0.1.0
 */
export interface TransformOptions {
  /**
   * The region of the source image to crop to, in source pixels.
   *
   * The region must be within the bounds of the source image.
   *
   * @since 0.1.0
   */
  crop?: CropOptions;
  /**
   * Whether or not to flip the image horizontally.
   *
   * @since 0.1.0
   * @default false
   */
  flipHorizontal?: boolean;
  /**
   * Whether or not to flip the image vertically.
   *
   * @since 0.1.0
   * @default false
   */
  flipVertical?: boolean;
  /**
   * The format of the output file.
   *
   * On iOS, `ImageFormat.Webp` is not supported and rejects with
   * the `UNSUPPORTED_FORMAT` error code.
   *
   * @since 0.1.0
   * @default ImageFormat.Jpeg
   */
  format?: ImageFormat;
  /**
   * The path of the image file to transform.
   *
   * On Android and iOS, only local file paths and `file://` URIs are supported.
   * On the web, any fetchable URL (e.g. `https://`, `blob:`, `data:`) is supported.
   *
   * @since 0.1.0
   * @example 'file:///data/user/0/dev.example.app/cache/photo.heic'
   */
  path: string;
  /**
   * The quality of the output file between `0` and `1`.
   *
   * Only applied when `format` is `ImageFormat.Jpeg` or `ImageFormat.Webp`.
   *
   * @since 0.1.0
   * @default 0.9
   * @example 0.7
   */
  quality?: number;
  /**
   * The target size to resize the image to, in pixels.
   *
   * If only one of `width` and `height` is provided, the aspect ratio
   * is preserved. The resize is applied after the crop.
   *
   * @since 0.1.0
   */
  resize?: ResizeOptions;
  /**
   * The clockwise angle to rotate the image by, in degrees.
   *
   * Must be `90`, `180` or `270`.
   *
   * @since 0.1.0
   * @default 0
   * @example 90
   */
  rotate?: number;
}

/**
 * @since 0.1.0
 */
export interface TransformResult {
  /**
   * The height of the transformed image in pixels.
   *
   * @since 0.1.0
   * @example 1080
   */
  height: number;
  /**
   * The path of the transformed image file.
   *
   * On Android and iOS, the file is stored in the cache directory and
   * deleted on the next app launch. Move it to a permanent location if
   * you want to keep it. On the web, the path is an object URL.
   *
   * @since 0.1.0
   * @example 'file:///data/user/0/dev.example.app/cache/capawesome_capacitor_photo_manipulator_images/B995B473-0808-4C3F-9C60-6BE38A12A452.jpeg'
   */
  path: string;
  /**
   * The width of the transformed image in pixels.
   *
   * @since 0.1.0
   * @example 1920
   */
  width: number;
}

/**
 * The format of the output file.
 *
 * @since 0.1.0
 */
export enum ImageFormat {
  /**
   * JPEG (`image/jpeg`).
   *
   * @since 0.1.0
   */
  Jpeg = 'JPEG',
  /**
   * PNG (`image/png`).
   *
   * @since 0.1.0
   */
  Png = 'PNG',
  /**
   * WebP (`image/webp`).
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   */
  Webp = 'WEBP',
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The file was not found.
   *
   * @since 0.1.0
   */
  FileNotFound = 'FILE_NOT_FOUND',
  /**
   * The image could not be transformed.
   *
   * @since 0.1.0
   */
  TransformFailed = 'TRANSFORM_FAILED',
  /**
   * The image format is not supported.
   *
   * This is thrown when the source image cannot be decoded on the
   * current platform (e.g. AVIF on Android 11 and older, HEIC on the web)
   * or when the requested output format cannot be encoded on the current
   * platform (e.g. WebP on iOS).
   *
   * @since 0.1.0
   */
  UnsupportedFormat = 'UNSUPPORTED_FORMAT',
}
