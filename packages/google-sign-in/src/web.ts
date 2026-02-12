import { WebPlugin } from "@capacitor/core";

import type { GoogleSignInPlugin } from "./definitions";

export class GoogleSignInWeb extends WebPlugin implements GoogleSignInPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log("ECHO", options);
    return options;
  }
}
