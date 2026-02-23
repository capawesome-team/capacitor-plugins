import { WebPlugin } from '@capacitor/core';

import type { PixlivePlugin } from './definitions';

export class PixliveWeb extends WebPlugin implements PixlivePlugin {
  async initialize(): Promise<void> {
    this.throwUnimplemented();
  }

  async checkPermissions(): Promise<never> {
    this.throwUnimplemented();
  }

  async requestPermissions(): Promise<never> {
    this.throwUnimplemented();
  }

  async synchronize(): Promise<void> {
    this.throwUnimplemented();
  }

  async synchronizeWithToursAndContexts(): Promise<void> {
    this.throwUnimplemented();
  }

  async updateTagMapping(): Promise<void> {
    this.throwUnimplemented();
  }

  async enableContextsWithTags(): Promise<void> {
    this.throwUnimplemented();
  }

  async getContexts(): Promise<never> {
    this.throwUnimplemented();
  }

  async getContext(): Promise<never> {
    this.throwUnimplemented();
  }

  async activateContext(): Promise<void> {
    this.throwUnimplemented();
  }

  async stopContext(): Promise<void> {
    this.throwUnimplemented();
  }

  async getNearbyGPSPoints(): Promise<never> {
    this.throwUnimplemented();
  }

  async getGPSPointsInBoundingBox(): Promise<never> {
    this.throwUnimplemented();
  }

  async getNearbyBeacons(): Promise<never> {
    this.throwUnimplemented();
  }

  async startNearbyGPSDetection(): Promise<void> {
    this.throwUnimplemented();
  }

  async stopNearbyGPSDetection(): Promise<void> {
    this.throwUnimplemented();
  }

  async startGPSNotifications(): Promise<void> {
    this.throwUnimplemented();
  }

  async stopGPSNotifications(): Promise<void> {
    this.throwUnimplemented();
  }

  async setNotificationsSupport(): Promise<void> {
    this.throwUnimplemented();
  }

  async setInterfaceLanguage(): Promise<void> {
    this.throwUnimplemented();
  }

  async createARView(): Promise<void> {
    this.throwUnimplemented();
  }

  async destroyARView(): Promise<void> {
    this.throwUnimplemented();
  }

  async resizeARView(): Promise<void> {
    this.throwUnimplemented();
  }

  async setARViewTouchEnabled(): Promise<void> {
    this.throwUnimplemented();
  }

  async setARViewTouchHole(): Promise<void> {
    this.throwUnimplemented();
  }

  private throwUnimplemented(): never {
    throw this.unimplemented('Not implemented on web.');
  }
}
