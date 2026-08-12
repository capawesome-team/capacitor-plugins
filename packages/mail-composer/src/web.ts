import { WebPlugin } from '@capacitor/core';

import type {
  CanComposeMailResult,
  ComposeMailOptions,
  ComposeMailResult,
  MailComposerPlugin,
} from './definitions';

export class MailComposerWeb extends WebPlugin implements MailComposerPlugin {
  private static readonly errorAttachmentsNotSupported =
    'Attachments are not supported on the web.';

  async canComposeMail(): Promise<CanComposeMailResult> {
    return { canCompose: true };
  }

  async composeMail(options: ComposeMailOptions): Promise<ComposeMailResult> {
    if (options.attachments && options.attachments.length > 0) {
      throw new Error(MailComposerWeb.errorAttachmentsNotSupported);
    }
    const url = this.createMailtoUrl(options);
    window.open(url, '_self');
    return { status: 'unknown' };
  }

  private createMailtoUrl(options: ComposeMailOptions): string {
    const to = (options.to ?? []).join(',');
    const params = new URLSearchParams();
    if (options.cc && options.cc.length > 0) {
      params.set('cc', options.cc.join(','));
    }
    if (options.bcc && options.bcc.length > 0) {
      params.set('bcc', options.bcc.join(','));
    }
    if (options.subject) {
      params.set('subject', options.subject);
    }
    if (options.body) {
      params.set('body', options.body);
    }
    const query = params.toString();
    return `mailto:${encodeURIComponent(to)}${query ? `?${query}` : ''}`;
  }
}
