import { CapacitorException, WebPlugin } from '@capacitor/core';

import { ClipboardContentType, ErrorCode } from './definitions';
import type { ClipboardPlugin, ReadResult, WriteOptions } from './definitions';

export class ClipboardWeb extends WebPlugin implements ClipboardPlugin {
  private static readonly errorContentMissing =
    'One of text, html, image or url must be provided.';
  private static readonly errorEmptyClipboard = 'The clipboard is empty.';
  private static readonly errorReadFailed =
    'The clipboard content could not be read.';
  private static readonly errorRichContentNotSupported =
    'This browser does not support writing rich content to the clipboard.';
  private static readonly errorWriteFailed =
    'The content could not be written to the clipboard.';

  async read(): Promise<ReadResult> {
    if (navigator.clipboard.read) {
      try {
        const items = await navigator.clipboard.read();
        for (const item of items) {
          const imageType = item.types.find(type => type.startsWith('image/'));
          if (imageType) {
            const blob = await item.getType(imageType);
            const value = await ClipboardWeb.convertBlobToDataUrl(blob);
            return { type: ClipboardContentType.Image, value };
          }
        }
        for (const item of items) {
          if (item.types.includes('text/html')) {
            const blob = await item.getType('text/html');
            const value = await blob.text();
            return { type: ClipboardContentType.Html, value };
          }
        }
      } catch (error) {
        throw ClipboardWeb.createException(
          ClipboardWeb.errorReadFailed,
          ErrorCode.ReadFailed,
        );
      }
    }
    let value: string;
    try {
      value = await navigator.clipboard.readText();
    } catch (error) {
      throw ClipboardWeb.createException(
        ClipboardWeb.errorReadFailed,
        ErrorCode.ReadFailed,
      );
    }
    if (!value) {
      throw ClipboardWeb.createException(
        ClipboardWeb.errorEmptyClipboard,
        ErrorCode.EmptyClipboard,
      );
    }
    return { type: ClipboardWeb.resolveTextType(value), value };
  }

  async write(options: WriteOptions): Promise<void> {
    if (
      options.html === undefined &&
      options.image === undefined &&
      options.text === undefined &&
      options.url === undefined
    ) {
      throw new Error(ClipboardWeb.errorContentMissing);
    }
    try {
      if (options.image !== undefined) {
        await ClipboardWeb.writeRichContent({
          'image/png': await ClipboardWeb.convertDataUrlToBlob(options.image),
        });
      } else if (options.html !== undefined) {
        const data: Record<string, Blob> = {
          'text/html': new Blob([options.html], { type: 'text/html' }),
        };
        if (options.text !== undefined) {
          data['text/plain'] = new Blob([options.text], { type: 'text/plain' });
        }
        await ClipboardWeb.writeRichContent(data);
      } else if (options.url !== undefined) {
        await navigator.clipboard.writeText(options.url);
      } else if (options.text !== undefined) {
        await navigator.clipboard.writeText(options.text);
      }
    } catch (error) {
      if (error instanceof CapacitorException) {
        throw error;
      }
      throw ClipboardWeb.createException(
        ClipboardWeb.errorWriteFailed,
        ErrorCode.WriteFailed,
      );
    }
  }

  private static convertBlobToDataUrl(blob: Blob): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onloadend = () => resolve(reader.result as string);
      reader.onerror = () => reject(reader.error);
      reader.readAsDataURL(blob);
    });
  }

  private static async convertDataUrlToBlob(dataUrl: string): Promise<Blob> {
    const response = await fetch(dataUrl);
    return response.blob();
  }

  private static createException(
    message: string,
    code: ErrorCode,
  ): CapacitorException {
    return new CapacitorException(message, undefined, { code });
  }

  private static resolveTextType(value: string): ClipboardContentType {
    if (value.startsWith('http://') || value.startsWith('https://')) {
      return ClipboardContentType.Url;
    }
    return ClipboardContentType.Text;
  }

  private static async writeRichContent(
    data: Record<string, Blob>,
  ): Promise<void> {
    if (typeof ClipboardItem === 'undefined') {
      throw ClipboardWeb.createException(
        ClipboardWeb.errorRichContentNotSupported,
        ErrorCode.WriteFailed,
      );
    }
    await navigator.clipboard.write([new ClipboardItem(data)]);
  }
}
