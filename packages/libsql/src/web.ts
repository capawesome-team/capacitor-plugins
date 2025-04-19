import { WebPlugin } from "@capacitor/core";

import type { LibsqlPlugin } from "./definitions";

export class LibsqlWeb extends WebPlugin implements LibsqlPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log("ECHO", options);
    return options;
  }
}
