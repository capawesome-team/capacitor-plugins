/* eslint-disable @typescript-eslint/ban-ts-comment */
import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  GyroscopePlugin,
  IsAvailableResult,
  Measurement,
  PermissionStatus,
} from './definitions';

export class GyroscopeWeb extends WebPlugin implements GyroscopePlugin {
  // @ts-ignore
  private _gyroscope: Gyroscope | undefined;
  private readonly _isAvailable = 'Gyroscope' in window;
  private measurementEventStarted = false;

  async checkPermissions(): Promise<PermissionStatus> {
    const { state } = await navigator.permissions.query({
      name: 'gyroscope' as PermissionName,
    });
    return { gyroscope: state };
  }

  async getMeasurement(): Promise<Measurement> {
    if (!this._isAvailable) {
      throw this.createUnavailableException();
    }
    const gyroscope = this.createOrGetGyroscope();
    return new Promise<Measurement>((resolve, reject) => {
      gyroscope.onerror = (event: any) => {
        console.error(event);
        reject(event);
      };
      gyroscope.onreading = () => {
        const measurement = this.readMeasurementFromGyroscope();
        gyroscope.stop();
        resolve(measurement);
      };
      gyroscope.start();
    });
  }

  async isAvailable(): Promise<IsAvailableResult> {
    let isAvailable = false;
    if (!this._isAvailable) {
      return { isAvailable };
    }
    // According to an article on Chrome Developers (https://developer.chrome.com/docs/capabilities/web-apis/generic-sensor#feature-detection)
    // we also need to connect to the sensor for an actual meaningful feature detection
    const gyroscope = this.createOrGetGyroscope();
    try {
      await new Promise<void>(resolve => {
        gyroscope.onerror = (event: any) => {
          console.error(event);
          isAvailable = false;
          gyroscope.stop();
          resolve();
        };
        gyroscope.onreading = () => {
          isAvailable = true;
          gyroscope.stop();
          resolve();
        };
        gyroscope.start();
      });
    } catch (error: any) {
      console.error(error);
      isAvailable = false;
    }
    return { isAvailable };
  }

  async removeAllListeners(): Promise<void> {
    await super.removeAllListeners();
    if (this.measurementEventStarted) {
      await this.stopMeasurementUpdates();
    }
  }

  async requestPermissions(): Promise<PermissionStatus> {
    const { state } = await navigator.permissions.query({
      name: 'gyroscope' as PermissionName,
    });
    return { gyroscope: state };
  }

  async startMeasurementUpdates(): Promise<void> {
    if (!this._isAvailable) {
      throw this.createUnavailableException();
    }
    if (this.measurementEventStarted) {
      return;
    }
    this.measurementEventStarted = true;
    const gyroscope = this.createOrGetGyroscope();
    gyroscope.onreading = () => this.handleMeasurementEvent();
    gyroscope.start();
  }

  async stopMeasurementUpdates(): Promise<void> {
    if (!this._isAvailable) {
      throw this.createUnavailableException();
    }
    if (!this.measurementEventStarted) {
      return;
    }
    this.measurementEventStarted = false;
    const gyroscope = this.createOrGetGyroscope();
    gyroscope.stop();
    gyroscope.onreading = null;
  }

  // @ts-ignore
  private createOrGetGyroscope(): Gyroscope {
    if (!this._gyroscope) {
      // @ts-ignore
      this._gyroscope = new Gyroscope({
        frequency: 10,
      });
    }
    return this._gyroscope;
  }

  private createUnavailableException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not available on this platform.',
      ExceptionCode.Unavailable,
    );
  }

  private handleMeasurementEvent(): void {
    const measurement = this.readMeasurementFromGyroscope();
    this.notifyListeners('measurement', measurement);
  }

  private readMeasurementFromGyroscope(): Measurement {
    const gyroscope = this.createOrGetGyroscope();
    return {
      x: Math.round(gyroscope.x * 100) / 100,
      y: Math.round(gyroscope.y * 100) / 100,
      z: Math.round(gyroscope.z * 100) / 100,
    };
  }
}
