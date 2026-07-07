import { WebPlugin } from '@capacitor/core';

import type {
  GetLocalesResult,
  GetSettingsResult,
  Locale,
  LocalizationPlugin,
  TextDirection,
} from './definitions';

interface LocaleTextInfo {
  direction: TextDirection;
}

interface LocaleWeekInfo {
  firstDay: number;
}

interface ExtendedLocale extends Intl.Locale {
  getTextInfo?: () => LocaleTextInfo;
  getWeekInfo?: () => LocaleWeekInfo;
  textInfo?: LocaleTextInfo;
  weekInfo?: LocaleWeekInfo;
}

export class LocalizationWeb extends WebPlugin implements LocalizationPlugin {
  private static readonly rtlLanguageCodes = [
    'ar',
    'ckb',
    'dv',
    'fa',
    'he',
    'ps',
    'sd',
    'ug',
    'ur',
    'yi',
  ];

  async getLocales(): Promise<GetLocalesResult> {
    const languageTags =
      navigator.languages && navigator.languages.length > 0
        ? navigator.languages
        : [navigator.language];
    const locales = languageTags.map(languageTag =>
      this.createLocale(languageTag),
    );
    return { locales };
  }

  async getSettings(): Promise<GetSettingsResult> {
    const timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
    const uses24HourClock = this.determineUses24HourClock();
    const firstDayOfWeek = this.determineFirstDayOfWeek(navigator.language);
    return { timeZone, uses24HourClock, firstDayOfWeek };
  }

  private createLocale(languageTag: string): Locale {
    const locale = new Intl.Locale(languageTag) as ExtendedLocale;
    const separators = this.determineSeparators(languageTag);
    return {
      languageTag,
      languageCode: locale.language,
      regionCode: locale.region ?? null,
      currencyCode: null,
      currencySymbol: null,
      decimalSeparator: separators.decimalSeparator,
      groupingSeparator: separators.groupingSeparator,
      textDirection: this.determineTextDirection(locale),
      measurementSystem: null,
    };
  }

  private determineFirstDayOfWeek(languageTag: string): number {
    const locale = new Intl.Locale(languageTag) as ExtendedLocale;
    const weekInfo = locale.getWeekInfo?.() ?? locale.weekInfo;
    return weekInfo?.firstDay ?? 1;
  }

  private determineSeparators(languageTag: string): {
    decimalSeparator: string | null;
    groupingSeparator: string | null;
  } {
    const parts = new Intl.NumberFormat(languageTag).formatToParts(1000000.1);
    const decimalSeparator =
      parts.find(part => part.type === 'decimal')?.value ?? null;
    const groupingSeparator =
      parts.find(part => part.type === 'group')?.value ?? null;
    return { decimalSeparator, groupingSeparator };
  }

  private determineTextDirection(locale: ExtendedLocale): TextDirection {
    const textInfo = locale.getTextInfo?.() ?? locale.textInfo;
    if (textInfo) {
      return textInfo.direction;
    }
    return LocalizationWeb.rtlLanguageCodes.includes(locale.language)
      ? 'rtl'
      : 'ltr';
  }

  private determineUses24HourClock(): boolean {
    const resolvedOptions = Intl.DateTimeFormat(navigator.language, {
      hour: 'numeric',
    }).resolvedOptions() as Intl.ResolvedDateTimeFormatOptions & {
      hourCycle?: string;
    };
    const hourCycle = resolvedOptions.hourCycle;
    return hourCycle === 'h23' || hourCycle === 'h24';
  }
}
