import { WebPlugin } from "@capacitor/core";

import type { AppleSignInPlugin } from "./definitions";

export class AppleSignInWeb extends WebPlugin implements AppleSignInPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log("ECHO", options);
    return options;
  }
}
