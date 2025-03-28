import { WebPlugin } from '@capacitor/core';

import type {
  CheckPermissionOptions,
  CheckPermissionResult,
  PermissionsPlugin,
  RequestPermissionOptions,
  RequestPermissionResult,
} from './definitions';

export class PermissionsWeb extends WebPlugin implements PermissionsPlugin {
  checkPermission(
    options: CheckPermissionOptions,
  ): Promise<CheckPermissionResult> {
    throw new Error('Method not implemented.');
  }
  requestPermission(
    options: RequestPermissionOptions,
  ): Promise<RequestPermissionResult> {
    throw new Error('Method not implemented.');
  }
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
