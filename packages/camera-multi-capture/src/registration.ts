import { registerPlugin } from '@capacitor/core';
import type { CameraMultiCapturePlugin } from './definitions';

/**
 * Register the CameraMultiCapture plugin with Capacitor
 */
export const CameraMultiCapture = registerPlugin<CameraMultiCapturePlugin>('CameraMultiCapture', {
  web: () => import('./web').then((m) => new m.CameraMultiCaptureWeb()),
});
