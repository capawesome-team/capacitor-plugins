import type {
  ResourceType,
  ICloudinaryUtils,
  UploadResourceAsBlobOptions,
  DownloadResourceAsBlobOptions,
  DownloadResourceAsBlobResult,
  UploadResourceAsBlobResult,
} from './definitions';

export class CloudinaryUtils implements ICloudinaryUtils {
  public async uploadResourceAsBlob(
    options: UploadResourceAsBlobOptions,
  ): Promise<UploadResourceAsBlobResult> {
    const uniqueUploadId = this.generateUniqueId();
    const chunkSize = 1024 * 1024 * 10; // 10 Megabytes
    const totalSize = options.blob.size;
    const chunks: { start: number; end: number; blob: Blob }[] = [];

    let start = 0;
    let end = Math.min(chunkSize, totalSize);
    while (start < totalSize) {
      const blob = options.blob.slice(start, end);
      chunks.push({ start, end, blob });
      start = end;
      end = Math.min(start + chunkSize, totalSize);
    }
    let response: any;
    for (const chunk of chunks) {
      const { start, end, blob } = chunk;
      response = await this.uploadResourceChunk(
        options,
        uniqueUploadId,
        start,
        end - 1,
        totalSize,
        blob,
      );
    }
    return {
      assetId: response.asset_id,
      bytes: response.bytes,
      createdAt: response.created_at,
      duration: response.duration,
      format: response.format,
      height: response.height,
      originalFilename: response.original_filename,
      resourceType: response.resource_type,
      publicId: response.public_id,
      url: response.secure_url,
      width: response.width,
    };
  }

  public async downloadResourceAsBlob(
    options: DownloadResourceAsBlobOptions,
  ): Promise<DownloadResourceAsBlobResult> {
    const blob = await fetch(options.url).then(res => res.blob());
    return {
      blob,
    };
  }

  private async uploadResourceChunk(
    options: {
      cloudName: string;
      resourceType: ResourceType;
      blob: Blob;
      uploadPreset: string;
      publicId?: string;
    },
    uniqueUploadId: string,
    start: number,
    end: number,
    size: number,
    chunk: Blob,
  ): Promise<any> {
    const formData = new FormData();
    formData.append('file', chunk);
    formData.append('upload_preset', options.uploadPreset);
    formData.append('cloud_name', options.cloudName);
    if (options.publicId) {
      formData.append('public_id', options.publicId);
    }
    return fetch(
      `https://api.cloudinary.com/v1_1/${options.cloudName}/${options.resourceType}/upload`,
      {
        method: 'PUT',
        body: formData,
        headers: {
          'X-Unique-Upload-Id': uniqueUploadId,
          'Content-Range': `bytes ${start}-${end}/${size}`,
        },
      },
    ).then(async response => {
      if (!response.ok) {
        throw new Error(
          `Request failed with status ${response.status}: ${response.statusText}`,
        );
      }
      return response.json();
    });
  }

  private generateUniqueId(): string {
    return Date.now().toString(36) + Math.random().toString(36).substring(2);
  }
}
