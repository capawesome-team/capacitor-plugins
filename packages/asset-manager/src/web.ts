import { WebPlugin } from "@capacitor/core";

import type { AssetManagerPlugin } from "./definitions";

export class AssetManagerWeb extends WebPlugin implements AssetManagerPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log("ECHO", options);
    return options;
  }
}
