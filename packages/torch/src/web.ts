import { WebPlugin } from '@capacitor/core';

import type {
  IsAvailableResult,
  IsEnabledResult,
  TorchPlugin,
} from './definitions';

export class TorchWeb extends WebPlugin implements TorchPlugin {
  private readonly errorNotAvailable = 'Not available on this device.';
  private stream?: MediaStream;

  async enable(): Promise<void> {
    const { available } = await this.isAvailable();
    if (available) {
      const { enabled } = await this.isEnabled();
      if (enabled) {
        return;
      } else {
        this.stream?.getTracks().forEach(track => track.stop());
        this.stream = await navigator.mediaDevices.getUserMedia({
          audio: false,
          video: { torch: true } as MediaTrackConstraints,
        });
      }
    } else {
      throw new Error(this.errorNotAvailable);
    }
  }

  async disable(): Promise<void> {
    if (await this.isAvailable()) {
      const { enabled } = await this.isEnabled();
      if (enabled) {
        this.stream?.getTracks().forEach(track => track.stop());
      } else return;
    } else {
      throw new Error(this.errorNotAvailable);
    }
  }

  isAvailable(): Promise<IsAvailableResult> {
    const supportedConstraints =
      navigator.mediaDevices.getSupportedConstraints() as {
        [key: string]: boolean | undefined;
      };
    const available = !!supportedConstraints['torch'];
    return Promise.resolve({ available });
  }

  async isEnabled(): Promise<IsEnabledResult> {
    if (this.stream?.active) {
      const [videoTrack] = this.stream.getVideoTracks();
      const enabled = !!(
        videoTrack.getSettings() as MediaTrackConstraints & {
          torch: boolean | undefined;
        }
      ).torch;
      return Promise.resolve({ enabled });
    } else return Promise.resolve({ enabled: false });
  }

  async toggle(): Promise<void> {
    const { enabled } = await this.isEnabled();
    if (enabled) {
      return this.disable();
    } else return this.enable();
  }
}
