import { WebPlugin } from '@capacitor/core';

import type {
  DeleteBundleOptions,
  DownloadBundleOptions,
  FetchChannelsResult,
  FetchLatestBundleResult,
  GetBlockedBundlesResult,
  GetBundleResult,
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
  SetBundleOptions,
  SetChannelOptions,
  SetConfigOptions,
  SetCustomIdOptions,
  SetNextBundleOptions,
  SyncResult,
} from './definitions';

export class LiveUpdateWeb extends WebPlugin implements LiveUpdatePlugin {
  public async clearBlockedBundles(): Promise<void> {
    this.throwUnimplementedError();
  }

  public async deleteBundle(_options: DeleteBundleOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  public async downloadBundle(_options: DownloadBundleOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  public async fetchChannels(): Promise<FetchChannelsResult> {
    this.throwUnimplementedError();
  }

  public async fetchLatestBundle(): Promise<FetchLatestBundleResult> {
    this.throwUnimplementedError();
  }

  public async getBlockedBundles(): Promise<GetBlockedBundlesResult> {
    this.throwUnimplementedError();
  }

  public async getBundle(): Promise<GetBundleResult> {
    this.throwUnimplementedError();
  }

  public async getBundles(): Promise<GetBundlesResult> {
    this.throwUnimplementedError();
  }

  public async getDownloadedBundles(): Promise<GetDownloadedBundlesResult> {
    this.throwUnimplementedError();
  }

  public async getChannel(): Promise<GetChannelResult> {
    this.throwUnimplementedError();
  }

  public async getConfig(): Promise<GetConfigResult> {
    this.throwUnimplementedError();
  }

  public async getCurrentBundle(): Promise<GetCurrentBundleResult> {
    this.throwUnimplementedError();
  }

  public async getCustomId(): Promise<GetCustomIdResult> {
    this.throwUnimplementedError();
  }

  public async getDeviceId(): Promise<GetDeviceIdResult> {
    this.throwUnimplementedError();
  }

  public async getNextBundle(): Promise<GetNextBundleResult> {
    this.throwUnimplementedError();
  }

  public async getVersionCode(): Promise<GetVersionCodeResult> {
    this.throwUnimplementedError();
  }

  public async getVersionName(): Promise<GetVersionNameResult> {
    this.throwUnimplementedError();
  }

  public async isSyncing(): Promise<IsSyncingResult> {
    this.throwUnimplementedError();
  }

  public async ready(): Promise<ReadyResult> {
    this.throwUnimplementedError();
  }

  public async reload(): Promise<void> {
    this.throwUnimplementedError();
  }

  public async reset(): Promise<void> {
    this.throwUnimplementedError();
  }

  public async resetConfig(): Promise<void> {
    this.throwUnimplementedError();
  }

  public async setBundle(_options: SetBundleOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  public async setChannel(_options: SetChannelOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  public async setConfig(_options: SetConfigOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  public async setCustomId(_options: SetCustomIdOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  public async setNextBundle(_options: SetNextBundleOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  public async sync(): Promise<SyncResult> {
    this.throwUnimplementedError();
  }

  private throwUnimplementedError(): never {
    throw this.unimplemented('Not implemented on web.');
  }
}
