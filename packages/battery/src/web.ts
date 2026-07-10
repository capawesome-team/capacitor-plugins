import { WebPlugin } from '@capacitor/core';

import type {
  BatteryPlugin,
  BatteryState,
  GetBatteryLevelResult,
  GetBatteryStateResult,
  IsLowPowerModeEnabledResult,
} from './definitions';

interface BatteryManager extends EventTarget {
  readonly charging: boolean;
  readonly level: number;
}

interface NavigatorWithBattery extends Navigator {
  getBattery?: () => Promise<BatteryManager>;
}

export class BatteryWeb extends WebPlugin implements BatteryPlugin {
  private static readonly errorNotSupported =
    'The Battery Status API is not supported in this browser.';

  private batteryManagerPromise: Promise<BatteryManager> | undefined;

  constructor() {
    super();
    void this.initializeBatteryManager();
  }

  async getBatteryLevel(): Promise<GetBatteryLevelResult> {
    const batteryManager = await this.getBatteryManager();
    return { level: batteryManager.level };
  }

  async getBatteryState(): Promise<GetBatteryStateResult> {
    const batteryManager = await this.getBatteryManager();
    return { state: this.mapBatteryState(batteryManager) };
  }

  async isLowPowerModeEnabled(): Promise<IsLowPowerModeEnabledResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  private async getBatteryManager(): Promise<BatteryManager> {
    const getBattery = (navigator as NavigatorWithBattery).getBattery;
    if (!getBattery) {
      throw this.unimplemented(BatteryWeb.errorNotSupported);
    }
    if (!this.batteryManagerPromise) {
      this.batteryManagerPromise = getBattery.call(navigator);
    }
    return this.batteryManagerPromise;
  }

  private async initializeBatteryManager(): Promise<void> {
    let batteryManager: BatteryManager;
    try {
      batteryManager = await this.getBatteryManager();
    } catch {
      return;
    }
    batteryManager.addEventListener('levelchange', () => {
      this.notifyListeners('batteryLevelChange', {
        level: batteryManager.level,
      });
    });
    batteryManager.addEventListener('chargingchange', () => {
      this.notifyListeners('batteryStateChange', {
        state: this.mapBatteryState(batteryManager),
      });
    });
  }

  private mapBatteryState(batteryManager: BatteryManager): BatteryState {
    if (batteryManager.level >= 1) {
      return 'full';
    }
    if (batteryManager.charging) {
      return 'charging';
    }
    return 'unplugged';
  }
}
