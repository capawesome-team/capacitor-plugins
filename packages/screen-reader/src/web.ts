import { WebPlugin } from '@capacitor/core';
import type { PluginListenerHandle } from '@capacitor/core';

import type {
  AnnounceOptions,
  IsEnabledResult,
  ScreenReaderPlugin,
} from './definitions';

export class ScreenReaderWeb extends WebPlugin implements ScreenReaderPlugin {
  private static readonly errorValueMissing = 'value must be provided.';

  private liveRegion: HTMLElement | undefined;

  async announce(options: AnnounceOptions): Promise<void> {
    if (!options.value) {
      throw new Error(ScreenReaderWeb.errorValueMissing);
    }
    const liveRegion = this.getLiveRegion();
    // Clear first so that repeating the same message is announced again.
    liveRegion.textContent = '';
    window.setTimeout(() => {
      liveRegion.textContent = options.value;
    }, 100);
  }

  async isEnabled(): Promise<IsEnabledResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async addListener(): Promise<PluginListenerHandle> {
    throw this.unimplemented('Not implemented on web.');
  }

  private getLiveRegion(): HTMLElement {
    if (!this.liveRegion) {
      const liveRegion = document.createElement('div');
      liveRegion.setAttribute('aria-live', 'polite');
      liveRegion.setAttribute('aria-atomic', 'true');
      liveRegion.setAttribute('role', 'status');
      liveRegion.style.position = 'absolute';
      liveRegion.style.width = '1px';
      liveRegion.style.height = '1px';
      liveRegion.style.margin = '-1px';
      liveRegion.style.padding = '0';
      liveRegion.style.border = '0';
      liveRegion.style.overflow = 'hidden';
      liveRegion.style.clip = 'rect(0, 0, 0, 0)';
      liveRegion.style.whiteSpace = 'nowrap';
      document.body.appendChild(liveRegion);
      this.liveRegion = liveRegion;
    }
    return this.liveRegion;
  }
}
