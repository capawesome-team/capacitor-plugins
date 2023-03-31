import { WebPlugin } from '@capacitor/core';

import type {
  ConvertHeicToJpegOptions,
  ConvertHeicToJpegResult,
  File as FileModel,
  FilePickerPlugin,
  PickFilesOptions,
  PickFilesResult,
  PickImagesOptions,
  PickImagesResult,
  PickMediaOptions,
  PickMediaResult,
  PickVideosOptions,
  PickVideosResult,
} from './definitions';

export class FilePickerWeb extends WebPlugin implements FilePickerPlugin {
  public readonly ERROR_PICK_FILE_CANCELED = 'pickFiles canceled.';

  public async convertHeicToJpeg(
    _options: ConvertHeicToJpegOptions,
  ): Promise<ConvertHeicToJpegResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async pickFiles(options?: PickFilesOptions): Promise<PickFilesResult> {
    const pickedFiles = await this.openFilePicker(options);
    if (!pickedFiles) {
      throw new Error(this.ERROR_PICK_FILE_CANCELED);
    }
    const result: PickFilesResult = {
      files: [],
    };
    for (const pickedFile of pickedFiles) {
      const file: FileModel = {
        blob: pickedFile,
        modifiedAt: pickedFile.lastModified,
        mimeType: this.getMimeTypeFromUrl(pickedFile),
        name: this.getNameFromUrl(pickedFile),
        path: undefined,
        size: this.getSizeFromUrl(pickedFile),
      };
      if (options?.readData) {
        file.data = await this.getDataFromFile(pickedFile);
      }
      result.files.push(file);
    }
    return result;
  }

  public async pickImages(
    _options?: PickImagesOptions,
  ): Promise<PickImagesResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async pickMedia(
    _options?: PickMediaOptions,
  ): Promise<PickMediaResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async pickVideos(
    _options?: PickVideosOptions,
  ): Promise<PickVideosResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  private async openFilePicker(
    options?: PickFilesOptions,
  ): Promise<File[] | undefined> {
    const accept = options?.types?.join(',') || '';
    const multiple = !!options?.multiple;
    return new Promise(resolve => {
      let onChangeFired = false;
      const input = document.createElement('input');
      input.type = 'file';
      input.accept = accept;
      input.multiple = multiple;
      input.addEventListener(
        'change',
        () => {
          onChangeFired = true;
          const files = Array.from(input.files || []);
          resolve(files);
        },
        { once: true },
      );
      // Workaround to detect when Cancel is selected in the File Selection dialog box.
      window.addEventListener(
        'focus',
        async () => {
          await this.wait(1000);
          if (onChangeFired) {
            return;
          }
          resolve(undefined);
        },
        { once: true },
      );
      input.click();
    });
  }

  private async getDataFromFile(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        const result = typeof reader.result === 'string' ? reader.result : '';
        const splittedResult = result.split('base64,');
        const base64 = splittedResult[1] || '';
        resolve(base64);
      };
      reader.onerror = error => {
        reject(error);
      };
    });
  }

  private getNameFromUrl(file: File): string {
    return file.name;
  }

  private getMimeTypeFromUrl(file: File): string {
    return file.type;
  }

  private getSizeFromUrl(file: File): number {
    return file.size;
  }

  private async wait(delayMs: number): Promise<void> {
    return new Promise(resolve => setTimeout(resolve, delayMs));
  }
}
