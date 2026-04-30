import { WebPlugin } from "@capacitor/core";

import type { FormbricksPlugin } from "./definitions";

export class FormbricksWeb extends WebPlugin implements FormbricksPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log("ECHO", options);
    return options;
  }
}
