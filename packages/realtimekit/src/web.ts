import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type { RealtimeKitPlugin, StartMeetingOptions } from './definitions';

export class RealtimeKitWeb extends WebPlugin implements RealtimeKitPlugin {
  public async initialize(): Promise<void> {
    throw this.createUnimplementedException();
  }

  public async startMeeting(_options: StartMeetingOptions): Promise<void> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'Not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }
}
