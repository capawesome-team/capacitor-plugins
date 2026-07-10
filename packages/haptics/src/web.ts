import { WebPlugin } from '@capacitor/core';

import type {
  HapticEvent,
  HapticsPlugin,
  ImpactOptions,
  IsAvailableResult,
  NotificationOptions,
  PerformAndroidHapticOptions,
  PlayPatternOptions,
  VibrateOptions,
} from './definitions';
import { ImpactStyle, NotificationType } from './definitions';

export class HapticsWeb extends WebPlugin implements HapticsPlugin {
  private static readonly defaultVibrateDuration = 300;
  private static readonly errorEventsMissing = 'events must be provided.';
  private static readonly transientEventDuration = 30;

  async impact(options?: ImpactOptions): Promise<void> {
    const style = options?.style ?? ImpactStyle.Medium;
    this.vibrateOrThrow(this.getDurationForImpactStyle(style));
  }

  async isAvailable(): Promise<IsAvailableResult> {
    return { available: this.isVibrationSupported() };
  }

  async notification(options?: NotificationOptions): Promise<void> {
    const type = options?.type ?? NotificationType.Success;
    this.vibrateOrThrow(this.getPatternForNotificationType(type));
  }

  async performAndroidHaptic(
    _options: PerformAndroidHapticOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async playPattern(options: PlayPatternOptions): Promise<void> {
    const events = options.events;
    if (!events || events.length === 0) {
      throw new Error(HapticsWeb.errorEventsMissing);
    }
    this.vibrateOrThrow(this.createPatternFromEvents(events));
  }

  async selectionChanged(): Promise<void> {
    // No-op on the web.
  }

  async selectionEnd(): Promise<void> {
    // No-op on the web.
  }

  async selectionStart(): Promise<void> {
    // No-op on the web.
  }

  async vibrate(options?: VibrateOptions): Promise<void> {
    this.vibrateOrThrow(options?.duration ?? HapticsWeb.defaultVibrateDuration);
  }

  private createPatternFromEvents(events: HapticEvent[]): number[] {
    const sortedEvents = [...events].sort((a, b) => a.time - b.time);
    const pattern: number[] = [];
    let currentTime = 0;
    for (const event of sortedEvents) {
      const startTime = Math.max(Math.round(event.time * 1000), currentTime);
      const pause = startTime - currentTime;
      const duration = event.duration
        ? Math.round(event.duration * 1000)
        : HapticsWeb.transientEventDuration;
      if (pattern.length === 0) {
        if (pause > 0) {
          pattern.push(0, pause);
        }
      } else {
        pattern.push(pause);
      }
      pattern.push(duration);
      currentTime = startTime + duration;
    }
    return pattern;
  }

  private getDurationForImpactStyle(style: ImpactStyle): number {
    switch (style) {
      case ImpactStyle.Heavy:
        return 60;
      case ImpactStyle.Light:
        return 20;
      case ImpactStyle.Rigid:
        return 25;
      case ImpactStyle.Soft:
        return 50;
      default:
        return 40;
    }
  }

  private getPatternForNotificationType(type: NotificationType): number[] {
    switch (type) {
      case NotificationType.Error:
        return [40, 60, 40, 60, 60];
      case NotificationType.Warning:
        return [60, 100, 60];
      default:
        return [40, 80, 60];
    }
  }

  private isVibrationSupported(): boolean {
    return typeof navigator !== 'undefined' && 'vibrate' in navigator;
  }

  private vibrateOrThrow(pattern: number | number[]): void {
    if (!this.isVibrationSupported()) {
      throw this.unavailable('Vibration API not available in this browser.');
    }
    navigator.vibrate(pattern);
  }
}
