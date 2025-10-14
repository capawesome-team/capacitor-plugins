import { WebPlugin } from "@capacitor/core";

import type { AgeSignalsPlugin } from "./definitions";

export class AgeSignalsWeb extends WebPlugin implements AgeSignalsPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log("ECHO", options);
    return options;
  }
}
