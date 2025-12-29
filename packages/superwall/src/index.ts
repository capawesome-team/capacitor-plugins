import { registerPlugin } from "@capacitor/core";

import type { SuperwallPlugin } from "./definitions";

const Superwall = registerPlugin<SuperwallPlugin>("Superwall", {
  web: () => import("./web").then((m) => new m.SuperwallWeb()),
});

export * from "./definitions";
export { Superwall };
