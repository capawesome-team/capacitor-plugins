import { WebPlugin } from '@capacitor/core';

import type { FileOpenerPlugin, OpenFileOptions } from './definitions';

export class FileOpenerWeb extends WebPlugin implements FileOpenerPlugin {
  public openFile(_options: OpenFileOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
