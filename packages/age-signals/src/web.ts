import { WebPlugin } from "@capacitor/core";

import type { AgeSignalsPlugin, CheckAgeSignalsResult } from "./definitions";

export class AgeSignalsWeb extends WebPlugin implements AgeSignalsPlugin {
  async checkAgeSignals(): Promise<CheckAgeSignalsResult> {
    throw this.unimplemented("Not implemented on web.");
  }
}
