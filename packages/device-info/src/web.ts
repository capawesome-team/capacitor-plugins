import { WebPlugin } from '@capacitor/core';

import type {
  DeviceInfoPlugin,
  DeviceType,
  GetIdResult,
  GetInfoResult,
  GetUptimeResult,
  OperatingSystem,
} from './definitions';

interface UserAgentBrand {
  brand: string;
  version: string;
}

interface UserAgentData {
  brands?: UserAgentBrand[];
  mobile?: boolean;
  platform?: string;
}

interface NavigatorWithUserAgentData extends Navigator {
  userAgentData?: UserAgentData;
}

export class DeviceInfoWeb extends WebPlugin implements DeviceInfoPlugin {
  private static readonly identifierStorageKey =
    'capawesome-capacitor-device-info-id';

  async getId(): Promise<GetIdResult> {
    let identifier = window.localStorage.getItem(
      DeviceInfoWeb.identifierStorageKey,
    );
    if (!identifier) {
      identifier = this.createIdentifier();
      window.localStorage.setItem(
        DeviceInfoWeb.identifierStorageKey,
        identifier,
      );
    }
    return { identifier };
  }

  async getInfo(): Promise<GetInfoResult> {
    const userAgent = navigator.userAgent;
    const userAgentData = (navigator as NavigatorWithUserAgentData)
      .userAgentData;
    return {
      androidSdkVersion: null,
      deviceType: this.determineDeviceType(userAgent, userAgentData),
      iosVersion: null,
      isVirtual: false,
      manufacturer: 'unknown',
      model: 'unknown',
      name: null,
      operatingSystem: this.determineOperatingSystem(userAgent, userAgentData),
      osVersion: this.determineOsVersion(userAgent) ?? 'unknown',
      platform: 'web',
      totalMemory: null,
      usedMemory: null,
      webViewVersion: this.determineWebViewVersion(userAgent),
    };
  }

  async getUptime(): Promise<GetUptimeResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  private createIdentifier(): string {
    if (typeof crypto !== 'undefined' && 'randomUUID' in crypto) {
      return crypto.randomUUID();
    }
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(
      /[xy]/g,
      character => {
        const random = (Math.random() * 16) | 0;
        const value = character === 'x' ? random : (random & 0x3) | 0x8;
        return value.toString(16);
      },
    );
  }

  private determineDeviceType(
    userAgent: string,
    userAgentData?: UserAgentData,
  ): DeviceType {
    if (/TV|SmartTV|Tizen|Web0S|WebOS/i.test(userAgent)) {
      return 'tv';
    }
    if (/iPad/.test(userAgent)) {
      return 'tablet';
    }
    if (/Android/.test(userAgent) && !/Mobile/.test(userAgent)) {
      return 'tablet';
    }
    if (userAgentData?.mobile) {
      return 'phone';
    }
    if (/Mobile|iPhone|iPod/.test(userAgent)) {
      return 'phone';
    }
    return 'desktop';
  }

  private determineOperatingSystem(
    userAgent: string,
    userAgentData?: UserAgentData,
  ): OperatingSystem {
    const platform = userAgentData?.platform?.toLowerCase();
    if (platform) {
      if (platform.includes('win')) {
        return 'windows';
      }
      if (platform.includes('android')) {
        return 'android';
      }
      if (platform.includes('ios')) {
        return 'ios';
      }
      if (platform.includes('mac')) {
        return 'mac';
      }
    }
    if (/Windows/.test(userAgent)) {
      return 'windows';
    }
    if (/Android/.test(userAgent)) {
      return 'android';
    }
    if (/iPhone|iPad|iPod/.test(userAgent)) {
      return 'ios';
    }
    if (/Mac OS X/.test(userAgent)) {
      return 'mac';
    }
    return 'unknown';
  }

  private determineOsVersion(userAgent: string): string | null {
    const windowsMatch = /Windows NT ([0-9._]+)/.exec(userAgent);
    if (windowsMatch) {
      return windowsMatch[1];
    }
    const androidMatch = /Android ([0-9._]+)/.exec(userAgent);
    if (androidMatch) {
      return androidMatch[1];
    }
    const iosMatch = /OS ([0-9_]+) like Mac OS X/.exec(userAgent);
    if (iosMatch) {
      return iosMatch[1].replace(/_/g, '.');
    }
    const macMatch = /Mac OS X ([0-9_]+)/.exec(userAgent);
    if (macMatch) {
      return macMatch[1].replace(/_/g, '.');
    }
    return null;
  }

  private determineWebViewVersion(userAgent: string): string | null {
    const patterns = [
      /Edg\/([0-9.]+)/,
      /Chrome\/([0-9.]+)/,
      /Firefox\/([0-9.]+)/,
      /Version\/([0-9.]+).*Safari/,
    ];
    for (const pattern of patterns) {
      const match = pattern.exec(userAgent);
      if (match) {
        return match[1];
      }
    }
    return null;
  }
}
