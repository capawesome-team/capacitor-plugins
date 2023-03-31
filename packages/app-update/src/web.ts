import { WebPlugin } from '@capacitor/core';

import type {
  AppUpdateInfo,
  AppUpdatePlugin,
  AppUpdateResult,
} from './definitions';

export class AppUpdateWeb extends WebPlugin implements AppUpdatePlugin {
  async getAppUpdateInfo(): Promise<AppUpdateInfo> {
    throw new Error('Web platform is not supported.');
  }

  async openAppStore(): Promise<void> {
    throw new Error('Web platform is not supported.');
  }

  async performImmediateUpdate(): Promise<AppUpdateResult> {
    throw new Error('Web platform is not supported.');
  }

  async startFlexibleUpdate(): Promise<AppUpdateResult> {
    throw new Error('Web platform is not supported.');
  }

  async completeFlexibleUpdate(): Promise<void> {
    throw new Error('Web platform is not supported.');
  }
}
