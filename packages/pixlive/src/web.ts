import { WebPlugin } from "@capacitor/core";

import type { PixlivePlugin } from "./definitions";

export class PixliveWeb extends WebPlugin implements PixlivePlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log("ECHO", options);
    return options;
  }
}
