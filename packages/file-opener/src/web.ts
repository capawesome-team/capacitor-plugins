import { WebPlugin } from '@capacitor/core';

import type { FileOpenerPlugin, OpenFileOptions } from './definitions';

export class FileOpenerWeb extends WebPlugin implements FileOpenerPlugin {
  public static readonly ERROR_BLOB_MISSING = 'blob must be provided.';

  public async openFile(options: OpenFileOptions): Promise<void> {
    if (!options.blob) {
      throw new Error(FileOpenerWeb.ERROR_BLOB_MISSING);
    }
    const objectUrl = URL.createObjectURL(options.blob);
    window.open(objectUrl, '_blank');
  }
}
