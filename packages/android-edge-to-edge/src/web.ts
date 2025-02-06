import { WebPlugin } from "@capacitor/core";

import type { EdgeToEdgePlugin } from "./definitions";

export class EdgeToEdgeWeb extends WebPlugin implements EdgeToEdgePlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log("ECHO", options);
    return options;
  }
}
