import { WebPlugin } from '@capacitor/core';

import type {
  CloudinaryPlugin,
  DownloadResourceOptions,
  DownloadResourceResult,
  InitializeOptions,
  UploadResourceOptions,
  UploadResourceResult,
} from './definitions';
import { CloudinaryUtils } from './utils';

export class CloudinaryWeb extends WebPlugin implements CloudinaryPlugin {
  public static readonly ERROR_NOT_INITIALIZED = 'Plugin is not initialized.';
  public static readonly ERROR_FILE_MISSING = 'blob must be provided.';
  private readonly cloudinaryUtils = new CloudinaryUtils();

  private cloudName?: string;

  public async initialize(options: InitializeOptions): Promise<void> {
    this.cloudName = options.cloudName;
  }

  public async uploadResource(
    options: UploadResourceOptions,
  ): Promise<UploadResourceResult> {
    if (!this.cloudName) {
      throw new Error(CloudinaryWeb.ERROR_NOT_INITIALIZED);
    }
    if (!options.blob) {
      throw new Error(CloudinaryWeb.ERROR_FILE_MISSING);
    }
    return this.cloudinaryUtils.uploadResourceAsBlob({
      ...options,
      cloudName: this.cloudName,
      blob: options.blob,
    });
  }

  public async downloadResource(
    options: DownloadResourceOptions,
  ): Promise<DownloadResourceResult> {
    return this.cloudinaryUtils.downloadResourceAsBlob({ ...options });
  }
}
