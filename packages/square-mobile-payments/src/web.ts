import { WebPlugin } from "@capacitor/core";

import type { SquareMobilePaymentsPlugin } from "./definitions";

export class SquareMobilePaymentsWeb
  extends WebPlugin
  implements SquareMobilePaymentsPlugin
{
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log("ECHO", options);
    return options;
  }
}
