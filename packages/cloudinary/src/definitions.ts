export interface CloudinaryPlugin {
  /**
   * Initialize the plugin.
   *
   * This method must be called once before all other methods.
   *
   * @since 0.0.1
   */
  initialize(options: InitializeOptions): Promise<void>;
  /**
   * Upload a file to Cloudinary.
   *
   * **Note**: Currently, only unsigned uploads are supported.
   *
   * @since 0.0.1
   */
  uploadResource(options: UploadResourceOptions): Promise<UploadResourceResult>;
  /**
   * Download a file from Cloudinary.
   *
   * On **Android**, the file will be downloaded to the `Downloads` directory.
   * On **iOS**, the file will be downloaded to the temporary directory.
   *
   * It is recommended to copy the file to a permanent location for
   * further processing after downloading.
   *
   * @since 0.0.3
   */
  downloadResource(
    options: DownloadResourceOptions,
  ): Promise<DownloadResourceResult>;
}

/**
 * @since 0.0.1
 */
export interface InitializeOptions {
  /**
   * The cloud name of your app which you can find in the Cloudinary Management Console.
   *
   * @since 0.0.1
   */
  cloudName: string;
}

/**
 * @since 0.0.1
 */
export interface UploadResourceOptions {
  /**
   * The resource type to upload.
   *
   * @since 0.0.1
   */
  resourceType: ResourceType;
  /**
   * The file to upload.
   *
   * Only available on Web.
   *
   * @since 0.0.1
   */
  blob?: Blob;
  /**
   * The selected upload preset.
   *
   * @since 0.0.1
   * @see https://cloudinary.com/documentation/upload_presets
   */
  uploadPreset: string;
  /**
   * The path of the file to upload.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  path?: string;
  /**
   * Assign a unique public identifier to the resource.
   *
   * @since 0.0.1
   * @see https://cloudinary.com/documentation/upload_images#public_id
   */
  publicId?: string;
}

/**
 * @since 0.0.1
 */
export interface UploadResourceResult {
  /**
   * The unique asset identifier of the uploaded resource.
   *
   * Only available on Android and Web.
   *
   * @since 0.0.1
   */
  assetId?: string;
  /**
   * The number of bytes of the uploaded resource.
   *
   * Only available on Android and Web.
   *
   * @since 0.0.1
   */
  bytes?: number;
  /**
   * The timestamp at which the resource was uploaded.
   *
   * @since 0.0.1
   * @example '2022-12-02T11:03:17Z'
   */
  createdAt: string;
  /**
   * The duration of the uploaded resource in seconds.
   *
   * @since 0.1.5
   * @example 13.4134
   */
  duration?: number;
  /**
   * The format of the uploaded resource.
   *
   * @since 0.0.1
   * @example 'mp4'
   */
  format: string;
  /**
   * The height of the uploaded resource.
   *
   * @since 0.1.4
   * @example 1080
   */
  height?: number;
  /**
   * The original filename of the uploaded resource.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   * @example 'my_video'
   */
  originalFilename?: string;
  /**
   * The resource type of the uploaded resource.
   *
   * @since 0.0.1
   */
  resourceType: ResourceType;
  /**
   * The unique public identifier of the uploaded resource.
   *
   * @since 0.0.1
   */
  publicId: string;
  /**
   * The url of the uploaded resource.
   *
   * @since 0.0.1
   */
  url: string;
  /**
   * The secure url of the uploaded resource.
   *
   * @since 5.1.0
   */
  secureUrl: string;
  /**
   * The width of the uploaded resource.
   *
   * @since 0.1.4
   * @example 1920
   */
  width?: number;
}

/**
 * @since 0.0.3
 */
export interface DownloadResourceOptions {
  /**
   * The url of the resource to download.
   *
   * @since 0.0.3
   */
  url: string;
}

/**
 * @since 0.0.3
 */
export interface DownloadResourceResult {
  /**
   * The path of the downloaded resource where it is stored on the device.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.3
   */
  path?: string;
  /**
   * The downloaded resource as a blob.
   *
   * Only available on Web.
   *
   * @since 0.0.1
   */
  blob?: Blob;
}

/**
 * @since 0.0.1
 */
export enum ResourceType {
  /**
   * @since 0.0.1
   */
  Image = 'image',
  /**
   * @since 0.0.1
   */
  Video = 'video',
  /**
   * @since 0.0.1
   */
  Raw = 'raw',
}

/**
 * @since 0.1.1
 */
export interface ICloudinaryUtils {
  /**
   * Upload a file to Cloudinary as a blob.
   *
   * @since 0.1.1
   */
  uploadResourceAsBlob(
    options: UploadResourceAsBlobOptions,
  ): Promise<UploadResourceResult>;
  /**
   * Download a file from Cloudinary as a blob.
   *
   * @since 0.1.1
   */
  downloadResourceAsBlob(
    options: DownloadResourceAsBlobOptions,
  ): Promise<DownloadResourceAsBlobResult>;
}

/**
 * @since 0.1.1
 */
export interface UploadResourceAsBlobOptions {
  /**
   * The cloud name of your app which you can find in the Cloudinary Management Console.
   *
   * @since 0.1.1
   */
  cloudName: string;
  /**
   * The resource type to upload.
   *
   * @since 0.1.1
   */
  resourceType: ResourceType;
  /**
   * The file to upload.
   *
   * @since 0.1.1
   */
  blob: Blob;
  /**
   * The selected upload preset.
   *
   * @since 0.1.1
   * @see https://cloudinary.com/documentation/upload_presets
   */
  uploadPreset: string;
  /**
   * Assign a unique public identifier to the resource.
   *
   * @since 0.1.1
   * @see https://cloudinary.com/documentation/upload_images#public_id
   */
  publicId?: string;
}

/**
 * @since 0.1.1
 */
export type UploadResourceAsBlobResult = UploadResourceResult;

/**
 * @since 0.1.1
 */
export type DownloadResourceAsBlobOptions = DownloadResourceOptions;

/**
 * @since 0.1.1
 */
export interface DownloadResourceAsBlobResult {
  /**
   * The downloaded resource as a blob.
   *
   * @since 0.1.1
   */
  blob: Blob;
}
