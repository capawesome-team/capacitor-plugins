export interface PermissionsPlugin {
  checkPermission(
    options: CheckPermissionOptions,
  ): Promise<CheckPermissionResult>;
  requestPermission(
    options: RequestPermissionOptions,
  ): Promise<RequestPermissionResult>;
}

export interface CheckPermissionOptions {
  permission: AndroidPermission | IosPermission;
}

export interface CheckPermissionResult {
  state: PermissionState;
}

export interface RequestPermissionOptions {
  permission: AndroidPermission | IosPermission;
}

export interface RequestPermissionResult {
  state: PermissionState;
}

export enum AndroidPermission {
  AccessFineLocation = 'ACCESS_FINE_LOCATION',
}

export enum IosPermission {
  Camera = 'CAMERA',
}

export type PermissionState =
  | 'prompt'
  | 'prompt-with-rationale'
  | 'granted'
  | 'denied'
  | 'limited';
