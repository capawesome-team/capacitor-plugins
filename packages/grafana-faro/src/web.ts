import { WebPlugin } from "@capacitor/core";

import type { GrafanaFaroPlugin } from "./definitions";

export class GrafanaFaroWeb extends WebPlugin implements GrafanaFaroPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log("ECHO", options);
    return options;
  }
}
