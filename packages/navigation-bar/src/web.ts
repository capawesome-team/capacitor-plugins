import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  GetColorResult,
  GetStyleResult,
  NavigationBarPlugin,
  SetColorOptions,
  SetStyleOptions,
} from './definitions';

export class NavigationBarWeb extends WebPlugin implements NavigationBarPlugin {
  async getColor(): Promise<GetColorResult> {
    throw this.createUnimplementedError();
  }

  async getStyle(): Promise<GetStyleResult> {
    throw this.createUnimplementedError();
  }

  async hide(): Promise<void> {
    throw this.createUnimplementedError();
  }

  async setColor(_options: SetColorOptions): Promise<void> {
    throw this.createUnimplementedError();
  }

  async setStyle(_options: SetStyleOptions): Promise<void> {
    throw this.createUnimplementedError();
  }

  async show(): Promise<void> {
    throw this.createUnimplementedError();
  }

  private createUnimplementedError(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }
}
