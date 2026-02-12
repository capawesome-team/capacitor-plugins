import { registerPlugin } from "@capacitor/core";

import type { AppleSignInPlugin } from "./definitions";

const AppleSignIn = registerPlugin<AppleSignInPlugin>("AppleSignIn", {
  web: () => import("./web").then((m) => new m.AppleSignInWeb()),
});

export * from "./definitions";
export { AppleSignIn };
