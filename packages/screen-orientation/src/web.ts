import { WebPlugin } from '@capacitor/core';

import type {
  GetCurrentOrientationResult,
  LockOptions,
  ScreenOrientationChange,
  ScreenOrientationPlugin,
} from './definitions';
import { OrientationType } from './definitions';

export class ScreenOrientationWeb
  extends WebPlugin
  implements ScreenOrientationPlugin
{
  private readonly isSupported: boolean;

  constructor() {
    super();
    try {
      this.isSupported = 'orientation' in screen;
    } catch {
      this.isSupported = false;
    }

    if (this.isSupported) {
      screen.orientation.addEventListener(
        'change',
        this.handleOrientationChange,
      );
    }
  }

  public async lock(options: LockOptions): Promise<void> {
    if (!this.isSupported) {
      this.throwUnsupportedError();
    }
    const type = options?.type ?? screen.orientation.type;
    await screen.orientation.lock(type);
  }

  public async unlock(): Promise<void> {
    if (!this.isSupported) {
      this.throwUnsupportedError();
    }
    screen.orientation.unlock();
  }

  public async getCurrentOrientation(): Promise<GetCurrentOrientationResult> {
    if (!this.isSupported) {
      this.throwUnsupportedError();
    }
    switch (screen.orientation.type) {
      case 'landscape-primary':
        return { type: OrientationType.LANDSCAPE_PRIMARY };
      case 'landscape-secondary':
        return { type: OrientationType.LANDSCAPE_SECONDARY };
      case 'portrait-secondary':
        return { type: OrientationType.PORTRAIT_SECONDARY };
      default:
        return { type: OrientationType.PORTRAIT_PRIMARY };
    }
  }

  private handleOrientationChange = async () => {
    const result = await this.getCurrentOrientation();
    const changeEvent: ScreenOrientationChange = {
      type: result.type,
    };
    this.notifyListeners('screenOrientationChange', changeEvent);
  };

  private throwUnsupportedError(): never {
    throw this.unavailable(
      'Screen Orientation API not available in this browser.',
    );
  }
}
