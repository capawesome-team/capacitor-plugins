import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  DisableOptions,
  EnableOptions,
  IsAvailableResult,
  IsEnabledOptions,
  IsEnabledResult,
  ToggleOptions,
  TorchPlugin,
} from './definitions';

export class TorchWeb extends WebPlugin implements TorchPlugin {
  private stream?: MediaStream;

  async enable(options?: EnableOptions): Promise<void> {
    const { available } = await this.isAvailable();
    if (!available) {
      throw this.createUnavailableException();
    }
    const { enabled } = await this.isEnabled(options);
    if (enabled) {
      return;
    }
    const stream = options?.stream || this.stream;
    if (stream) {
      const [videoTrack] = stream.getVideoTracks();
      await videoTrack.applyConstraints({
        torch: true,
      } as MediaTrackConstraints & {
        torch?: boolean;
      });
    } else {
      this.stream = await navigator.mediaDevices.getUserMedia({
        audio: false,
        video: {
          facingMode: 'environment',
          torch: true,
        } as MediaTrackConstraints,
      });
    }
  }

  async disable(options?: DisableOptions): Promise<void> {
    const { available } = await this.isAvailable();
    if (!available) {
      throw this.createUnavailableException();
    }
    if (options?.stream) {
      const [videoTrack] = options.stream.getVideoTracks();
      await videoTrack.applyConstraints({
        torch: false,
      } as MediaTrackConstraints & {
        torch?: boolean;
      });
    } else {
      this.stream?.getTracks().forEach(track => track.stop());
      this.stream = undefined;
    }
  }

  public async isAvailable(): Promise<IsAvailableResult> {
    const supportedConstraints =
      navigator.mediaDevices.getSupportedConstraints() as MediaTrackSupportedConstraints & {
        torch?: boolean;
      };
    const available = !!supportedConstraints.torch;
    return {
      available,
    };
  }

  public async isEnabled(options?: IsEnabledOptions): Promise<IsEnabledResult> {
    const { available } = await this.isAvailable();
    if (!available) {
      throw this.createUnavailableException();
    }
    const stream = options?.stream || this.stream;
    if (stream?.active) {
      const [videoTrack] = stream.getVideoTracks();
      const enabled = !!(
        videoTrack.getSettings() as MediaTrackSettings & {
          torch?: boolean;
        }
      ).torch;
      return {
        enabled,
      };
    } else {
      return {
        enabled: false,
      };
    }
  }

  public async toggle(options?: ToggleOptions): Promise<void> {
    const { available } = await this.isAvailable();
    if (!available) {
      throw this.createUnavailableException();
    }
    const { enabled } = await this.isEnabled(options);
    if (enabled) {
      return this.disable(options);
    } else {
      return this.enable(options);
    }
  }

  private createUnavailableException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not available on this platform.',
      ExceptionCode.Unavailable,
    );
  }
}
