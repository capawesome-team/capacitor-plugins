import { WebPlugin } from '@capacitor/core';

import type {
  AlarmPlugin,
  CancelAlarmOptions,
  CreateAlarmOptions,
  CreateAlarmResult,
  CreateTimerOptions,
  GetAlarmsResult,
  IsAvailableResult,
  PermissionStatus,
} from './definitions';

export class AlarmWeb extends WebPlugin implements AlarmPlugin {
  async cancelAlarm(_options: CancelAlarmOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async checkPermissions(): Promise<PermissionStatus> {
    throw this.unimplemented('Not implemented on web.');
  }

  async createAlarm(_options: CreateAlarmOptions): Promise<CreateAlarmResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async createTimer(_options: CreateTimerOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getAlarms(): Promise<GetAlarmsResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isAvailable(): Promise<IsAvailableResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async openAlarms(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async requestPermissions(): Promise<PermissionStatus> {
    throw this.unimplemented('Not implemented on web.');
  }
}
