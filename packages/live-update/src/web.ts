import { WebPlugin } from '@capacitor/core';

import type {
  DeleteBundleOptions,
  DownloadBundleOptions,
  FetchLatestBundleResult,
  GetBundleResult,
  GetBundlesResult,
  GetChannelResult,
  GetCurrentBundleResult,
  GetCustomIdResult,
  GetDeviceIdResult,
  GetNextBundleResult,
  GetVersionCodeResult,
  GetVersionNameResult,
  LiveUpdatePlugin,
  ReadyResult,
  SetBundleOptions,
  SetChannelOptions,
  SetCustomIdOptions,
  SetNextBundleOptions,
  SyncResult,
} from './definitions';

export class LiveUpdateWeb extends WebPlugin implements LiveUpdatePlugin {
  public async deleteBundle(_options: DeleteBundleOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  public async downloadBundle(_options: DownloadBundleOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  public async fetchLatestBundle(): Promise<FetchLatestBundleResult> {
    this.throwUnimplementedError();
  }

  public async getBundle(): Promise<GetBundleResult> {
    this.throwUnimplementedError();
  }

  public async getBundles(): Promise<GetBundlesResult> {
    this.throwUnimplementedError();
  }

  public async getChannel(): Promise<GetChannelResult> {
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

  public async ready(): Promise<ReadyResult> {
    this.throwUnimplementedError();
  }

  public async reload(): Promise<void> {
    this.throwUnimplementedError();
  }

  public async reset(): Promise<void> {
    this.throwUnimplementedError();
  }

  public async setBundle(_options: SetBundleOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  public async setChannel(_options: SetChannelOptions): Promise<void> {
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
