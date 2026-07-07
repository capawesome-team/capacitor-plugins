import { WebPlugin } from '@capacitor/core';

import type {
  GetStatusResult,
  IsAirplaneModeEnabledResult,
  NetworkPlugin,
} from './definitions';
import { ConnectionType } from './definitions';

interface NetworkInformation extends EventTarget {
  readonly type?: string;
}

interface NavigatorWithConnection extends Navigator {
  readonly connection?: NetworkInformation;
}

export class NetworkWeb extends WebPlugin implements NetworkPlugin {
  constructor() {
    super();
    window.addEventListener('online', this.handleNetworkStatusChange);
    window.addEventListener('offline', this.handleNetworkStatusChange);
    this.getConnection()?.addEventListener(
      'change',
      this.handleNetworkStatusChange,
    );
  }

  async getStatus(): Promise<GetStatusResult> {
    return this.createStatus();
  }

  async isAirplaneModeEnabled(): Promise<IsAirplaneModeEnabledResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  private createStatus(): GetStatusResult {
    const connected = navigator.onLine;
    return {
      connected,
      connectionType: connected
        ? this.mapConnectionType(this.getConnection()?.type)
        : ConnectionType.None,
      internetReachable: null,
    };
  }

  private getConnection(): NetworkInformation | undefined {
    return (navigator as NavigatorWithConnection).connection;
  }

  private handleNetworkStatusChange = (): void => {
    this.notifyListeners('networkStatusChange', this.createStatus());
  };

  private mapConnectionType(type: string | undefined): ConnectionType {
    switch (type) {
      case 'cellular':
        return ConnectionType.Cellular;
      case 'ethernet':
        return ConnectionType.Ethernet;
      case 'none':
        return ConnectionType.None;
      case 'wifi':
        return ConnectionType.Wifi;
      default:
        return ConnectionType.Unknown;
    }
  }
}
