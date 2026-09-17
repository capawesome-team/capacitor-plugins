import { CapacitorException, WebPlugin } from '@capacitor/core';
import IntercomMessenger, {
  hide,
  hideNotifications,
  onHide,
  onShow,
  onUnreadCountChange,
  shutdown,
  showArticle,
  showConversation,
  showNewMessage,
  showSpace,
  startSurvey,
  trackEvent,
  update,
} from '@intercom/messenger-js-sdk';

import type {
  GetUnreadConversationCountResult,
  InitializeOptions,
  IntercomPlugin,
  LogEventOptions,
  LoginUserOptions,
  PresentContentOptions,
  PresentMessageComposerOptions,
  PresentOptions,
  SetInAppMessagesVisibleOptions,
  SetLauncherVisibleOptions,
  SetUserHashOptions,
  SetUserJwtOptions,
  UpdateUserOptions,
} from './definitions';
import { ErrorCode } from './definitions';

type BootSettings = Parameters<typeof IntercomMessenger>[0];
type UpdateArgs = Parameters<typeof update>[0];

export class IntercomWeb extends WebPlugin implements IntercomPlugin {
  private static readonly errorNotInitialized =
    'Intercom is not initialized. Call initialize() first.';
  private static readonly errorNoWebEquivalent =
    'This content type is not available on the web platform.';

  private appId?: string;
  private initialized = false;
  private unreadConversationCount = 0;
  private userHash?: string;
  private userJwt?: string;

  async getUnreadConversationCount(): Promise<GetUnreadConversationCountResult> {
    return { count: this.unreadConversationCount };
  }

  async handlePushNotification(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async hide(): Promise<void> {
    this.throwIfNotInitialized();
    hide();
  }

  async initialize(options: InitializeOptions): Promise<void> {
    this.appId = options.appId;
    this.boot();
    this.registerEventListeners();
    this.initialized = true;
  }

  async isIntercomPushNotification(): Promise<never> {
    throw this.unimplemented('Not implemented on web.');
  }

  async logEvent(options: LogEventOptions): Promise<void> {
    this.throwIfNotInitialized();
    trackEvent(options.name, options.data);
  }

  async loginUnidentifiedUser(): Promise<void> {
    this.throwIfNotInitialized();
    this.boot();
  }

  async loginUser(options: LoginUserOptions): Promise<void> {
    this.throwIfNotInitialized();
    this.boot(options);
  }

  async logout(): Promise<void> {
    this.throwIfNotInitialized();
    shutdown();
    this.boot();
  }

  async present(options?: PresentOptions): Promise<void> {
    this.throwIfNotInitialized();
    showSpace(IntercomWeb.mapSpace(options?.space ?? 'home'));
  }

  async presentContent(options: PresentContentOptions): Promise<void> {
    this.throwIfNotInitialized();
    switch (options.type) {
      case 'article':
        showArticle(options.id ?? '');
        break;
      case 'survey':
        startSurvey(options.id ?? '');
        break;
      case 'conversation':
        showConversation(options.id ?? '');
        break;
      default:
        throw this.unavailable(IntercomWeb.errorNoWebEquivalent);
    }
  }

  async presentMessageComposer(
    options?: PresentMessageComposerOptions,
  ): Promise<void> {
    this.throwIfNotInitialized();
    showNewMessage(options?.initialMessage ?? '');
  }

  async sendPushTokenToIntercom(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setBottomPadding(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setInAppMessagesVisible(
    options: SetInAppMessagesVisibleOptions,
  ): Promise<void> {
    this.throwIfNotInitialized();
    hideNotifications(!options.visible);
  }

  async setLauncherVisible(options: SetLauncherVisibleOptions): Promise<void> {
    this.throwIfNotInitialized();
    update({ hide_default_launcher: !options.visible } as UpdateArgs);
  }

  async setUserHash(options: SetUserHashOptions): Promise<void> {
    this.userHash = options.userHash;
  }

  async setUserJwt(options: SetUserJwtOptions): Promise<void> {
    this.userJwt = options.jwt;
  }

  async updateUser(options: UpdateUserOptions): Promise<void> {
    this.throwIfNotInitialized();
    const args: Record<string, unknown> = {};
    if (options.userId !== undefined) {
      args.user_id = options.userId;
    }
    if (options.email !== undefined) {
      args.email = options.email;
    }
    if (options.name !== undefined) {
      args.name = options.name;
    }
    if (options.phone !== undefined) {
      args.phone = options.phone;
    }
    if (options.languageOverride !== undefined) {
      args.language_override = options.languageOverride;
    }
    if (options.signedUpAt !== undefined) {
      args.created_at = options.signedUpAt;
    }
    if (options.unsubscribedFromEmails !== undefined) {
      args.unsubscribed_from_emails = options.unsubscribedFromEmails;
    }
    if (options.customAttributes !== undefined) {
      Object.assign(args, options.customAttributes);
    }
    if (options.companies !== undefined) {
      args.companies = options.companies.map(company => ({
        company_id: company.id,
        name: company.name,
        plan: company.plan,
        monthly_spend: company.monthlySpend,
        created_at: company.createdAt,
        ...company.customAttributes,
      }));
    }
    update(args as UpdateArgs);
  }

  private boot(user?: LoginUserOptions): void {
    const settings: Record<string, unknown> = { app_id: this.appId };
    if (user?.userId !== undefined) {
      settings.user_id = user.userId;
    }
    if (user?.email !== undefined) {
      settings.email = user.email;
    }
    if (this.userHash !== undefined) {
      settings.user_hash = this.userHash;
    }
    if (this.userJwt !== undefined) {
      settings.intercom_user_jwt = this.userJwt;
    }
    IntercomMessenger(settings as unknown as BootSettings);
  }

  private static mapSpace(space: string): string {
    switch (space) {
      case 'messages':
        return 'messages';
      case 'help-center':
        return 'help';
      case 'tickets':
        return 'tickets';
      default:
        return 'home';
    }
  }

  private registerEventListeners(): void {
    onUnreadCountChange((count: number) => {
      this.unreadConversationCount = count;
      this.notifyListeners('unreadConversationCountChange', { count });
    });
    onShow(() => {
      this.notifyListeners('messengerShown', {});
    });
    onHide(() => {
      this.notifyListeners('messengerHidden', {});
    });
  }

  private throwIfNotInitialized(): void {
    if (!this.initialized) {
      throw new CapacitorException(IntercomWeb.errorNotInitialized, undefined, {
        code: ErrorCode.NotInitialized,
      });
    }
  }
}
