import { WebPlugin } from '@capacitor/core';

import type {
  ExecuteScriptResult,
  GetCookiesResult,
  InAppBrowserPlugin,
  OpenInExternalBrowserOptions,
} from './definitions';

export class InAppBrowserWeb extends WebPlugin implements InAppBrowserPlugin {
  private static errorUrlMissing = 'url must be provided.';

  async clearCache(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async clearSessionData(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async close(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async executeScript(): Promise<ExecuteScriptResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getCookies(): Promise<GetCookiesResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async openInExternalBrowser(
    options: OpenInExternalBrowserOptions,
  ): Promise<void> {
    if (!options.url) {
      throw new Error(InAppBrowserWeb.errorUrlMissing);
    }
    window.open(options.url, '_blank');
  }

  async openInSystemBrowser(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async openInWebView(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async postMessage(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
