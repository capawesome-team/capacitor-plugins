import type { PluginsConfig } from '@capacitor/cli';
import { CapacitorException, ExceptionCode } from '@capacitor/core';
import { ElectronPlugin } from '@capawesome/capacitor-electron/plugin';
import type { ElectronPluginContext } from '@capawesome/capacitor-electron/plugin';
import { LiveUpdateEngine } from '@capawesome/electron-live-update/engine';
import { app, powerMonitor } from 'electron';
import { createRequire } from 'node:module';
import { join } from 'node:path';

import type {
  DeleteBundleOptions,
  DownloadBundleOptions,
  FetchChannelsOptions,
  FetchChannelsResult,
  FetchLatestBundleOptions,
  FetchLatestBundleResult,
  GetBlockedBundlesResult,
  GetBundlesResult,
  GetChannelResult,
  GetConfigResult,
  GetCurrentBundleResult,
  GetCustomIdResult,
  GetDeviceIdResult,
  GetDownloadedBundlesResult,
  GetNextBundleResult,
  GetVersionCodeResult,
  GetVersionNameResult,
  IsSyncingResult,
  LiveUpdatePlugin,
  ReadyResult,
  SetChannelOptions,
  SetConfigOptions,
  SetCustomIdOptions,
  SetNextBundleOptions,
  SyncOptions,
  SyncResult,
} from '../../src/definitions';

const AUTO_UPDATE_MIN_INTERVAL = 15 * 60 * 1000;
const CAPACITOR_RUNTIME = 'capacitor';
const ELECTRON_PLATFORM = '2';
// Resolve the plugin version from the package's own `package.json`. The built
// file lives at `electron/dist/plugin.mjs`, so the package root is two levels up.
const PLUGIN_VERSION: string = createRequire(import.meta.url)(
  '../../package.json',
).version;

type LiveUpdateConfig = NonNullable<PluginsConfig['LiveUpdate']>;

export class LiveUpdateElectron
  extends ElectronPlugin
  implements Omit<LiveUpdatePlugin, 'addListener' | 'removeAllListeners'>
{
  public static readonly __capacitorElectronPlugin = {
    name: 'LiveUpdate',
    methods: [
      'clearBlockedBundles',
      'deleteBundle',
      'downloadBundle',
      'fetchChannels',
      'fetchLatestBundle',
      'getBlockedBundles',
      'getBundles',
      'getChannel',
      'getConfig',
      'getCurrentBundle',
      'getCustomId',
      'getDeviceId',
      'getDownloadedBundles',
      'getNextBundle',
      'getVersionCode',
      'getVersionName',
      'isSyncing',
      'ready',
      'reload',
      'reset',
      'resetConfig',
      'setChannel',
      'setConfig',
      'setCustomId',
      'setNextBundle',
      'sync',
    ],
  };

  private static readonly errorNotImplemented = 'Not implemented on Electron.';

  private readonly config: LiveUpdateConfig;
  private readonly engine: LiveUpdateEngine;
  private lastAutoUpdateCheck = 0;

  constructor(context: ElectronPluginContext) {
    super(context);
    this.config =
      (context.config.plugins as PluginsConfig | undefined)?.LiveUpdate ?? {};
    this.engine = new LiveUpdateEngine({
      appId: this.config.appId,
      autoBlockRolledBackBundles: this.config.autoBlockRolledBackBundles,
      autoDeleteBundles: this.config.autoDeleteBundles,
      dataDirectory: join(app.getPath('userData'), 'capawesome-live-update'),
      defaultChannel: this.config.defaultChannel,
      httpTimeout: this.config.httpTimeout,
      osVersion: process.getSystemVersion(),
      platform: ELECTRON_PLATFORM,
      pluginVersion: PLUGIN_VERSION,
      publicKey: this.config.publicKey,
      readyTimeout: this.config.readyTimeout,
      runtime: CAPACITOR_RUNTIME,
      sdkVersion: PLUGIN_VERSION,
      serverDomain: this.config.serverDomain,
      versionCode: app.getVersion(),
      versionName: app.getVersion(),
    });
    this.engine.on('downloadBundleProgress', event =>
      this.context.notifyListeners('downloadBundleProgress', event),
    );
    this.engine.on('nextBundleSet', event =>
      this.context.notifyListeners('nextBundleSet', event),
    );
    // The engine owns rollback. When its ready watchdog rolls back,
    // repoint the platform so that all windows reload the target bundle.
    this.engine.on('rolledBack', () => {
      void this.applyCurrentBundle().catch(error =>
        console.error(`[LiveUpdate] Failed to apply rollback: ${error}`),
      );
    });
  }

  public async clearBlockedBundles(): Promise<void> {
    return this.engine.clearBlockedBundles();
  }

  public async deleteBundle(options: DeleteBundleOptions): Promise<void> {
    return this.engine.deleteBundle(options);
  }

  public async downloadBundle(options: DownloadBundleOptions): Promise<void> {
    return this.engine.downloadBundle(options);
  }

  public async fetchChannels(
    options?: FetchChannelsOptions,
  ): Promise<FetchChannelsResult> {
    return this.engine.fetchChannels(options);
  }

  public async fetchLatestBundle(
    options?: FetchLatestBundleOptions,
  ): Promise<FetchLatestBundleResult> {
    return this.engine.fetchLatestBundle(options);
  }

  public async getBlockedBundles(): Promise<GetBlockedBundlesResult> {
    return this.engine.getBlockedBundles();
  }

  public async getBundles(): Promise<GetBundlesResult> {
    return this.getDownloadedBundles();
  }

  public async getChannel(): Promise<GetChannelResult> {
    return this.engine.getChannel();
  }

  public async getConfig(): Promise<GetConfigResult> {
    return {
      appId: this.config.appId ?? null,
      autoUpdateStrategy: this.config.autoUpdateStrategy ?? 'none',
    };
  }

  public async getCurrentBundle(): Promise<GetCurrentBundleResult> {
    return this.engine.getCurrentBundle();
  }

  public async getCustomId(): Promise<GetCustomIdResult> {
    return this.engine.getCustomId();
  }

  public async getDeviceId(): Promise<GetDeviceIdResult> {
    return this.engine.getDeviceId();
  }

  public async getDownloadedBundles(): Promise<GetDownloadedBundlesResult> {
    return this.engine.getDownloadedBundles();
  }

  public async getNextBundle(): Promise<GetNextBundleResult> {
    return this.engine.getNextBundle();
  }

  public async getVersionCode(): Promise<GetVersionCodeResult> {
    return this.engine.getVersionCode();
  }

  public async getVersionName(): Promise<GetVersionNameResult> {
    return this.engine.getVersionName();
  }

  public async load(): Promise<void> {
    await this.engine.initialize();
    const bundlePath = this.engine.getCurrentBundlePath();
    if (
      bundlePath !== null ||
      this.context.services.bundles.getActiveBundlePath() !== null
    ) {
      await this.context.services.bundles.setActiveBundle(bundlePath, {
        bootWatchdog: false,
      });
    }
    if (this.config.autoUpdateStrategy === 'background') {
      this.setUpBackgroundAutoUpdate();
    }
  }

  public async isSyncing(): Promise<IsSyncingResult> {
    return this.engine.isSyncing();
  }

  public async ready(): Promise<ReadyResult> {
    return this.engine.ready();
  }

  public async reload(): Promise<void> {
    await this.engine.applyNextBundle();
    await this.applyCurrentBundle();
    this.context.notifyListeners('reloaded');
  }

  public async reset(): Promise<void> {
    return this.engine.reset();
  }

  public async resetConfig(): Promise<void> {
    this.throwUnimplementedError();
  }

  public async setChannel(options: SetChannelOptions): Promise<void> {
    return this.engine.setChannel(options);
  }

  public async setConfig(_options: SetConfigOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  public async setCustomId(options: SetCustomIdOptions): Promise<void> {
    return this.engine.setCustomId(options);
  }

  public async setNextBundle(options: SetNextBundleOptions): Promise<void> {
    return this.engine.setNextBundle(options);
  }

  public async sync(options?: SyncOptions): Promise<SyncResult> {
    return this.engine.sync(options);
  }

  private async applyCurrentBundle(): Promise<void> {
    await this.context.services.bundles.setActiveBundle(
      this.engine.getCurrentBundlePath(),
      { bootWatchdog: false },
    );
  }

  private setUpBackgroundAutoUpdate(): void {
    const check = (): void => {
      const now = Date.now();
      if (now - this.lastAutoUpdateCheck < AUTO_UPDATE_MIN_INTERVAL) {
        return;
      }
      this.lastAutoUpdateCheck = now;
      void this.sync().catch(error =>
        console.warn(`[LiveUpdate] Background sync failed: ${error}`),
      );
    };
    void app.whenReady().then(() => {
      check();
      app.on('browser-window-focus', check);
      powerMonitor.on('resume', check);
    });
  }

  private throwUnimplementedError(): never {
    throw new CapacitorException(
      LiveUpdateElectron.errorNotImplemented,
      ExceptionCode.Unimplemented,
    );
  }
}
