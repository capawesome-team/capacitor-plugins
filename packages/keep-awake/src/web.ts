import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  IsAvailableResult,
  IsKeptAwakeResult,
  KeepAwakePlugin,
} from './definitions';

export class KeepAwakeWeb extends WebPlugin implements KeepAwakePlugin {
  private keepAwakeRequested = false;
  private wakeLockSentinel: WakeLockSentinel | null = null;

  constructor() {
    super();
    document.addEventListener(
      'visibilitychange',
      this.handleVisibilityChange.bind(this),
    );
  }

  async allowSleep(): Promise<void> {
    if (!this.isSupported()) {
      throw this.createUnavailableException();
    }
    this.keepAwakeRequested = false;
    await this.releaseWakeLock();
  }

  async isAvailable(): Promise<IsAvailableResult> {
    return { available: this.isSupported() };
  }

  async isKeptAwake(): Promise<IsKeptAwakeResult> {
    return { keptAwake: this.keepAwakeRequested };
  }

  async keepAwake(): Promise<void> {
    if (!this.isSupported()) {
      throw this.createUnavailableException();
    }
    this.keepAwakeRequested = true;
    await this.requestWakeLock();
  }

  private createUnavailableException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not available on this platform.',
      ExceptionCode.Unavailable,
    );
  }

  private async handleVisibilityChange(): Promise<void> {
    if (this.keepAwakeRequested && document.visibilityState === 'visible') {
      await this.requestWakeLock();
    }
  }

  private isSupported(): boolean {
    return 'wakeLock' in navigator;
  }

  private async releaseWakeLock(): Promise<void> {
    if (this.wakeLockSentinel) {
      await this.wakeLockSentinel.release();
      this.wakeLockSentinel = null;
    }
  }

  private async requestWakeLock(): Promise<void> {
    if (this.wakeLockSentinel && !this.wakeLockSentinel.released) {
      return;
    }
    this.wakeLockSentinel = await navigator.wakeLock.request('screen');
  }
}
