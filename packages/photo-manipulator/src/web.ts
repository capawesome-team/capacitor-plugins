import { CapacitorException, WebPlugin } from '@capacitor/core';

import type {
  CropOptions,
  GetInfoOptions,
  GetInfoResult,
  PhotoManipulatorPlugin,
  TransformOptions,
  TransformResult,
} from './definitions';
import { ErrorCode, ImageFormat } from './definitions';

export class PhotoManipulatorWeb
  extends WebPlugin
  implements PhotoManipulatorPlugin
{
  private static errorCropInvalid =
    'crop must be within the bounds of the source image.';
  private static errorFileNotFound = 'The file was not found.';
  private static errorFormatInvalid =
    'format must be one of the supported values.';
  private static errorPathMissing = 'path must be provided.';
  private static errorQualityInvalid = 'quality must be between 0 and 1.';
  private static errorResizeInvalid =
    'resize must contain a positive width or height.';
  private static errorRotateInvalid = 'rotate must be 90, 180 or 270.';
  private static errorTransformFailed = 'The image could not be transformed.';
  private static errorUnsupportedFormat = 'The image format is not supported.';

  async getInfo(options: GetInfoOptions): Promise<GetInfoResult> {
    const path = this.getPath(options.path);
    const blob = await this.fetchBlob(path);
    const bitmap = await this.decodeBlob(blob);
    const height = bitmap.height;
    const width = bitmap.width;
    bitmap.close();
    return {
      format: this.getFormatFromMimeType(blob.type),
      height,
      width,
    };
  }

  async transform(options: TransformOptions): Promise<TransformResult> {
    const path = this.getPath(options.path);
    const flipHorizontal = options.flipHorizontal === true;
    const flipVertical = options.flipVertical === true;
    const format = this.getFormat(options.format);
    const quality = this.getQuality(options.quality);
    const rotate = this.getRotate(options.rotate);
    if (options.resize) {
      const { height, width } = options.resize;
      if (
        (height === undefined && width === undefined) ||
        (height !== undefined && height <= 0) ||
        (width !== undefined && width <= 0)
      ) {
        throw new CapacitorException(PhotoManipulatorWeb.errorResizeInvalid);
      }
    }
    const blob = await this.fetchBlob(path);
    const bitmap = await this.decodeBlob(blob);
    try {
      const crop = this.getCrop(options.crop, bitmap.width, bitmap.height);
      let targetWidth = crop.width;
      let targetHeight = crop.height;
      if (options.resize) {
        const { height, width } = options.resize;
        if (width !== undefined && height !== undefined) {
          targetWidth = Math.round(width);
          targetHeight = Math.round(height);
        } else if (width !== undefined) {
          targetWidth = Math.round(width);
          targetHeight = Math.max(
            1,
            Math.round((crop.height * width) / crop.width),
          );
        } else if (height !== undefined) {
          targetHeight = Math.round(height);
          targetWidth = Math.max(
            1,
            Math.round((crop.width * height) / crop.height),
          );
        }
      }
      const swapDimensions = rotate === 90 || rotate === 270;
      const canvas = document.createElement('canvas');
      canvas.width = swapDimensions ? targetHeight : targetWidth;
      canvas.height = swapDimensions ? targetWidth : targetHeight;
      const context = canvas.getContext('2d');
      if (!context) {
        throw new CapacitorException(
          PhotoManipulatorWeb.errorTransformFailed,
          undefined,
          { code: ErrorCode.TransformFailed },
        );
      }
      context.imageSmoothingEnabled = true;
      context.imageSmoothingQuality = 'high';
      context.translate(canvas.width / 2, canvas.height / 2);
      if (flipHorizontal) {
        context.scale(-1, 1);
      }
      if (flipVertical) {
        context.scale(1, -1);
      }
      context.rotate((rotate * Math.PI) / 180);
      context.drawImage(
        bitmap,
        crop.x,
        crop.y,
        crop.width,
        crop.height,
        -targetWidth / 2,
        -targetHeight / 2,
        targetWidth,
        targetHeight,
      );
      const outputBlob = await this.encodeCanvas(canvas, format, quality);
      return {
        height: canvas.height,
        path: URL.createObjectURL(outputBlob),
        width: canvas.width,
      };
    } finally {
      bitmap.close();
    }
  }

  private async decodeBlob(blob: Blob): Promise<ImageBitmap> {
    try {
      return await createImageBitmap(blob);
    } catch {
      throw new CapacitorException(
        PhotoManipulatorWeb.errorUnsupportedFormat,
        undefined,
        { code: ErrorCode.UnsupportedFormat },
      );
    }
  }

  private async encodeCanvas(
    canvas: HTMLCanvasElement,
    format: ImageFormat,
    quality: number,
  ): Promise<Blob> {
    const mimeType = this.getMimeTypeFromFormat(format);
    const blob = await new Promise<Blob | null>(resolve =>
      canvas.toBlob(resolve, mimeType, quality),
    );
    if (!blob) {
      throw new CapacitorException(
        PhotoManipulatorWeb.errorTransformFailed,
        undefined,
        { code: ErrorCode.TransformFailed },
      );
    }
    if (blob.type !== mimeType) {
      throw new CapacitorException(
        PhotoManipulatorWeb.errorUnsupportedFormat,
        undefined,
        { code: ErrorCode.UnsupportedFormat },
      );
    }
    return blob;
  }

  private async fetchBlob(path: string): Promise<Blob> {
    let response: Response;
    try {
      response = await fetch(path);
    } catch {
      throw new CapacitorException(
        PhotoManipulatorWeb.errorFileNotFound,
        undefined,
        { code: ErrorCode.FileNotFound },
      );
    }
    if (!response.ok) {
      throw new CapacitorException(
        PhotoManipulatorWeb.errorFileNotFound,
        undefined,
        { code: ErrorCode.FileNotFound },
      );
    }
    return await response.blob();
  }

  private getCrop(
    crop: CropOptions | undefined,
    sourceWidth: number,
    sourceHeight: number,
  ): CropOptions {
    if (!crop) {
      return { height: sourceHeight, width: sourceWidth, x: 0, y: 0 };
    }
    if (
      crop.x < 0 ||
      crop.y < 0 ||
      crop.width <= 0 ||
      crop.height <= 0 ||
      crop.x + crop.width > sourceWidth ||
      crop.y + crop.height > sourceHeight
    ) {
      throw new CapacitorException(PhotoManipulatorWeb.errorCropInvalid);
    }
    return crop;
  }

  private getFormat(format: ImageFormat | undefined): ImageFormat {
    if (format === undefined) {
      return ImageFormat.Jpeg;
    }
    if (!Object.values(ImageFormat).includes(format)) {
      throw new CapacitorException(PhotoManipulatorWeb.errorFormatInvalid);
    }
    return format;
  }

  private getFormatFromMimeType(mimeType: string): string | null {
    if (!mimeType.startsWith('image/')) {
      return null;
    }
    const format = mimeType.substring('image/'.length).toLowerCase();
    return format === 'jpg' ? 'jpeg' : format;
  }

  private getMimeTypeFromFormat(format: ImageFormat): string {
    switch (format) {
      case ImageFormat.Png:
        return 'image/png';
      case ImageFormat.Webp:
        return 'image/webp';
      default:
        return 'image/jpeg';
    }
  }

  private getPath(path: string | undefined): string {
    if (!path) {
      throw new CapacitorException(PhotoManipulatorWeb.errorPathMissing);
    }
    return path;
  }

  private getQuality(quality: number | undefined): number {
    if (quality === undefined) {
      return 0.9;
    }
    if (quality < 0 || quality > 1) {
      throw new CapacitorException(PhotoManipulatorWeb.errorQualityInvalid);
    }
    return quality;
  }

  private getRotate(rotate: number | undefined): number {
    if (rotate === undefined) {
      return 0;
    }
    if (rotate !== 0 && rotate !== 90 && rotate !== 180 && rotate !== 270) {
      throw new CapacitorException(PhotoManipulatorWeb.errorRotateInvalid);
    }
    return rotate;
  }
}
