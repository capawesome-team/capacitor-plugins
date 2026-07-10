import { WebPlugin } from '@capacitor/core';

import type {
  AccessibilityPreferencesPlugin,
  GetPreferencesResult,
} from './definitions';

export class AccessibilityPreferencesWeb
  extends WebPlugin
  implements AccessibilityPreferencesPlugin
{
  async getPreferences(): Promise<GetPreferencesResult> {
    return {
      fontScale: 1.0,
      isReduceMotionEnabled: this.matchesMediaQuery(
        '(prefers-reduced-motion: reduce)',
      ),
      isBoldTextEnabled: null,
      isInvertColorsEnabled: null,
      isReduceTransparencyEnabled: null,
      isHighContrastEnabled: this.matchesMediaQuery('(prefers-contrast: more)'),
    };
  }

  private matchesMediaQuery(query: string): boolean {
    return window.matchMedia(query).matches;
  }
}
