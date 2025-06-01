import { WebPlugin } from '@capacitor/core';
import type { CameraImageData, CameraMultiCapturePlugin, CameraOverlayOptions, CameraOverlayResult } from './definitions';

export class CameraMultiCaptureWeb extends WebPlugin implements CameraMultiCapturePlugin {
  async capture(): Promise<{ value: CameraImageData }> {
    console.warn('[CameraMultiCapture] capture() not available on web.');
    return { value: { uri: '', base64: '' } };
  }

  async stop(): Promise<void> {
    console.warn('[CameraMultiCapture] stop() not available on web.');
  }

  async start(_options?: CameraOverlayOptions): Promise<CameraOverlayResult> {
    console.warn('[CameraMultiCapture] start() not available on web. Use initialize instead.');
    return { images: [], cancelled: true };
  }

  async switchCamera(): Promise<void> {
    console.warn('[CameraMultiCapture] switchCamera() not available on web.');
  }

  async setZoom(_options: { zoom: number }): Promise<void> {
    console.warn('[CameraMultiCapture] setZoom() not available on web.');
  }

  async setFlash(_options: { enableFlash: boolean }): Promise<void> {
    console.warn('[CameraMultiCapture] setFlash() not available on web.');
  }
}

const CameraMultiCapture = new CameraMultiCaptureWeb();
export { CameraMultiCapture };
