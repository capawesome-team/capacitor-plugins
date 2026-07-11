import { CapacitorException, WebPlugin } from '@capacitor/core';
import { Crisp as CrispSdk, EventsColors } from 'crisp-sdk-web';

import type {
  ConfigureOptions,
  CrispPlugin,
  OpenHelpdeskArticleOptions,
  PushSessionEventOptions,
  SetCompanyOptions,
  SetSessionBoolOptions,
  SetSessionIntOptions,
  SetSessionSegmentOptions,
  SetSessionStringOptions,
  SetTokenIdOptions,
  SetUserOptions,
} from './definitions';
import { ErrorCode } from './definitions';

export class CrispWeb extends WebPlugin implements CrispPlugin {
  private static readonly errorNotConfigured =
    'Crisp is not configured. Call configure() first.';

  private configured = false;

  async configure(options: ConfigureOptions): Promise<void> {
    CrispSdk.configure(options.websiteId, {
      tokenId: options.tokenId,
    });
    this.configured = true;
    this.registerEventListeners();
  }

  async handlePushNotification(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isCrispPushNotification(): Promise<never> {
    throw this.unimplemented('Not implemented on web.');
  }

  async openChat(): Promise<void> {
    this.throwIfNotConfigured();
    CrispSdk.chat.open();
  }

  async openHelpdeskArticle(
    options: OpenHelpdeskArticleOptions,
  ): Promise<void> {
    this.throwIfNotConfigured();
    CrispSdk.chat.openHelpdeskArticle(
      options.locale,
      options.id,
      options.title,
      options.category,
    );
  }

  async pushSessionEvent(options: PushSessionEventOptions): Promise<void> {
    this.throwIfNotConfigured();
    const color = options.color
      ? (options.color.toLowerCase() as EventsColors)
      : EventsColors.Blue;
    CrispSdk.session.pushEvent(options.name, {}, color);
  }

  async resetSession(): Promise<void> {
    this.throwIfNotConfigured();
    CrispSdk.session.reset();
  }

  async searchHelpdesk(): Promise<void> {
    this.throwIfNotConfigured();
    CrispSdk.chat.setHelpdeskView();
    CrispSdk.chat.open();
  }

  async setCompany(options: SetCompanyOptions): Promise<void> {
    this.throwIfNotConfigured();
    CrispSdk.user.setCompany(options.name, {
      description: options.description,
      employment: options.employment,
      geolocation: options.geolocation,
      url: options.url,
    });
  }

  async setNotificationsEnabled(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setSessionBool(options: SetSessionBoolOptions): Promise<void> {
    this.throwIfNotConfigured();
    CrispSdk.session.setData({ [options.key]: options.value });
  }

  async setSessionInt(options: SetSessionIntOptions): Promise<void> {
    this.throwIfNotConfigured();
    CrispSdk.session.setData({ [options.key]: options.value });
  }

  async setSessionSegment(options: SetSessionSegmentOptions): Promise<void> {
    this.throwIfNotConfigured();
    CrispSdk.session.setSegments([options.segment], false);
  }

  async setSessionString(options: SetSessionStringOptions): Promise<void> {
    this.throwIfNotConfigured();
    CrispSdk.session.setData({ [options.key]: options.value });
  }

  async setShouldPromptForNotificationPermission(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setTokenId(options: SetTokenIdOptions): Promise<void> {
    CrispSdk.setTokenId(options.tokenId);
  }

  async setUser(options: SetUserOptions): Promise<void> {
    this.throwIfNotConfigured();
    if (options.email !== undefined) {
      CrispSdk.user.setEmail(options.email, options.emailSignature);
    }
    if (options.nickname !== undefined) {
      CrispSdk.user.setNickname(options.nickname);
    }
    if (options.phone !== undefined) {
      CrispSdk.user.setPhone(options.phone);
    }
    if (options.avatarUrl !== undefined) {
      CrispSdk.user.setAvatar(options.avatarUrl);
    }
  }

  private extractMessageContent(message: unknown): string | null {
    if (typeof message === 'string') {
      return message;
    }
    if (message && typeof message === 'object' && 'content' in message) {
      const content = (message as { content: unknown }).content;
      return typeof content === 'string' ? content : null;
    }
    return null;
  }

  private registerEventListeners(): void {
    CrispSdk.chat.onChatOpened(() => {
      this.notifyListeners('chatOpened', {});
    });
    CrispSdk.chat.onChatClosed(() => {
      this.notifyListeners('chatClosed', {});
    });
    CrispSdk.message.onMessageSent((message: unknown) => {
      this.notifyListeners('messageSent', {
        content: this.extractMessageContent(message),
      });
    });
    CrispSdk.message.onMessageReceived((message: unknown) => {
      this.notifyListeners('messageReceived', {
        content: this.extractMessageContent(message),
      });
    });
    CrispSdk.session.onLoaded((sessionId: unknown) => {
      this.notifyListeners('sessionLoaded', {
        sessionId: typeof sessionId === 'string' ? sessionId : '',
      });
    });
  }

  private throwIfNotConfigured(): void {
    if (!this.configured) {
      throw new CapacitorException(CrispWeb.errorNotConfigured, undefined, {
        code: ErrorCode.NotConfigured,
      });
    }
  }
}
