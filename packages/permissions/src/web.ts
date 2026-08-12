import { WebPlugin } from '@capacitor/core';

import type {
  CheckOptions,
  CheckResult,
  PermissionsPlugin,
  PermissionState,
  PermissionStatus,
  RequestOptions,
  RequestResult,
} from './definitions';
import { Permission } from './definitions';

export class PermissionsWeb extends WebPlugin implements PermissionsPlugin {
  private static readonly errorPermissionsMissing =
    'permissions must be provided.';

  async check(options: CheckOptions): Promise<CheckResult> {
    const permissions = this.getPermissionsFromOptions(options);
    const statuses: PermissionStatus[] = [];
    for (const permission of permissions) {
      statuses.push({
        permission,
        state: await this.getPermissionState(permission),
      });
    }
    return { statuses };
  }

  async request(options: RequestOptions): Promise<RequestResult> {
    const permissions = this.getPermissionsFromOptions(options);
    const statuses: PermissionStatus[] = [];
    for (const permission of permissions) {
      statuses.push({
        permission,
        state: await this.requestPermission(permission),
      });
    }
    return { statuses };
  }

  private getNotificationsPermissionState(): PermissionState {
    if (!('Notification' in window)) {
      return 'unavailable';
    }
    switch (Notification.permission) {
      case 'granted':
        return 'granted';
      case 'denied':
        return 'denied';
      default:
        return 'prompt';
    }
  }

  private getPermissionNameForQuery(
    permission: Permission,
  ): PermissionName | undefined {
    switch (permission) {
      case Permission.Camera:
        return 'camera' as PermissionName;
      case Permission.Location:
        return 'geolocation' as PermissionName;
      case Permission.Microphone:
        return 'microphone' as PermissionName;
      default:
        return undefined;
    }
  }

  private async getPermissionState(
    permission: Permission,
  ): Promise<PermissionState> {
    if (permission === Permission.Notifications) {
      return this.getNotificationsPermissionState();
    }
    const name = this.getPermissionNameForQuery(permission);
    if (!name || !navigator.permissions) {
      return 'unavailable';
    }
    try {
      const status = await navigator.permissions.query({ name });
      switch (status.state) {
        case 'granted':
          return 'granted';
        case 'denied':
          return 'denied';
        default:
          return 'prompt';
      }
    } catch {
      return 'unavailable';
    }
  }

  private getPermissionsFromOptions(
    options: CheckOptions | RequestOptions,
  ): Permission[] {
    const permissions = options?.permissions;
    if (!permissions || permissions.length === 0) {
      throw new Error(PermissionsWeb.errorPermissionsMissing);
    }
    return permissions;
  }

  private async requestPermission(
    permission: Permission,
  ): Promise<PermissionState> {
    if (permission === Permission.Notifications && 'Notification' in window) {
      await Notification.requestPermission();
      return this.getNotificationsPermissionState();
    }
    return this.getPermissionState(permission);
  }
}
